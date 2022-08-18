package dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.Map;

@Data
@Builder
@ApiModel
public class CodeTryStatDTO {
    @ApiModelProperty(value = "Мапа Map<String, Long>, где ключ(String) - номер той самой сложной задачи " +
            "(пример: 4.2.1, где 4 - глава, 2 - шаг, 1 - урок), а значение(Long) - количество неудачных решений данной задачи")
    private Map<String, Long> hardTasks;
    @ApiModelProperty(value = "Мапа Map<String, BigInteger>, где ключ(String) - название статуса по выполнению " +
            "задачи (пример: 'COMPILE_ERROR' или 'OK'), а значение(BigInteger) - количество данных статусов в таблице")
    private Map<String, BigInteger> codeTryStatus;
    @ApiModelProperty(value = "Мапа Map<String, BigInteger>, где ключ(String) - логин студента (пример: 'Raymon'), " +
            "а значение(BigInteger) - общее количество попыток решений задач для данного студента")
    private Map<String, BigInteger> studentsCodeTry;
    @ApiModelProperty(value = "Мапа Map<String, Double>, где ключ(String) - номер главы и шага (пример: 4.2, где 4 - глава, 2 - шаг), " +
            "а значение(Double) - среднее количество попыток выполнения данного шага данной главы в одном уроке, если проще, то среднее количество попыток выполнения данного шага")
    private Map<String, Double> hardStepRatio;
}
