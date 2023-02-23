package service;

import com.override.exception.SendMessageException;
import com.override.exception.SmsRuException;
import com.override.model.Recipient;
import com.override.service.MessageService;
import com.override.service.RecipientService;
import com.override.service.communication.*;
import enums.CommunicationType;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private SmsCommunication smsCommunication;

    @Mock
    private CommunicationStrategyFactory strategyFactory;

    @Test
    public void testWhenSendMessage() {
        Recipient recipient = getRecipient();
        String message = "test";
        CommunicationType[] communicationType = {CommunicationType.TELEGRAM, CommunicationType.EMAIL};
        Map<CommunicationType, CommunicationStrategy> strategyMap = getSenderMap(telegramCommunication, emailCommunication, smsCommunication);

        when(recipientService.findRecipientByLogin(recipient.getLogin())).thenReturn(recipient);
        when(strategyFactory.getSenderMap()).thenReturn(strategyMap);
        doNothing().when(telegramCommunication).sendMessage(recipient, message);

        messageService.sendMessage(recipient.getLogin(), message, communicationType);

        verify(recipientService, times(1)).findRecipientByLogin(recipient.getLogin());
        verify(strategyFactory, times(1)).getSenderMap();
        verify(telegramCommunication, times(1)).sendMessage(recipient, message);
    }

    @Test
    public void testWhenCommunicationTypesEmpty() {
        Recipient recipient = getRecipient();
        String message = "test";

        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(recipient.getLogin(), message));
    }

    @Test
    public void testWhenRecipientNotFound() {
        String message = "test";
        CommunicationType[] communicationType = {CommunicationType.TELEGRAM, CommunicationType.EMAIL};

        when(recipientService.findRecipientByLogin("test")).thenThrow(NoSuchElementException.class);

        assertThatThrownBy(() -> messageService.sendMessage("test", message, communicationType))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testWhenFirstTypeCommunicationReturnError() {
        Recipient recipient = getRecipient();
        String message = "test";
        CommunicationType[] communicationType = {CommunicationType.TELEGRAM, CommunicationType.EMAIL};
        Map<CommunicationType, CommunicationStrategy> strategyMap = getSenderMap(telegramCommunication, emailCommunication, smsCommunication);

        when(recipientService.findRecipientByLogin(recipient.getLogin())).thenReturn(recipient);
        when(strategyFactory.getSenderMap()).thenReturn(strategyMap);
        doThrow(FeignException.class).when(telegramCommunication).sendMessage(recipient, message);
        doNothing().when(emailCommunication).sendMessage(recipient, message);

        messageService.sendMessage(recipient.getLogin(), message, communicationType);

        verify(recipientService, times(1)).findRecipientByLogin(recipient.getLogin());
        verify(strategyFactory, times(1)).getSenderMap();
        assertThatThrownBy(() -> telegramCommunication.sendMessage(recipient, message))
                .isInstanceOf(FeignException.class);
        verify(emailCommunication, times(1)).sendMessage(recipient, message);
    }

    @Test
    public void testWhenAllTypeCommunicationReturnError() {
        Recipient recipient = getRecipient();
        String message = "test";
        CommunicationType[] communicationType = {CommunicationType.TELEGRAM, CommunicationType.SMS};
        Map<CommunicationType, CommunicationStrategy> strategyMap = getSenderMap(telegramCommunication, emailCommunication, smsCommunication);

        when(recipientService.findRecipientByLogin(recipient.getLogin())).thenReturn(recipient);
        when(strategyFactory.getSenderMap()).thenReturn(strategyMap);
        doThrow(FeignException.class).when(telegramCommunication).sendMessage(recipient, message);
        doThrow(MailSendException.class).when(emailCommunication).sendMessage(recipient, message);
        doThrow(SmsRuException.class).when(smsCommunication).sendMessage(recipient, message);

        assertThrows(FeignException.class, () -> telegramCommunication.sendMessage(recipient, message));
        assertThrows(MailSendException.class, () -> emailCommunication.sendMessage(recipient, message));
        assertThrows(SmsRuException.class, () -> smsCommunication.sendMessage(recipient, message));
        assertThatThrownBy(() -> messageService.sendMessage(recipient.getLogin(), message, communicationType)).isInstanceOf(SendMessageException.class);
    }

    @Test
    public void testCheckNotificationMethods() {
        List<CommunicationType> communicationTypes = new ArrayList<>();
        communicationTypes.add(CommunicationType.EMAIL);
        communicationTypes.add(CommunicationType.SMS);
        communicationTypes.add(CommunicationType.TELEGRAM);
        communicationTypes.add(CommunicationType.VK);
        when(recipientService.findRecipientByLogin(any())).thenReturn(new Recipient(1L, "test", "test", "test", "test", "test"));
        List<CommunicationType> result = messageService.checkNotificationMethods("test");
        assertEquals(communicationTypes, result);
    }
}
