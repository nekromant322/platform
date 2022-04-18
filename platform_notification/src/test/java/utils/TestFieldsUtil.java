package utils;

import com.override.models.Recipient;
import com.override.util.CommunicationStrategy;
import com.override.util.EmailCommunication;
import com.override.util.TelegramCommunication;
import dtos.RecipientDTO;
import enums.Communication;

import java.util.HashMap;
import java.util.Map;

public class TestFieldsUtil {

    public static Map<Communication, CommunicationStrategy> getSenderMap(
            TelegramCommunication telegramCommunication,
            EmailCommunication emailCommunication) {
        Map<Communication, CommunicationStrategy> senderMap = new HashMap<>();
        senderMap.put(Communication.EMAIL, emailCommunication);
        senderMap.put(Communication.TELEGRAM, telegramCommunication);
        return senderMap;
    }

    public static Recipient getRecipient() {
        return Recipient.builder()
                .login("admin")
                .telegramId("123")
                .email("123@mail.ru")
                .build();
    }

    public static RecipientDTO getRecipientDto() {
        return RecipientDTO.builder()
                .login("admin")
                .telegramId("123")
                .email("123@mail.ru")
                .build();
    }
}
