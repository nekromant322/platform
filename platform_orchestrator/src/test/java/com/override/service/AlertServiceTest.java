package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.*;
import com.override.model.enums.CoursePart;
import com.override.repository.CodeTryRepository;
import com.override.repository.ReviewRepository;
import enums.Communication;
import enums.StudyStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.override.utils.TestFieldsUtil.*;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {

    @InjectMocks
    private AlertService alertService;

    @Mock
    private PlatformUserService platformUserService;

    @Mock
    private CodeTryRepository codeTryRepository;

    @Mock
    private NotificatorFeign notificatorFeign;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    public void alertBadStudents() {

        List<PlatformUser> students = List.of(generateTestUser());

        List<PlatformUser> admins = List.of(new PlatformUser(4L, "Liza", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings()));

        List<CodeTry> testCodeTryList = new ArrayList<>();

        LocalDate testLocalDate = LocalDate.of(2022, 7, 28);
        LocalTime testLocalTime = LocalTime.of(23, 55);
        LocalDateTime testLocalDateTime = LocalDateTime.of(testLocalDate, testLocalTime);

        CodeTry codeTry1 = generateTestCodeTry();
        codeTry1.setId(1L);
        codeTry1.setDate(LocalDateTime.now().minusDays(4));
        testCodeTryList.add(codeTry1);

        CodeTry codeTry2 = generateTestCodeTry();
        codeTry1.setId(2L);
        codeTry2.setDate(LocalDateTime.now().minusDays(4));
        testCodeTryList.add(codeTry2);

        CodeTry codeTry3 = generateTestCodeTry();
        codeTry1.setId(3L);
        codeTry3.setDate(LocalDateTime.now().minusDays(8));
        testCodeTryList.add(codeTry3);

        when(platformUserService.getStudentsByCoursePart(CoursePart.CORE.ordinal())).thenReturn(students);
        when(platformUserService.getAllAdmins()).thenReturn(admins);

        when(codeTryRepository.findFirstByUserIdOrderByDate(codeTry1.getUser().getId())).thenReturn(testCodeTryList.get(0));
        when(codeTryRepository.findFirstByUserIdOrderByDate(codeTry2.getUser().getId())).thenReturn(testCodeTryList.get(1));
        when(codeTryRepository.findFirstByUserIdOrderByDate(codeTry3.getUser().getId())).thenReturn(testCodeTryList.get(2));

       // int countDays = codeTry3.getDate().getDayOfMonth();

       // String messageText = countDays + " - уже столько дней ты не отправляешь новых решений :(";

        alertService.alertBadStudents();

        verify(notificatorFeign, times(2)).sendMessage(codeTry3.getUser().getLogin(),
                Math.abs(codeTryRepository.findFirstByUserIdOrderByDate(students.get(0).getId()).getDate().getDayOfMonth()
                        - testLocalDateTime.getDayOfMonth()) + " - уже столько дней ты не отправляешь новых решений :(", Communication.EMAIL);

    }

    /*@Test
    public void alertMentorsAboutBadStudents() {
        List<PlatformUser> students = List.of(new PlatformUser(1L, "Andrey", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()),
                new PlatformUser(2L, "Liza", "s", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()),
                new PlatformUser(3L, "Artur", "e", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()));

        List<PlatformUser> admins = List.of(new PlatformUser(4L, "Andrey", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings()),
                new PlatformUser(5L, "Liza", "s", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings()));

        when(platformUserService.getAllStudents()).thenReturn(students);
        when(platformUserService.getAllAdmins()).thenReturn(admins);

        Review review1 = new Review();
        review1.setBookedDate(LocalDate.now());
        Review review2 = new Review();
        review2.setBookedDate(LocalDate.now());
        Review review3 = new Review();
        review3.setBookedDate(LocalDate.now());

        when(reviewRepository.findFirstByStudentIdOrderByIdDesc(students.get(0).getId())).thenReturn(review1);
        when(reviewRepository.findFirstByStudentIdOrderByIdDesc(students.get(1).getId())).thenReturn(review2);
        when(reviewRepository.findFirstByStudentIdOrderByIdDesc(students.get(2).getId())).thenReturn(review3);


    }*/

}
