package com.override.service;

import com.itextpdf.text.DocumentException;
import com.override.exception.InvalidPersonalDataException;
import com.override.model.PersonalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ActGenerationService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ITextRenderer iTextRenderer;

    public void createPDF(PersonalData personalData) {

        try (OutputStream outputStream = new FileOutputStream("act" + personalData.getActNumber() + ".pdf")) {

            String processHTML = templateEngine.process("docs/actGenerationTemplate",
                    contextCreation(personalData));

            iTextRenderer.setDocumentFromString(processHTML);
            iTextRenderer.layout();
            iTextRenderer.createPDF(outputStream, false);
            iTextRenderer.finishPDF();
        } catch (DocumentException | IOException e) {
            log.warn("При создании акта №" + personalData.getActNumber() + " произошла ошибка!");
        } catch (NullPointerException e) {
            throw new InvalidPersonalDataException("Данные пользователя содержат пустые поля");
        }
    }

    public Context contextCreation(PersonalData personalData) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        Context context = new Context();

        context.setVariable("actNumber", personalData.getActNumber());
        context.setVariable("contractNumber", personalData.getContractNumber());
        context.setVariable("date", formatter.format(personalData.getDate()));
        context.setVariable("fullName", personalData.getFullName());
        context.setVariable("passportSeries", personalData.getPassportSeries());
        context.setVariable("passportNumber", personalData.getPassportNumber());
        context.setVariable("passportIssued", personalData.getPassportIssued());
        context.setVariable("issueDate", formatter.format(personalData.getIssueDate()));
        context.setVariable("birthDate", formatter.format(personalData.getBirthDate()));
        context.setVariable("registration", personalData.getRegistration());
        context.setVariable("email", personalData.getEmail());
        context.setVariable("phoneNumber", personalData.getPhoneNumber());
        context.setVariable("actualDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        return context;
    }
}
