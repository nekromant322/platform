package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.ReviewService;
import com.override.service.VkApiService;
import dto.ReviewDTO;
import dto.ReviewFilterDTO;
import dto.VkActorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private VkApiService vkApiService;

    @PatchMapping
    @Operation(summary = "Сохраняет новое или изменяет существующее ревью.",
            description = "Варианты условий:\n" +
            "reviewDTO.getId() == null, reviewDTO.getStudentLogin() == null — будет отправлено уведомление в Telegram\n" +
            "ментору о новом запросе на ревью от студента. Имя пользователя студента отправляется в reviewDTO.\n" +
            "Когда ревью только создается, подразумевается, что этот запрос делает студент,\n" +
            "и, следовательно, у ревью нет айди и в дто не передается его логин, поэтому сетим логин студента и сохраняем.\n" +
            "---\n" +
            "reviewDTO.getBookedTime() != null && reviewDTO.getMentorLogin() == \"\" -\n" +
            "будет отправлено уведомление в Telegram студенту о подтверждении ревью ментором. Имя пользователя ментора отправляется в reviewDTO.\n" +
            "---\n" +
            "reviewDTO.getMentorLogin() != null && reviewDTO.getBookedTime() != null - изменения вносятся в уже\n" +
            "подтвержденное ревью. А дальше уже два варианта:\n" +
            "!reviewDTO.getMentorLogin().equals(userLogin) - будет отправлено уведомление о смене ментора с указанием времени.\n" +
            "reviewDTO.getMentorLogin().equals(userLogin) - если логины совпадают, отправляется уведомление об изменении времени")
    @ApiResponse(responseCode = "200", description = "Ревью сохранено!")
    public ResponseEntity<String> saveOrUpdateReview(@RequestBody @Parameter(description = "reviewDTO проверяет информацию, полученную из запроса") ReviewDTO reviewDTO,
                                                     @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        reviewService.saveOrUpdate(reviewDTO, user.getUsername());
        return new ResponseEntity<>("Ревью сохранено!", HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Возвращает список ревью, полученный путем сопоставления списка найденных отзывов.",
            description = "Поиск выбранных ревью с использованием фильтра.\n" +
            "Ревью можно найти по логину студента или ментора, а также по дате.\n" +
            "Если эти параметры имеют значение null, то отбираются новые ревью, которым еще не назначены ментор и время")
    public List<ReviewDTO> findReview(@RequestBody @Parameter(description = "reviewFilterDTO поступает из запроса")
                                          ReviewFilterDTO reviewFilterDTO) {
        return reviewService.find(reviewFilterDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    @Operation(summary = "Удаляет ревью по id")
    @ApiResponse(responseCode = "200", description = "Ревью удалено!")
    public ResponseEntity<String> deleteReview(@RequestParam Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/createVkCall")
    @Operation(summary = "Создание звонка в ВК", description = "Делает запрос по open VK API на создание звонка")
    public void createVkCallCall(@Parameter(description = "ID ревью", example = "Shu") @RequestParam Long reviewId,
                                 @RequestBody VkActorDTO vkActorDTO) {
        vkApiService.createVkCall(reviewId, vkActorDTO);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/vkAppId")
    @Operation(summary = "Запрос на ID VK приложения")
    public Long getVkAppId() {
        System.out.println(vkApiService.getVkAppId());
        return vkApiService.getVkAppId();
    }
}
