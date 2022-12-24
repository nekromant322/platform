package service;

import com.override.exception.SmsRuException;
import com.override.feign.SmsRuFeign;
import com.override.service.SmsRuServiceImpl;
import dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SmsRuServiceImplTest {

    @InjectMocks
    private SmsRuServiceImpl smsRuService;

    @Mock
    private SmsRuFeign smsRuFeign;

    @Value("${sms.api.id}")
    private String apiID;

    @Test
    public void testVerifyNumber() {
        CodeCallResponseDTO codeCallResponseDTO = new CodeCallResponseDTO();
        codeCallResponseDTO.setCode("1234");
        when(smsRuFeign.verifyPhone("test", apiID)).thenReturn(codeCallResponseDTO);
        assertEquals("1234", smsRuService.verifyNumber("test"));
    }

    @Test
    public void testGetBalance() {
        SmsRuBalanceResponseDTO smsRuBalanceResponseDTO = new SmsRuBalanceResponseDTO();
        smsRuBalanceResponseDTO.setBalance(99.99);
        when(smsRuFeign.getBalance(apiID)).thenReturn(smsRuBalanceResponseDTO);
        assertEquals(99.99, smsRuService.getBalance());
    }

    @Test
    public void testSendSmsWithBadStatus() {
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setStatus("NOK");
        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setPhoneDTOS("test", phoneDTO);
        SmsResponseDTO smsResponseDTO = new SmsResponseDTO();
        smsResponseDTO.setSms(smsDTO);
        when(smsRuFeign.sendSms("test", "test", apiID)).thenReturn(smsResponseDTO);
        assertThrows(SmsRuException.class, () -> smsRuService.sendSms("test", "test"));
    }
}
