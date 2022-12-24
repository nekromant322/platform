package service;

import com.override.feign.TelegramFeign;
import com.override.service.TelegramService;
import dto.MessageDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelegramServiceTest {

    @InjectMocks
    private TelegramService telegramService;

    @Mock
    private TelegramFeign telegramFeign;

    @Test
    public void testSendMessage() {
        MessageDTO messageDTO = MessageDTO.builder().chatId("test").message("test").build();
        telegramService.sendMessage(messageDTO);
        verify(telegramFeign, times(1)).sendMessage(messageDTO);
    }
}
