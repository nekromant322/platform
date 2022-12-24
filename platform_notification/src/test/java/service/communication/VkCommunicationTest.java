package service.communication;

import com.override.model.Recipient;
import com.override.service.VkService;
import com.override.service.communication.VkCommunication;
import dto.MessageDTO;
import enums.Communication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VkCommunicationTest {

    @InjectMocks
    private VkCommunication vkCommunication;

    @Mock
    private VkService vkService;

    @Test
    public void testSendMessage() {
        Recipient recipient = Recipient.builder().vkChatId("testChatId").build();
        vkCommunication.sendMessage(recipient, "test");
        verify(vkService, times(1)).sendMessage(MessageDTO.builder().chatId("testChatId").message("test").build());
    }

    @Test
    public void testUpdateRecipient() {
        Recipient recipient = Recipient.builder().build();
        assertEquals(vkCommunication.updateRecipient(recipient, "test"), Recipient.builder().vkChatId("test").build());
    }

    @Test
    public void testGetType() {
        assertEquals(Communication.VK, vkCommunication.getType());
    }
}
