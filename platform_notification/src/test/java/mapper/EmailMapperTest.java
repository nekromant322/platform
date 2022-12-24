package mapper;

import com.override.mapper.EmailMapper;
import dto.MailDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EmailMapperTest {

    @InjectMocks
    private EmailMapper emailMapper;

    @Test
    public void testDtoToListOfSimpleMails() {
        MailDTO mailDTO = MailDTO
                .builder()
                .to(List.of("test"))
                .text("testText")
                .build();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("test");
        simpleMailMessage.setText("testText");
        
        assertEquals(List.of(simpleMailMessage), emailMapper.dtoToListOfSimpleMails(mailDTO));
    }
}
