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

import java.io.*;
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

            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + new File("D:\\platform\\" + "act" + personalData.getActNumber() + ".pdf"));

        } catch (DocumentException | IOException e) {
            log.warn("При создании акта №" + personalData.getActNumber() + " произошла ошибка!");
        } catch (NullPointerException e) {
            throw new InvalidPersonalDataException("Данные пользователя содержат пустые поля");
        }
    }

    public Context contextCreation(PersonalData personalData) {

        Context context = new Context();

        context.setVariable("actNumber", personalData.getActNumber());
        context.setVariable("contractNumber", personalData.getContractNumber());
        context.setVariable("contractDate", personalData.getContractDate().toString());
        context.setVariable("fullName", personalData.getFullName());
        context.setVariable("passportSeries", personalData.getPassportSeries());
        context.setVariable("passportNumber", personalData.getPassportNumber());
        context.setVariable("passportIssued", personalData.getPassportIssued());
        context.setVariable("issueDate", personalData.getIssueDate().toString());
        context.setVariable("birthDate", personalData.getBirthDate().toString());
        context.setVariable("registration", personalData.getRegistration());
        context.setVariable("email", personalData.getEmail());
        context.setVariable("phoneNumber", personalData.getPhoneNumber());
        context.setVariable("actualDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        return context;
    }
}