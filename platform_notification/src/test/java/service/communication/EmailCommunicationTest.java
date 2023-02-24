package service.communication;

import com.override.model.Recipient;
import com.override.service.EmailService;
import com.override.service.communication.EmailCommunication;
import dto.MailDTO;
import enums.CommunicationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailCommunicationTest {

    @Value("${mail.subject}")
    private String mailSubject;

    @InjectMocks
    private EmailCommunication emailCommunication;

    @Mock
    private EmailService emailService;

    @Test
    public void testSendMessage() {
        Recipient recipient = Recipient.builder().email("testEmail").build();
        emailCommunication.sendMessage(recipient, "test");
        verify(emailService, times(1)).sendSimpleMail(MailDTO.builder().to(List.of("testEmail")).text("test").subject(mailSubject).build());
    }

    @Test
    public void testUpdateRecipient() {
        Recipient recipient = Recipient.builder().build();
        assertEquals(emailCommunication.updateRecipient(recipient, "test"), Recipient.builder().email("test").build());
    }

    @Test
    public void testGetType() {
        assertEquals(CommunicationType.EMAIL, emailCommunication.getType());
    }
}
