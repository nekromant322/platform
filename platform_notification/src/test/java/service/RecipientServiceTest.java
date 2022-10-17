package service;

import com.override.mapper.RecipientMapper;
import com.override.model.Recipient;
import com.override.repository.RecipientRepository;
import com.override.service.RecipientService;
import com.override.service.communicationStrategy.*;
import dto.RecipientDTO;
import enums.Communication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static utils.TestFieldsUtil.*;

@ExtendWith(MockitoExtension.class)
public class RecipientServiceTest {

    @InjectMocks
    private RecipientService recipientService;

    @Mock
    private RecipientRepository recipientRepository;

    @Mock
    private RecipientMapper recipientMapper;

    @Mock
    private CommunicationStrategyFactory strategyFactory;

    @Mock
    private TelegramCommunication telegramCommunication;

    @Mock
    private EmailCommunication emailCommunication;

    @Mock
    private SmsCommunication smsCommunication;

    @Test
    public void testWhenSaveByRecipient() {
        Recipient recipient = getRecipient();
        RecipientDTO recipientDTO = getRecipientDto();

        when(recipientRepository.findRecipientByLogin(recipientDTO.getLogin())).thenReturn(Optional.empty());
        when(recipientRepository.save(recipientMapper.dtoToEntity(recipientDTO))).thenReturn(recipient);

        recipientService.save(recipientDTO);

        verify(recipientRepository, times(1)).findRecipientByLogin(recipientDTO.getLogin());
        verify(recipientRepository, times(1)).save(recipientMapper.dtoToEntity(recipientDTO));
    }

    @Test
    public void testWhenSaveByRecipientDto() {
        Recipient recipient = getRecipient();
        RecipientDTO recipientDTO = getRecipientDto();

        when(recipientRepository.findRecipientByLogin(recipientDTO.getLogin())).thenReturn(Optional.empty());
        when(recipientMapper.dtoToEntity(recipientDTO)).thenReturn(recipient);
        when(recipientRepository.save(recipient)).thenReturn(recipient);

        recipientService.save(recipientDTO);

        verify(recipientRepository, times(1)).findRecipientByLogin(recipient.getLogin());
        verify(recipientRepository, times(1)).save(recipient);
    }

    @Test
    public void testWhenUserExistByRecipientDto() {
        Recipient recipient = getRecipient();
        RecipientDTO recipientDTO = getRecipientDto();

        when(recipientRepository.findRecipientByLogin(recipientDTO.getLogin())).thenReturn(Optional.empty());
        when(recipientMapper.dtoToEntity(recipientDTO)).thenReturn(recipient);
        when(recipientRepository.save(recipient)).thenReturn(recipient);

        recipientService.save(recipientDTO);

        verify(recipientRepository, times(1)).save(recipient);
    }

    @Test
    public void testWhenUserNotExistByRecipientDto() {
        Recipient recipient = getRecipient();
        RecipientDTO recipientDTO = getRecipientDto();

        when(recipientRepository.findRecipientByLogin(recipientDTO.getLogin())).thenReturn(Optional.empty());
        when(recipientRepository.save(recipientMapper.dtoToEntity(recipientDTO))).thenReturn(recipient);

        recipientService.save(recipientDTO);

        verify(recipientRepository, times(1)).save(recipientMapper.dtoToEntity(recipientDTO));
    }

    @Test
    public void testWhenUpdateCommunication() {
        Recipient recipient = getRecipient();
        Map<Communication, CommunicationStrategy> strategyMap = getSenderMap(telegramCommunication, emailCommunication, smsCommunication);
        String value = "test";

        when(recipientRepository.findRecipientByLogin(recipient.getLogin())).thenReturn(Optional.of(recipient));
        when(strategyFactory.getSenderMap()).thenReturn(strategyMap);
        recipient.setTelegramId(value);
        when(telegramCommunication.updateRecipient(recipient, value)).thenReturn(recipient);
        when(recipientRepository.save(recipient)).thenReturn(recipient);

        recipientService.updateCommunication(recipient.getLogin(), value, Communication.TELEGRAM);

        verify(recipientRepository, times(1)).findRecipientByLogin(recipient.getLogin());
        verify(recipientRepository, times(1)).save(recipient);
    }

    @Test
    public void testWhenDeleteRecipient() {
        RecipientDTO recipientDTO = getRecipientDto();

        recipientService.delete(recipientDTO);

        verify(recipientRepository, times(1)).delete(recipientMapper.dtoToEntity(recipientDTO));
    }

    @Test
    public void testWhenFindRecipientByLogin() {
        Recipient recipient = getRecipient();

        when(recipientRepository.findRecipientByLogin(recipient.getLogin())).thenReturn(Optional.of(recipient));

        recipientService.findRecipientByLogin(recipient.getLogin());

        verify(recipientRepository, times(1)).findRecipientByLogin(recipient.getLogin());
    }
}
