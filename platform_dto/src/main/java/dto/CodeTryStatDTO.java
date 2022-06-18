package dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.Map;

@Data
@Builder
public class CodeTryStatDTO {
    private Map<String, Long> hardTasks;
    private Map<String, BigInteger> codeTryStatus;
    private Map<String, BigInteger> studentsCodeTry;
    private Map<String, Double> hardStepRatio;
}
