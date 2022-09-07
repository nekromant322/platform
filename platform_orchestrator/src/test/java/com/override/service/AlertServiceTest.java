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

import static java.time.temporal.ChronoUnit.DAYS;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

        alertService.setDaysOfInactivity(3L);

        List<PlatformUser> students = List.of(new PlatformUser(1L, "Student1", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()),
                new PlatformUser(2L, "Student2", "s", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()),
                new PlatformUser(3L, "Student3", "e", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()));

        List<PlatformUser> admins = List.of(new PlatformUser(4L, "Admin1", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings()),
                new PlatformUser(5L, "Admin2", "s", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings()));

        CodeTry codeTry1 = new CodeTry();
        codeTry1.setDate(LocalDateTime.now().minusDays(10));

        CodeTry codeTry2 = new CodeTry();
        codeTry2.setDate(LocalDateTime.now().minusDays(8));

        CodeTry codeTry3 = new CodeTry();
        codeTry3.setDate(LocalDateTime.now().minusDays(1));

        when(platformUserService.getStudentsByCoursePart(CoursePart.CORE.ordinal())).thenReturn(students);
        when(platformUserService.getAllAdmins()).thenReturn(admins);

        when(codeTryRepository.findFirstByUserIdOrderByDate(students.get(0).getId())).thenReturn(codeTry1);
        when(codeTryRepository.findFirstByUserIdOrderByDate(students.get(1).getId())).thenReturn(codeTry2);
        when(codeTryRepository.findFirstByUserIdOrderByDate(students.get(2).getId())).thenReturn(codeTry3);

        alertService.alertBadStudents();

        verify(notificatorFeign, times(1)).sendMessage(students.get(0).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(0).getId()).getDate(), LocalDateTime.now()) + " - уже столько дней ты не отправляешь новых решений :(", Communication.EMAIL);
        verify(notificatorFeign, times(1)).sendMessage(students.get(1).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(1).getId()).getDate(), LocalDateTime.now()) + " - уже столько дней ты не отправляешь новых решений :(", Communication.EMAIL);
        verify(notificatorFeign, times(0)).sendMessage(students.get(2).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(2).getId()).getDate(), LocalDateTime.now()) + " - уже столько дней ты не отправляешь новых решений :(", Communication.EMAIL);


        verify(notificatorFeign, times(1)).sendMessage(admins.get(0).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(0).getId()).getDate(), LocalDateTime.now()) + " - столько дней " + students.get(0).getLogin() + " не присылал новых решений на платформу", Communication.TELEGRAM);
        verify(notificatorFeign, times(1)).sendMessage(admins.get(0).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(1).getId()).getDate(), LocalDateTime.now()) + " - столько дней " + students.get(1).getLogin() + " не присылал новых решений на платформу", Communication.TELEGRAM);
        verify(notificatorFeign, times(0)).sendMessage(admins.get(0).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(2).getId()).getDate(), LocalDateTime.now()) + " - столько дней " + students.get(2).getLogin() + " не присылал новых решений на платформу", Communication.TELEGRAM);

        verify(notificatorFeign, times(1)).sendMessage(admins.get(1).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(0).getId()).getDate(), LocalDateTime.now()) + " - столько дней " + students.get(0).getLogin() + " не присылал новых решений на платформу", Communication.TELEGRAM);
        verify(notificatorFeign, times(1)).sendMessage(admins.get(1).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(1).getId()).getDate(), LocalDateTime.now()) + " - столько дней " + students.get(1).getLogin() + " не присылал новых решений на платформу", Communication.TELEGRAM);
        verify(notificatorFeign, times(0)).sendMessage(admins.get(1).getLogin(), DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(students.get(2).getId()).getDate(), LocalDateTime.now()) + " - столько дней " + students.get(2).getLogin() + " не присылал новых решений на платформу", Communication.TELEGRAM);

    }

    @Test
    public void alertMentorsAboutBadStudents() {

        long daysForReview = 7L;

        alertService.setDaysForReview(7L);

        List<PlatformUser> students = List.of(new PlatformUser(1L, "Student1", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()),
                new PlatformUser(2L, "Student2", "s", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()),
                new PlatformUser(3L, "Student3", "e", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "user")), new PersonalData(), new UserSettings()));

        List<PlatformUser> admins = List.of(new PlatformUser(4L, "Admin1", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings()),
                new PlatformUser(5L, "Admin2", "s", StudyStatus.ACTIVE, CoursePart.CORE,
                        Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings()));


        when(platformUserService.getStudentsByLastReview(daysForReview)).thenReturn(students);
        when(platformUserService.getAllAdmins()).thenReturn(admins);


        alertService.alertMentorsAboutBadStudents();

        verify(notificatorFeign, times(1)).sendMessage(admins.get(0).getLogin(), "студент " + students.get(0).getLogin() + " давно не был на ревью ", Communication.TELEGRAM);
        verify(notificatorFeign, times(1)).sendMessage(admins.get(0).getLogin(), "студент " + students.get(1).getLogin() + " давно не был на ревью ", Communication.TELEGRAM);
        verify(notificatorFeign, times(1)).sendMessage(admins.get(0).getLogin(), "студент " + students.get(2).getLogin() + " давно не был на ревью ", Communication.TELEGRAM);

    }

}