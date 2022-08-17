package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.mapper.ReviewMapper;
import com.override.model.PlatformUser;
import com.override.model.Review;
import com.override.repository.PlatformUserRepository;
import com.override.repository.ReviewRepository;
import com.override.util.CurrentTimeService;
import dto.ReviewDTO;
import dto.ReviewFilterDTO;
import enums.Communication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private PlatformUserRepository platformUserRepository;

    @Mock
    private NotificatorFeign notificatorFeign;

    @Mock
    private CurrentTimeService currentTimeService;

    @Test
    public void saveOrUpdateConfirmReview() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testUser = generateTestUser();
        testReviewDTO.setMentorLogin("");

        when(platformUserRepository.findFirstByLogin(testReviewDTO.getStudentLogin()))
                .thenReturn(testUser);

        reviewService.saveOrUpdate(testReviewDTO, testUser.getLogin());

        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
        verify(notificatorFeign, times(1)).sendMessage(testReviewDTO.getStudentLogin(),
                String.format(ReviewService.CONFIRMED_REVIEW_MESSAGE_TELEGRAM, testUser.getLogin(), testReviewDTO.getBookedDate(),
                        testReviewDTO.getBookedTime(), testReviewDTO.getCallLink()), Communication.TELEGRAM);
    }

    @Test
    public void saveOrUpdateChangeReviewTime() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testUser = generateTestUser();

        when(platformUserRepository.findFirstByLogin(testReviewDTO.getStudentLogin()))
                .thenReturn(testUser);
        when(platformUserRepository.findFirstByLogin(testReviewDTO.getMentorLogin()))
                .thenReturn(testUser);

        reviewService.saveOrUpdate(testReviewDTO, testReviewDTO.getMentorLogin());

        verify(notificatorFeign, times(1)).sendMessage(testReviewDTO.getStudentLogin(), String.format(
                ReviewService.CHANGED_REVIEW_TIME_MESSAGE_TELEGRAM, testReviewDTO.getBookedDate(),
                testReviewDTO.getBookedTime(), testReviewDTO.getCallLink()), Communication.TELEGRAM);
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
    }

    @Test
    public void saveOrUpdateChangeReviewMentor() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testUser = generateTestUser();
        String testUserLogin = "Ivan";

        when(platformUserRepository.findFirstByLogin(testReviewDTO.getStudentLogin()))
                .thenReturn(testUser);
        when(platformUserRepository.findFirstByLogin(testReviewDTO.getMentorLogin()))
                .thenReturn(testUser);

        reviewService.saveOrUpdate(testReviewDTO, testUserLogin);

        verify(notificatorFeign, times(1)).sendMessage(testReviewDTO.getStudentLogin(),
                String.format(ReviewService.CHANGED_REVIEW_MENTOR_MESSAGE_TELEGRAM, testUserLogin,
                        testReviewDTO.getBookedDate(), testReviewDTO.getBookedTime(), testReviewDTO.getCallLink()), Communication.TELEGRAM);
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
    }

    @Test
    public void saveOrUpdateNewReviewMessageTelegram() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testUser = generateTestUser();

        testReviewDTO.setId(null);
        testReviewDTO.setStudentLogin(null);
        testReviewDTO.setMentorLogin(null);
        testReviewDTO.setBookedTime(null);

        ArrayList<PlatformUser> testList = new ArrayList<>();
        testList.add(testUser);

        when(platformUserRepository.findFirstByLogin(testUser.getLogin()))
                .thenReturn(testUser);
        when(platformUserRepository.findFirstByLogin(testReviewDTO.getMentorLogin()))
                .thenReturn(testUser);

        when(platformUserRepository.findAllByAuthorityName(any()))
                .thenReturn(testList);

        reviewService.saveOrUpdate(testReviewDTO, testUser.getLogin());

        verify(notificatorFeign, times(1)).sendMessage(testUser.getLogin(),
                String.format(ReviewService.NEW_REVIEW_MESSAGE_TELEGRAM, testUser.getLogin()), Communication.TELEGRAM);
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
    }

    @Test
    public void deleteReviewTelegramNotification() {
        String DELETED_REVIEW_MESSAGE_TELEGRAM_TEST = "Ментор вынужден был отменить ревью. Попробуйте записаться на другое время";
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testUser = generateTestUser();
        Review testReview = generateTestReview();

        when(reviewRepository.findById(any())).thenReturn(Optional.ofNullable(testReview));

        reviewService.delete(testReviewDTO.getId());

        verify(reviewRepository, times(1)).deleteById(1L);
        verify(notificatorFeign, times(1)).sendMessage(testUser.getLogin(), DELETED_REVIEW_MESSAGE_TELEGRAM_TEST, Communication.TELEGRAM);
    }

    @Test
    public void findReviewByMentorLogin() {
        List<ReviewDTO> testReviewDTOList = new ArrayList<>();
        testReviewDTOList.add(generateTestReviewDTO());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());

        ReviewFilterDTO testReviewFilterDTO = new ReviewFilterDTO();
        testReviewFilterDTO.setStudentLogin(null);
        testReviewFilterDTO.setBookedDate(null);
        testReviewFilterDTO.setMentorLogin(testReviewDTO.getMentorLogin());

        when(reviewRepository.findReviewByMentorLogin(testReviewFilterDTO.getMentorLogin())).thenReturn(testReview);
        when(reviewMapper.entityToDto(testReview.iterator().next())).thenReturn(testReviewDTOList.iterator().next());

        List<ReviewDTO> review = reviewService.find(testReviewFilterDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewDTOList.iterator().next());
    }

    @Test
    public void findReviewByStudentLogin() {
        List<ReviewDTO> testReviewDTOList = new ArrayList<>();
        testReviewDTOList.add(generateTestReviewDTO());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());

        ReviewFilterDTO testReviewFilterDTO = new ReviewFilterDTO();
        testReviewFilterDTO.setStudentLogin(testReviewDTO.getStudentLogin());
        testReviewFilterDTO.setBookedDate(null);
        testReviewFilterDTO.setMentorLogin(null);

        when(reviewRepository.findReviewByStudentLogin(testReviewFilterDTO.getStudentLogin())).thenReturn(testReview);
        when(reviewMapper.entityToDto(testReview.iterator().next())).thenReturn(testReviewDTOList.iterator().next());

        List<ReviewDTO> review = reviewService.find(testReviewFilterDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewDTOList.iterator().next());
    }

    @Test
    public void findReviewByBookedDate() {
        List<ReviewDTO> testReviewDTOList = new ArrayList<>();
        testReviewDTOList.add(generateTestReviewDTO());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());

        ReviewFilterDTO testReviewFilterDTO = new ReviewFilterDTO();
        testReviewFilterDTO.setStudentLogin(null);
        testReviewFilterDTO.setBookedDate(testReviewDTO.getBookedDate());
        testReviewFilterDTO.setMentorLogin(null);

        when(reviewRepository.findReviewByBookedDate(testReviewFilterDTO.getBookedDate())).thenReturn(testReview);
        when(reviewMapper.entityToDto(testReview.iterator().next())).thenReturn(testReviewDTOList.iterator().next());

        List<ReviewDTO> review = reviewService.find(testReviewFilterDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewDTOList.iterator().next());
    }

    @Test
    public void findReviewByMentorIsNull() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        testReviewDTO.setMentorLogin(null);
        List<ReviewDTO> testReviewList = new ArrayList<>();
        testReviewList.add(testReviewDTO);

        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());

        ReviewFilterDTO testReviewFilterDTO = new ReviewFilterDTO();
        testReviewFilterDTO.setStudentLogin(null);
        testReviewFilterDTO.setBookedDate(null);
        testReviewFilterDTO.setMentorLogin(null);

        when(reviewRepository.findReviewByMentorIsNull()).thenReturn(testReview);
        when(reviewMapper.entityToDto(testReview.iterator().next())).thenReturn(testReviewList.iterator().next());

        List<ReviewDTO> review = reviewService.find(testReviewFilterDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewList.iterator().next());
    }

    @Test
    public void sendScheduledNotificationVar3() {
        List<Review> testReviewList = new ArrayList<>();
        LocalDate testLocalDate = LocalDate.of(2022, 7, 28);
        LocalTime testLocalTime = LocalTime.of(23, 55);
        LocalDateTime testLocalDateTime = LocalDateTime.of(testLocalDate, testLocalTime);

        Review review1 = generateTestReview();
        review1.setId(10L);
        review1.setBookedTime(null);
        review1.setBookedDate(null);
        testReviewList.add(review1);

        Review review2 = generateTestReview();
        review2.setId(20L);
        LocalDateTime localDateTimeReview2 = testLocalDateTime.plusMinutes(45);
        review2.setBookedTime(localDateTimeReview2.toLocalTime());
        review2.setBookedDate(localDateTimeReview2.toLocalDate());
        testReviewList.add(review2);

        Review review3 = generateTestReview();
        review3.setId(30L);
        LocalDateTime localDateTimeReview3 = testLocalDateTime.minusMinutes(45);
        review3.setBookedTime(localDateTimeReview3.toLocalTime());
        review3.setBookedDate(localDateTimeReview3.toLocalDate());
        testReviewList.add(review3);

        Review review4 = generateTestReview();
        review4.setId(40L);
        LocalDateTime localDateTimeReview4 = testLocalDateTime.plusMinutes(5);
        review4.setBookedTime(localDateTimeReview4.toLocalTime());
        review4.setBookedDate(localDateTimeReview4.toLocalDate());
        testReviewList.add(review4);


        when(currentTimeService.getCurrentDateTime()).thenReturn(testLocalDateTime);
        when(reviewRepository.findReviewByBookedDate(currentTimeService.getCurrentDateTime().plusMinutes(10).toLocalDate()))
                .thenReturn(testReviewList);

        String messageText = "Скоро ревью у @" + review4.getStudent().getLogin() +
                " с @" + review4.getMentor().getLogin() + "\n" +
                review4.getBookedDate() + " " + review4.getBookedTime() +
                "\nТема: " + review4.getTopic() + "\n" +
                "Ссылка на звонок: " + review4.getCallLink();

        reviewService.sendScheduledNotification();
        verify(notificatorFeign, times(2)).sendMessage(review4.getStudent().getLogin(), messageText, Communication.TELEGRAM);
    }
}
