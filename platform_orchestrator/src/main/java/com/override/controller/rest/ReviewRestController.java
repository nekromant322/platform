package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.ReviewService;
import dto.ReviewDTO;
import dto.ReviewFilterDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @PatchMapping
    @ApiOperation(value = "Сохраняет новое или изменяет существующее ревью.\n" +
            "Варианты условий:\n" +
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
            "reviewDTO.getMentorLogin().equals(userLogin) - если логины совпадают, отправляется уведомление об изменении времени.\n" +
            "@param reviewDTO проверяет информацию, полученную из запроса\n" +
            "@param userLogin имя пользователя, делающего запрос")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ревью сохранено!")
    })
    public ResponseEntity<String> saveOrUpdateReview(@RequestBody @ApiParam(value = "reviewDTO проверяет информацию, полученную из запроса") ReviewDTO reviewDTO,
                                                     @AuthenticationPrincipal @ApiParam(value = "Отсюда достанем имя пользователя, делающего запрос") CustomStudentDetailService.CustomStudentDetails user) {
        reviewService.saveOrUpdate(reviewDTO, user.getUsername());
        return new ResponseEntity<>("Ревью сохранено!", HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Поиск выбранных ревью с использованием фильтра.\n" +
            "Ревью можно найти по логину студента или ментора, а также по дате.\n" +
            "Если эти параметры имеют значение null, то отбираются новые ревью, которым еще не назначены ментор и время",
            notes = "Возвращает список ReviewDTO, полученный путем сопоставления списка найденных отзывов.")
    public List<ReviewDTO> findReview(@RequestBody @ApiParam(value = "reviewFilterDTO поступает из запроса") ReviewFilterDTO reviewFilterDTO) {
        return reviewService.find(reviewFilterDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    @ApiOperation(value = "Удаляет ревью по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ревью удалено!")
    })
    public ResponseEntity<String> deleteReview(@RequestParam Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }
}
