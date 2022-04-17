package utils;

import com.override.models.Recipient;
import com.override.util.CommunicationStrategy;
import com.override.util.EmailCommunication;
import com.override.util.TelegramCommunication;
import enums.Communication;

import java.util.HashMap;
import java.util.Map;

public class TestFieldsUtil {

    public static Map<Communication, CommunicationStrategy> getSenderMap(
            CommunicationStrategy strategy,
            TelegramCommunication telegramCommunication,
            EmailCommunication emailCommunication) {
        Map<Communication, CommunicationStrategy> senderMap = new HashMap<>();

        strategy = telegramCommunication;
        senderMap.put(Communication.TELEGRAM, strategy);
        strategy = emailCommunication;
        senderMap.put(Communication.EMAIL, strategy);
        return senderMap;
    }

    public static Recipient getRecipient() {
        return Recipient.builder()
                .login("admin")
                .telegramId("123")
                .email("123@mail.ru")
                .build();
    }
}
