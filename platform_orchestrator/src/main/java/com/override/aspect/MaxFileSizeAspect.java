package com.override.aspect;

import com.override.annotation.MaxFileSize;
import org.apache.commons.fileupload.FileUploadException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class MaxFileSizeAspect {

    @Autowired
    private Environment environment;

    private static final long MEGABYTE = 1024L * 1024L;

    private String getValueFromAnnotation(MaxFileSize maxFileSize) {
        String value = environment.getProperty(maxFileSize.value().replaceAll("[${}]*", ""));
        if (value == null) {
            return maxFileSize.value();
        }
        return value;
    }

    @Before(value = "@annotation(maxFileSize)")
    public void maxFileSizeException(JoinPoint joinPoint, MaxFileSize maxFileSize) throws FileUploadException {
        String maxFileSizeValue = getValueFromAnnotation(maxFileSize);
        List<MultipartFile> multipartFileList = new ArrayList<>();
        Arrays.stream(joinPoint.getArgs()).filter(arg -> arg instanceof MultipartFile).forEach(arg -> multipartFileList.add((MultipartFile) arg));
        long maxFileSizeByte = Long.parseLong(maxFileSizeValue) * MEGABYTE;

        for (MultipartFile file : multipartFileList) {
            if (file.getSize() > maxFileSizeByte) {
                long fileSizeMb = file.getSize() / MEGABYTE;
                throw new FileUploadException(HttpStatus.UNSUPPORTED_MEDIA_TYPE + " размер файла не должен превышать "
                        + maxFileSizeValue + "МБ, " + "а размер вашего файла '" + file.getOriginalFilename() + "' равен " + fileSizeMb + "МБ");
            }
        }
    }
}
