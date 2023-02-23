package utils;

import com.override.model.Recipient;
import com.override.service.communication.CommunicationStrategy;
import com.override.service.communication.EmailCommunication;
import com.override.service.communication.SmsCommunication;
import com.override.service.communication.TelegramCommunication;
import dto.RecipientDTO;
import enums.CommunicationType;

import java.util.HashMap;
import java.util.Map;

public class TestFieldsUtil {

    public static Map<CommunicationType, CommunicationStrategy> getSenderMap(
            TelegramCommunication telegramCommunication,
            EmailCommunication emailCommunication, SmsCommunication smsCommunication) {
        Map<CommunicationType, CommunicationStrategy> senderMap = new HashMap<>();
        senderMap.put(CommunicationType.EMAIL, emailCommunication);
        senderMap.put(CommunicationType.TELEGRAM, telegramCommunication);
        senderMap.put(CommunicationType.SMS, smsCommunication);
        return senderMap;
    }

    public static Recipient getRecipient() {
        return Recipient.builder()
                .login("admin")
                .telegramId("123")
                .email("123@mail.ru")
                .phoneNumber("79998887766")
                .build();
    }

    public static RecipientDTO getRecipientDto() {
        return RecipientDTO.builder()
                .login("admin")
                .telegramId("123")
                .email("123@mail.ru")
                .phoneNumber("79998887766")
                .build();
    }
}
