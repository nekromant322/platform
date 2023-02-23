package com.override.service;


import com.override.feign.NotificatorFeign;
import com.override.mapper.ReviewMapper;
import com.override.model.PlatformUser;
import com.override.model.enums.Role;
import com.override.repository.PlatformUserRepository;
import com.override.repository.ReviewRepository;
import com.override.repository.VkCallRepository;
import com.override.util.CurrentTimeService;
import dto.ReviewDTO;
import dto.ReviewFilterDTO;
import enums.CommunicationType;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewService {

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VkCallRepository vkCallRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private NotificatorFeign notificatorFeign;

    @Autowired
    private CurrentTimeService currentTimeService;

    @Autowired
    private VkApiService vkApiService;

    public static String CONFIRMED_REVIEW_MESSAGE_TELEGRAM = "Ментор %s подтвердил ревью %s в %s, ссылка на звонок: %s";
    public static String DELETED_REVIEW_MESSAGE_TELEGRAM = "Ментор вынужден был отменить ревью. " +
            "Попробуйте записаться на другое время";
    public static String CHANGED_REVIEW_TIME_MESSAGE_TELEGRAM = "Ментор изменил время ревью. Ревью пройдет %s в %s, ссылка на звонок: %s";
    public static String CHANGED_REVIEW_MENTOR_MESSAGE_TELEGRAM = "На Ваше ревью сменился ментор. Его проведет: %s %s в %s, ссылка на звонок: %s";
    public static String NEW_REVIEW_MESSAGE_TELEGRAM = "Новый запрос на ревью от пользователя %s";

    /**
     * Сохраняет новое или изменяет существующее ревью.
     * Разберемся с условиями.
     * reviewDTO.getId() == null, reviewDTO.getStudentLogin() == null — будет отправлено уведомление в Telegram
     * ментору о новом запросе на ревью от студента. Имя пользователя студента отправляется в reviewDTO.
     * Когда ревью только создается, подразумевается, что этот запрос делает студент,
     * значит у ревью нет айди и в дто не передается его логин, поэтому сетим логин студента и сохраняем.
     * <p>
     * reviewDTO.getBookedTime() != null && reviewDTO.getMentorLogin() == "" -
     * будет отправлено уведомление в Telegram студенту о подтверждении ревью ментором. Имя пользователя ментора
     * отправляется в reviewDTO.
     * <p>
     * reviewDTO.getMentorLogin() != null && reviewDTO.getBookedTime() != null - изменения вносятся в уже
     * подтвержденное ревью. А дальше уже два пути:
     * <p>
     * !reviewDTO.getMentorLogin().equals(userLogin) - будет отправлено уведомление о смене ментора с указанием времени.
     * reviewDTO.getMentorLogin().equals(userLogin) - если логины совпадают, отправляется уведомление об изменении
     * времени.
     *
     * @param reviewDTO review information obtained from the request
     * @param userLogin username of the user making the request
     */
    public void saveOrUpdate(ReviewDTO reviewDTO, String userLogin) {
        if (reviewDTO.getId() == null && reviewDTO.getStudentLogin() == null) {
            List<PlatformUser> mentorList = platformUserRepository.findAllByAuthorityName(Role.ADMIN.getName());
            for (PlatformUser mentor : mentorList) {
                sendMessage(mentor.getLogin(), String.format(NEW_REVIEW_MESSAGE_TELEGRAM,
                        userLogin));
                reviewDTO.setStudentLogin(userLogin);
            }
        } else if (reviewDTO.getBookedTime() != null && reviewDTO.getMentorLogin() == "") {
            reviewDTO.setCallLink(vkApiService.getCall(reviewDTO.getId()));
            sendMessage(reviewDTO.getStudentLogin(), String.format(CONFIRMED_REVIEW_MESSAGE_TELEGRAM, userLogin,
                    reviewDTO.getBookedDate(), reviewDTO.getBookedTime(), reviewDTO.getCallLink()));
            reviewDTO.setMentorLogin(userLogin);
        } else if (reviewDTO.getMentorLogin() != null && reviewDTO.getBookedTime() != null) {
            if (!reviewDTO.getMentorLogin().equals(userLogin)) {
                reviewDTO.setCallLink(vkApiService.getCall(reviewDTO.getId()));
                sendMessage(reviewDTO.getStudentLogin(), String.format(CHANGED_REVIEW_MENTOR_MESSAGE_TELEGRAM,
                        userLogin, reviewDTO.getBookedDate(), reviewDTO.getBookedTime(), reviewDTO.getCallLink()));
            } else {
                reviewDTO.setCallLink(vkApiService.getCall(reviewDTO.getId()));
                sendMessage(reviewDTO.getStudentLogin(), String.format(CHANGED_REVIEW_TIME_MESSAGE_TELEGRAM,
                        reviewDTO.getBookedDate(), reviewDTO.getBookedTime(), reviewDTO.getCallLink()));
            }
        }
        reviewRepository.save(reviewMapper.dtoToEntity(reviewDTO,
                platformUserRepository.findFirstByLogin(reviewDTO.getStudentLogin()),
                platformUserRepository.findFirstByLogin(reviewDTO.getMentorLogin()),
                vkCallRepository.findVkCallByReviewId(reviewDTO.getId())));
    }

    public void sendMessage(String login, String message) {
        try {
            PlatformUser user = platformUserRepository.findFirstByLogin(login);
            if (user.getUserSettings().getVkNotification()) {
                notificatorFeign.sendMessage(login, message, CommunicationType.VK);
            }
            if (user.getUserSettings().getTelegramNotification()) {
                notificatorFeign.sendMessage(login, message, CommunicationType.TELEGRAM);
            }
        } catch (FeignException e) {
            log.error("При попытке отправить сообщение пользователю \"{}\" произошла ошибка \"{}\"", login, e.getLocalizedMessage());
        }
    }

    public void delete(Long id) {
        sendMessage(reviewRepository.findById(id).get().getStudent().getLogin(),
                DELETED_REVIEW_MESSAGE_TELEGRAM);
        reviewRepository.deleteById(id);
    }

    /**
     * Searches for the necessary reviews using the filter
     * Reviews can be found by student or mentor login and by date
     * If these parameters have null, then we are looking for new reviews that have not yet assigned a mentor and time
     *
     * @param reviewFilterDTO comes from request
     * @return returns a list of reviewDTOs obtained by mapping the list of found reviews
     */
    public List<ReviewDTO> find(ReviewFilterDTO reviewFilterDTO) {
        if (reviewFilterDTO.getMentorLogin() != null) {
            return (reviewRepository.findReviewByMentorLogin(reviewFilterDTO.getMentorLogin())).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        }
        if (reviewFilterDTO.getStudentLogin() != null && reviewFilterDTO.getBookedDate() != null) {
            return (reviewRepository.findReviewByStudentLoginAndBookedDate(reviewFilterDTO.getStudentLogin(), reviewFilterDTO.getBookedDate())).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        }
        if (reviewFilterDTO.getStudentLogin() != null) {
            return (reviewRepository.findReviewByStudentLogin(reviewFilterDTO.getStudentLogin())).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        }
        if (reviewFilterDTO.getBookedDate() != null) {
            return (reviewRepository.findReviewByBookedDate(reviewFilterDTO.getBookedDate())).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        } else {
            return (reviewRepository.findReviewByMentorIsNull()).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        }
    }

    public void sendScheduledNotification() {
        reviewRepository.findReviewByBookedDate(currentTimeService.getCurrentDateTime().plusMinutes(10).toLocalDate())
                .stream()
                .filter(review -> review.getBookedTime() != null)
                .filter(review -> LocalDateTime.of(review.getBookedDate(),
                        review.getBookedTime()).isAfter(currentTimeService.getCurrentDateTime()))
                .filter(review -> LocalDateTime.of(review.getBookedDate(), review.getBookedTime()).isBefore(currentTimeService.getCurrentDateTime().plusMinutes(10)))
                .forEach(review -> {
                    String messageText = "Скоро ревью у @" + review.getStudent().getLogin() +
                            " с @" + review.getMentor().getLogin() + "\n" +
                            review.getBookedDate() + " " + review.getBookedTime() +
                            "\nТема: " + review.getTopic() + "\n" +
                            "Ссылка на звонок: " + review.getVkCall().getJoinLink();
                    sendMessage(review.getStudent().getLogin(), messageText);
                    sendMessage(review.getMentor().getLogin(), messageText);
                });
    }
}

