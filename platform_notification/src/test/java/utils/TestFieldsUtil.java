package utils;

import com.override.model.Recipient;
import com.override.service.communication.CommunicationStrategy;
import com.override.service.communication.EmailCommunication;
import com.override.service.communication.SmsCommunication;
import com.override.service.communication.TelegramCommunication;
import dto.RecipientDTO;
import enums.Communication;

import java.util.HashMap;
import java.util.Map;

public class TestFieldsUtil {

    public static Map<Communication, CommunicationStrategy> getSenderMap(
            TelegramCommunication telegramCommunication,
            EmailCommunication emailCommunication, SmsCommunication smsCommunication) {
        Map<Communication, CommunicationStrategy> senderMap = new HashMap<>();
        senderMap.put(Communication.EMAIL, emailCommunication);
        senderMap.put(Communication.TELEGRAM, telegramCommunication);
        senderMap.put(Communication.SMS, smsCommunication);
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
