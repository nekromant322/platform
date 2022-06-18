package com.override.mapper;

import dto.CodeTryStatDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.override.utils.TestFieldsUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CodeTryStatMapperTest {
    @InjectMocks
    CodeTryStatMapper codeTryStatMapper;

    @Test
    void entityToDto() {
        final List<Integer[]> integers = generateListInteger();
        final List<Object[]> objects = generateListObjects();
        final List<Long[]> longs = generateListLong();
        CodeTryStatDTO codeTryStatDTO = codeTryStatMapper.entityToDto(integers,
                objects,
                objects,
                longs);

        assertEquals(codeTryStatDTO.getHardTasks(), codeTryStatMapper.listToMapHardTask(integers));
        assertEquals(codeTryStatDTO.getCodeTryStatus(), codeTryStatMapper.listToMapCodeTryStatus(objects));
        assertEquals(codeTryStatDTO.getStudentsCodeTry(), codeTryStatMapper.listToMapStudentCodeTry(objects));
        assertEquals(codeTryStatDTO.getHardStepRatio(), codeTryStatMapper.listToMapAllTriesRatio(longs));
    }

    @Test
    void listToMapHardTask() {
        final List<Integer[]> integers = generateListInteger();

        final Map<String, Long> mapHardTask = codeTryStatMapper.listToMapHardTask(integers);

        assertEquals(mapHardTask.get("1.2.3"), Long.valueOf(integers.get(0)[3]));
    }

    @Test
    void listToMapStudentCodeTry() {
        final List<Object[]> objects = generateListObjects();

        final Map<String, BigInteger> studentCodeTry = codeTryStatMapper.listToMapStudentCodeTry(objects);

        assertEquals(studentCodeTry.get("testUser"), objects.get(0)[1]);
    }

    @Test
    void listToMapCodeTryStatus() {
        final List<Object[]> objects = generateListObjects();

        final Map<String, BigInteger> codeTryStatus = codeTryStatMapper.listToMapCodeTryStatus(objects);

        assertEquals(codeTryStatus.get("testUser"), objects.get(0)[1]);
    }

    @Test
    void listToMapAllTriesRatio() {
        final List<Long[]> longs = generateListLong();

        final Map<String, Double> triesRatio = codeTryStatMapper.listToMapAllTriesRatio(longs);

        assertEquals(triesRatio.get("1.2"), Double.valueOf(longs.get(0)[2])/ Double.valueOf(longs.get(0)[3]));
    }
}