import com.override.feign.StepikCodeExecutorFeign;
import com.override.service.StepikService;
import dto.CodeTryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StepikServiceTest {

    @InjectMocks
    private StepikService stepikService;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private StepikCodeExecutorFeign stepikCodeExecutorFeign;

    @Mock
    private Cache cache;

    @Mock
    private Cache.ValueWrapper cacheValue;

    @Test
    public void testRunStepikCode(){
        CodeTryDTO codeTryDTO = CodeTryDTO.builder().studentsCode("test").attempt("1").step("4").build();
        when(cacheManager.getCache("stepikToken")).thenReturn(cache);
        when(cache.get("token")).thenReturn(cacheValue);
        when(stepikService.getStepikToken()).thenReturn("12345");
        when(cacheValue.get()).thenReturn("12345");
        when(stepikCodeExecutorFeign.getResult("4","12345")).thenReturn("correct");
        stepikService.runStepikCode(codeTryDTO);
        verify(stepikCodeExecutorFeign,times(1)).execute("12345", stepikService.getRequest(codeTryDTO));
        verify(stepikCodeExecutorFeign,times(1)).getResult("4","12345");
    }
}
