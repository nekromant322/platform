package service.communication;

import com.override.service.RecipientService;
import com.override.service.communication.InitializationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InitializationServiceTest {

    @InjectMocks
    private InitializationService initializationService;

    @Mock
    private RecipientService recipientService;

    @Test
    public void testCreateRecipientDTO() {
        doNothing().when(recipientService).save(any());
        initializationService.createRecipientDTO();
        verify(recipientService, times(1)).save(any());
    }
}
