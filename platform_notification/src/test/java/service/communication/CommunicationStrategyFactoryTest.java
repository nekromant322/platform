package service.communication;

import com.override.service.communication.CommunicationStrategy;
import com.override.service.communication.CommunicationStrategyFactory;
import com.override.service.communication.SmsCommunication;
import com.override.service.communication.VkCommunication;
import enums.CommunicationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CommunicationStrategyFactoryTest {

    VkCommunication vkCommunication = new VkCommunication();

    SmsCommunication smsCommunication = new SmsCommunication();

    @InjectMocks
    private CommunicationStrategyFactory communicationStrategyFactory;

    @BeforeEach
    void before() {
        ReflectionTestUtils.setField(communicationStrategyFactory, "strategyList", List.of(vkCommunication, smsCommunication));
    }

    @Test
    public void testGetSenderMap() {
        Map<CommunicationType, CommunicationStrategy> wanted = new HashMap<>();
        wanted.put(CommunicationType.VK, vkCommunication);
        wanted.put(CommunicationType.SMS, smsCommunication);
        assertEquals(wanted, communicationStrategyFactory.getSenderMap());
    }
}
