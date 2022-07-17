package com.override.service;

import com.override.exception.InvalidPersonalDataException;
import com.override.model.PersonalData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import static com.override.utils.TestFieldsUtil.generateContextByPersonalData;
import static com.override.utils.TestFieldsUtil.generatePersonalData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ActGenerationServiceTest {

    @InjectMocks
    private ActGenerationService actGenerationService;

    @Mock
    private ITemplateEngine templateEngine;

    @Mock
    private ITextRenderer iTextRenderer;

    @Mock
    private PersonalData personalData;


    /*need help
    @Test
    void testCreatePDF() throws DocumentException, IOException {
        PersonalData personalData = generatePersonalData();

        String processHTML = templateEngine.process("docs/actGenerationTemplate",
                contextCreation(personalData));
        when(templateEngine.process(eq("docs/actGenerationTemplate"), any())).thenReturn("qwe");
        when(LocalDate.now()).thenReturn(LocalDate.parse(LocalDate.of(2022,06,10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));

        actGenerationService.createPDF(personalData);

        verify(iTextRenderer, times(1)).setDocumentFromString(any());
        verify(iTextRenderer, times(1)).layout();
        verify(iTextRenderer, times(1)).createPDF(any(), false);
        verify(iTextRenderer, times(1)).finishPDF();
    }
*/
    @Test
    void testCreatePDFWhenException() {
        assertThrows(InvalidPersonalDataException.class, () -> actGenerationService.createPDF(null));
    }

    @Test
    void testContextCreation() {

        Context contextTest = generateContextByPersonalData();

        Context context = actGenerationService.contextCreation(generatePersonalData());

        assertEquals(context.getVariable("actNumber"), contextTest.getVariable("actNumber"));
        assertEquals(context.getVariable("contactNumber"), contextTest.getVariable("contactNumber"));
        assertEquals(context.getVariable("date"), contextTest.getVariable("date"));
        assertEquals(context.getVariable("fullName"), contextTest.getVariable("fullName"));
        assertEquals(context.getVariable("passportSeries"), contextTest.getVariable("passportSeries"));
        assertEquals(context.getVariable("passportNumber"), contextTest.getVariable("passportNumber"));
        assertEquals(context.getVariable("passportIssued"), contextTest.getVariable("passportIssued"));
        assertEquals(context.getVariable("issueDate"), contextTest.getVariable("issueDate"));
        assertEquals(context.getVariable("birthDate"), contextTest.getVariable("birthDate"));
        assertEquals(context.getVariable("registration"), contextTest.getVariable("registration"));
        assertEquals(context.getVariable("email"), contextTest.getVariable("email"));
        assertEquals(context.getVariable("phoneNumber"), contextTest.getVariable("phoneNumber"));
        assertEquals(context.getVariable("actualDate"), contextTest.getVariable("actualDate"));
    }
}