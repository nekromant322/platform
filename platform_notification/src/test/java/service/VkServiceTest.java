package service;

import com.override.service.VkService;
import com.vk.api.sdk.actions.Messages;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.LongpollMessages;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.responses.GetLongPollHistoryResponse;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import dto.MessageDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VkServiceTest {

    @InjectMocks
    private VkService vkService;

    @Mock
    private VkApiClient vk;

    @Mock
    private MessagesSendQuery messagesSendQuery;

    @Mock
    private Messages messages;

    @Mock
    private GroupActor groupActor;

    @Mock
    private MessagesGetLongPollHistoryQuery getLongPollHistoryQuery;

    @Mock
    private MessagesGetLongPollHistoryQuery messagesGetLongPollHistoryQuery;

    @Mock
    private GetLongPollHistoryResponse getLongPollHistoryResponse;

    @Mock
    private LongpollMessages longpollMessages;

    @Mock
    private Message message;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private Random random;

    @Mock
    private Cache.ValueWrapper cacheValueWrapper;

    @Test
    public void testSendMessage() throws ClientException, ApiException {
        MessageDTO messageDTO = MessageDTO.builder().chatId("1234").message("test").build();
        when(vk.messages()).thenReturn(messages);
        when(messages.send(groupActor)).thenReturn(messagesSendQuery);
        when(messagesSendQuery.message(messageDTO.getMessage())).thenReturn(messagesSendQuery);
        when(messagesSendQuery.userId(anyInt())).thenReturn(messagesSendQuery);
        when(messagesSendQuery.randomId(anyInt())).thenReturn(messagesSendQuery);
        doReturn(null).when(messagesSendQuery).execute();
        vkService.sendMessage(messageDTO);
        verify(messagesSendQuery, times(1)).execute();
    }

    @Test
    public void testGetMessages() throws ClientException, ApiException {
        VkService spyVkService = Mockito.spy(vkService);
        when(vk.messages()).thenReturn(messages);
        when(messages.getLongPollHistory(groupActor)).thenReturn(getLongPollHistoryQuery);
        when(getLongPollHistoryQuery.ts(1)).thenReturn(messagesGetLongPollHistoryQuery);
        when(messagesGetLongPollHistoryQuery.execute()).thenReturn(getLongPollHistoryResponse);
        when(getLongPollHistoryResponse.getMessages()).thenReturn(longpollMessages);
        when(longpollMessages.getItems()).thenReturn(List.of(message));
        doNothing().when(spyVkService).extractCode(message);
        spyVkService.getMessages(1);
        verify(spyVkService, times(1)).extractCode(message);
    }

    @Test
    public void testGetMessagesWithEmptyMessages() throws ClientException, ApiException {
        VkService spyVkService = Mockito.spy(vkService);
        when(vk.messages()).thenReturn(messages);
        when(messages.getLongPollHistory(groupActor)).thenReturn(getLongPollHistoryQuery);
        when(getLongPollHistoryQuery.ts(1)).thenReturn(messagesGetLongPollHistoryQuery);
        when(messagesGetLongPollHistoryQuery.execute()).thenReturn(getLongPollHistoryResponse);
        when(getLongPollHistoryResponse.getMessages()).thenReturn(longpollMessages);
        when(longpollMessages.getItems()).thenReturn(new ArrayList<>());
        spyVkService.getMessages(1);
    }

    @Test
    public void testGetMessagesWhenApiException() throws ClientException, ApiException {
        VkService spyVkService = Mockito.spy(vkService);
        when(vk.messages()).thenReturn(messages);
        when(messages.getLongPollHistory(groupActor)).thenReturn(getLongPollHistoryQuery);
        when(getLongPollHistoryQuery.ts(1)).thenReturn(messagesGetLongPollHistoryQuery);
        doThrow(new ApiException("test")).when(messagesGetLongPollHistoryQuery).execute();
        spyVkService.getMessages(1);
        assertThatThrownBy(() -> messagesGetLongPollHistoryQuery.execute())
                .isInstanceOf(ApiException.class);
    }

    @Test
    public void testExtractCode() {
        Message message = new Message();
        message.setText("/code 123456");
        when(cacheManager.getCache("vkChatId")).thenReturn(cache);
        doNothing().when(cache).put(anyString(), anyString());
        vkService.extractCode(message);
        verify(cache, times(1)).put(anyString(), anyString());
    }

    @Test
    public void testExtractCodeWithNoCode() throws ClientException, ApiException {
        Message message = new Message();
        message.setText("test");
        message.setFromId(1);
        when(vk.messages()).thenReturn(messages);
        when(messages.send(groupActor)).thenReturn(messagesSendQuery);
        when(messagesSendQuery.message(anyString())).thenReturn(messagesSendQuery);
        when(messagesSendQuery.userId(1)).thenReturn(messagesSendQuery);
        when(messagesSendQuery.randomId(anyInt())).thenReturn(messagesSendQuery);
        when(random.nextInt(anyInt())).thenReturn(1);
        doReturn(null).when(messagesSendQuery).execute();
        vkService.extractCode(message);
        verify(vk, times(1)).messages();
    }

    @Test
    public void testExtractCodeWhenApiException() throws ClientException, ApiException {
        Message message = new Message();
        message.setText("test");
        message.setFromId(1);
        when(vk.messages()).thenReturn(messages);
        when(messages.send(groupActor)).thenReturn(messagesSendQuery);
        when(messagesSendQuery.message(anyString())).thenReturn(messagesSendQuery);
        when(messagesSendQuery.userId(1)).thenReturn(messagesSendQuery);
        when(messagesSendQuery.randomId(anyInt())).thenReturn(messagesSendQuery);
        when(random.nextInt(anyInt())).thenReturn(1);
        doThrow(new ApiException("test")).when(messagesSendQuery).execute();
        vkService.extractCode(message);
        assertThatThrownBy(() -> messagesSendQuery.execute())
                .isInstanceOf(ApiException.class);
    }

    @Test
    public void testGenerateCode() {
        when(cacheManager.getCache("vkSecurityCode")).thenReturn(cache);
        doNothing().when(cache).put(anyString(), anyString());
        vkService.generateCode("test");
        verify(cache, times(1)).put(anyString(), anyString());
    }

    @Test
    public void testGetChatIdByCode() {
        when(cacheManager.getCache(anyString())).thenReturn(cache);
        when(cache.get(anyString())).thenReturn(cacheValueWrapper);
        when(cacheValueWrapper.get()).thenReturn("123456");
        assertEquals(123456, vkService.getChatIdByCode("test"));
    }

    @Test
    public void testGetSecurityCodeByLogin() {
        when(cacheManager.getCache(anyString())).thenReturn(cache);
        when(cache.get(anyString())).thenReturn(cacheValueWrapper);
        when(cacheValueWrapper.get()).thenReturn("123456");
        assertEquals("123456", vkService.getSecurityCodeByLogin("test"));
    }
}
