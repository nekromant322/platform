package service;

import com.override.feign.TelegramFeign;
import com.override.models.Recipient;
import com.override.service.EmailService;
import com.override.service.MessageService;
import com.override.service.RecipientService;
import com.override.service.TelegramService;
import com.override.util.CommunicationStrategy;
import com.override.util.CommunicationStrategyFactory;
import com.override.util.EmailCommunication;
import com.override.util.TelegramCommunication;
import dtos.MailDTO;
import dtos.MessageDTO;
import enums.Communication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static utils.TestFieldsUtil.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private RecipientService recipientService;

    @Mock
    private TelegramCommunication telegramCommunication;

    @Mock
    private EmailCommunication emailCommunication;

    @Mock
    private CommunicationStrategyFactory strategyFactory;

    @Mock
    private CommunicationStrategy strategy;

    @Test
    public void testWhenSendMessage() {
        Recipient recipient = getRecipient();
        String message = "test";
        Communication[] communication = {Communication.TELEGRAM, Communication.EMAIL};
        Map<Communication, CommunicationStrategy> strategyMap = getSenderMap(strategy, telegramCommunication, emailCommunication);
        ResponseEntity<HttpStatus> status = new ResponseEntity<>(HttpStatus.OK);

        when(recipientService.getRecipientByLogin(recipient.getLogin())).thenReturn(recipient);
        when(strategyFactory.getSenderMap()).thenReturn(strategyMap);
        when(telegramCommunication.sendMessage(recipient, message)).thenReturn(status);

        status = messageService.sendMessage(recipient.getLogin(), message, communication);

        assertEquals(new ResponseEntity<>(HttpStatus.OK), status);
    }
}
