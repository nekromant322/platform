package service;

import com.override.models.Recipient;
import com.override.service.MessageService;
import com.override.service.RecipientService;
import com.override.util.CommunicationStrategy;
import com.override.util.CommunicationStrategyFactory;
import com.override.util.EmailCommunication;
import com.override.util.TelegramCommunication;
import enums.Communication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @Test
    public void testWhenSendMessage() {
        Recipient recipient = getRecipient();
        String message = "test";
        Communication[] communication = {Communication.TELEGRAM, Communication.EMAIL};
        Map<Communication, CommunicationStrategy> strategyMap = getSenderMap(telegramCommunication, emailCommunication);
        ResponseEntity<String> status = new ResponseEntity<>(HttpStatus.OK);

        when(recipientService.findRecipientByLogin(recipient.getLogin())).thenReturn(recipient);
        when(strategyFactory.getSenderMap()).thenReturn(strategyMap);
        when(telegramCommunication.sendMessage(recipient, message)).thenReturn(status);

        status = messageService.sendMessage(recipient.getLogin(), message, communication);

        verify(recipientService, times(1)).findRecipientByLogin(recipient.getLogin());
        verify(strategyFactory, times(1)).getSenderMap();
        verify(telegramCommunication, times(1)).sendMessage(recipient, message);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), status);
    }

    @Test
    public void testWhenCommunicationTypesEmpty() {
        Recipient recipient = getRecipient();
        String message = "test";
        Communication[] communication = {};
        Map<Communication, CommunicationStrategy> strategyMap = getSenderMap(telegramCommunication, emailCommunication);
        ResponseEntity<String> status;

        when(recipientService.findRecipientByLogin(recipient.getLogin())).thenReturn(recipient);
        when(strategyFactory.getSenderMap()).thenReturn(strategyMap);

        status = messageService.sendMessage(recipient.getLogin(), message, communication);

        verify(recipientService, times(1)).findRecipientByLogin(recipient.getLogin());
        verify(strategyFactory, times(1)).getSenderMap();
        assertEquals(new ResponseEntity<>("Communication types is empty", HttpStatus.INTERNAL_SERVER_ERROR), status);
    }

    @Test
    public void testWhenRecipientNotFound() {
        String message = "test";
        Communication[] communication = {Communication.TELEGRAM, Communication.EMAIL};

        when(recipientService.findRecipientByLogin("test")).thenThrow(NoSuchElementException.class);

        assertThatThrownBy(() -> messageService.sendMessage("test", message, communication))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testWhenFirstTypeCommunicationReturnError() {
        Recipient recipient = getRecipient();
        String message = "test";
        Communication[] communication = {Communication.TELEGRAM, Communication.EMAIL};
        Map<Communication, CommunicationStrategy> strategyMap = getSenderMap(telegramCommunication, emailCommunication);
        ResponseEntity<String> status = new ResponseEntity<>(HttpStatus.OK);

        when(recipientService.findRecipientByLogin(recipient.getLogin())).thenReturn(recipient);
        when(strategyFactory.getSenderMap()).thenReturn(strategyMap);
        when(telegramCommunication.sendMessage(recipient, message)).thenReturn(new ResponseEntity<>("TelegramApiException", HttpStatus.INTERNAL_SERVER_ERROR));
        when(emailCommunication.sendMessage(recipient, message)).thenReturn(status);

        status = messageService.sendMessage(recipient.getLogin(), message, communication);

        verify(recipientService, times(1)).findRecipientByLogin(recipient.getLogin());
        verify(strategyFactory, times(1)).getSenderMap();
        verify(telegramCommunication, times(1)).sendMessage(recipient, message);
        verify(emailCommunication, times(1)).sendMessage(recipient, message);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), status);
    }
}
