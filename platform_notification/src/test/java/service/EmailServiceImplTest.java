package service;

import com.override.mapper.EmailMapper;
import com.override.service.EmailServiceImpl;
import dto.MailDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private EmailMapper emailMapper;

    @Test
    public void testSendSimpleMail() {
        MailDTO mailDTO = MailDTO.builder().subject("test").text("test").replyTo("test").to(List.of("test")).build();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText("test");
        simpleMailMessage.setFrom("test");
        simpleMailMessage.setSubject("test");
        simpleMailMessage.setTo("test");
        when(emailMapper.dtoToListOfSimpleMails(mailDTO)).thenReturn(List.of(simpleMailMessage));
        emailService.sendSimpleMail(mailDTO);
        verify(emailSender, times(1)).send(simpleMailMessage);
        verify(emailMapper, times(1)).dtoToListOfSimpleMails(mailDTO);
    }

    @Test
    public void testSendSimpleMailWhenMailSendsException(){
        MailDTO mailDTO = MailDTO.builder().subject("test").text("test").replyTo("test").to(List.of("test")).build();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText("test");
        simpleMailMessage.setFrom("null");
        simpleMailMessage.setSubject("test");
        simpleMailMessage.setTo("test");
        when(emailMapper.dtoToListOfSimpleMails(mailDTO)).thenReturn(List.of(simpleMailMessage));
        doThrow(MailSendException.class).when(emailSender).send(simpleMailMessage);
        assertThatThrownBy(() -> emailService.sendSimpleMail(mailDTO))
                .isInstanceOf(MailSendException.class);
    }
}
