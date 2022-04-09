package com.override.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.override.exception.InvalidPersonalDataException;
import com.override.models.PersonalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ActGenerationService {

    @Autowired
    private TemplateEngine templateEngine;

    public void createPDF(PersonalData personalData) {

        try {
            OutputStream outputStream = new FileOutputStream("act" + personalData.getActNumber() + ".pdf");

            String processHTML = templateEngine.process("docs/actGenerationTemplate",
                    contextCreation(personalData));

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processHTML);
            renderer.getFontResolver().addFont("/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();
            outputStream.close();

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
