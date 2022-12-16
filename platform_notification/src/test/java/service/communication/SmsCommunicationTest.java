package service.communication;

import com.override.model.Recipient;
import com.override.service.SmsRuServiceImpl;
import com.override.service.communication.SmsCommunication;
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
public class SmsCommunicationTest {

    @InjectMocks
    private SmsCommunication smsCommunication;

    @Mock
    private SmsRuServiceImpl smsRuService;

    @Test
    public void testSendMessage() {
        Recipient recipient = Recipient.builder().login("test").phoneNumber("testPhone").build();
        smsCommunication.sendMessage(recipient, "testMessage");
        verify(smsRuService, times(1)).sendSms("testPhone", "testMessage");
    }

    @Test
    public void testUpdateRecipient() {
        Recipient recipient = Recipient.builder().build();
        assertEquals(smsCommunication.updateRecipient(recipient, "test"), Recipient.builder().phoneNumber("test").build());
    }

    @Test
    public void testGetType() {
        assertEquals(Communication.SMS, smsCommunication.getType());
    }
}
