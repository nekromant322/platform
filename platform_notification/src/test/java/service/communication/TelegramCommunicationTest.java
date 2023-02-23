package service.communication;

import com.override.model.Recipient;
import com.override.service.TelegramService;
import com.override.service.communication.TelegramCommunication;
import dto.MessageDTO;
import enums.CommunicationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TelegramCommunicationTest {

    @InjectMocks
    private TelegramCommunication telegramCommunication;

    @Mock
    private TelegramService telegramService;

    @Test
    public void testSendMessage() {
        Recipient recipient = Recipient.builder().telegramId("testChatId").build();
        telegramCommunication.sendMessage(recipient, "test");
        verify(telegramService, times(1)).sendMessage(MessageDTO.builder().message("test").chatId("testChatId").build());
    }

    @Test
    public void testUpdateRecipient() {
        Recipient recipient = Recipient.builder().build();
        assertEquals(telegramCommunication.updateRecipient(recipient, "test"), Recipient.builder().telegramId("test").build());
    }

    @Test
    public void testGetType() {
        assertEquals(CommunicationType.TELEGRAM, telegramCommunication.getType());
    }
}
