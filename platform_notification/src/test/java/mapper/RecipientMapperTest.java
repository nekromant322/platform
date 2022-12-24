package mapper;

import com.override.mapper.RecipientMapper;
import com.override.model.Recipient;
import dto.RecipientDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RecipientMapperTest {

    @InjectMocks
    private RecipientMapper recipientMapper;

    @Test
    public void testEntityToDto() {
        Recipient recipient = Recipient
                .builder()
                .email("testEmail")
                .telegramId("testTelegram")
                .vkChatId("testVk")
                .phoneNumber("testNumber")
                .login("testLogin")
                .build();

        RecipientDTO recipientDTO = RecipientDTO
                .builder()
                .email("testEmail")
                .telegramId("testTelegram")
                .vkChatId("testVk")
                .phoneNumber("testNumber")
                .login("testLogin")
                .build();

        assertEquals(recipientDTO, recipientMapper.entityToDto(recipient));
    }

    @Test
    public void testDtoToEntity() {
        Recipient recipient = Recipient
                .builder()
                .email("testEmail")
                .telegramId("testTelegram")
                .vkChatId("testVk")
                .phoneNumber("testNumber")
                .login("testLogin")
                .build();

        RecipientDTO recipientDTO = RecipientDTO
                .builder()
                .email("testEmail")
                .telegramId("testTelegram")
                .vkChatId("testVk")
                .phoneNumber("testNumber")
                .login("testLogin")
                .build();

        assertEquals(recipient, recipientMapper.dtoToEntity(recipientDTO));
    }
}
