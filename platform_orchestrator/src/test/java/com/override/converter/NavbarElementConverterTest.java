package com.override.converter;

import com.override.model.domain.NavbarElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class NavbarElementConverterTest {

    @InjectMocks
    private NavbarElementConverter converter;

    @Test
    public void testConvertListOfStringToListOfNavbarElement() {
        List<String> source = List.of("Ревью /review", "Написать отчет /report");

        List<NavbarElement> result = converter.convertListOfStringToListOfNavbarElement(source);

        Assertions.assertEquals(List.of(new NavbarElement("Ревью", "/review"), new NavbarElement("Написать отчет", "/report")), result);
    }
}
