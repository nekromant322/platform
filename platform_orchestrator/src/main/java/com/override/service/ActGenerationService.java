package com.override.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
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
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ActGenerationService {

    @Autowired
    private TemplateEngine templateEngine;

    public void createPDF(PersonalData personalData) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Map<String,Object> variables = new HashMap<>();
        variables.put("actNumber", personalData.getActNumber());
        variables.put("contractNumber", personalData.getContractNumber());
        variables.put("date", formatter.format(personalData.getDate()));
        variables.put("fullName", personalData.getFullName());
        variables.put("passportSeries", personalData.getPassportSeries());
        variables.put("passportNumber", personalData.getPassportNumber());
        variables.put("passportIssued", personalData.getPassportIssued());
        variables.put("issueDate", formatter.format(personalData.getIssueDate()));
        variables.put("birthDate", formatter.format(personalData.getBirthDate()));
        variables.put("registration", personalData.getRegistration());
        variables.put("email", personalData.getEmail());
        variables.put("phoneNumber", personalData.getPhoneNumber());
        variables.put("actualDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        Context context = new Context();
        context.setVariables(variables);

        try {
            OutputStream outputStream = new FileOutputStream("act" + personalData.getActNumber() + ".pdf");
            String processHTML = templateEngine.process("actGenerationTemplate", context);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processHTML);
            renderer.getFontResolver().addFont("/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.createPDF(outputStream, false);
            renderer.layout();
            renderer.finishPDF();
            outputStream.close();
        } catch (DocumentException | IOException e) {
            log.warn("При создании акта №" + personalData.getActNumber() + " произошла ошибка!");
        }
    }
}
