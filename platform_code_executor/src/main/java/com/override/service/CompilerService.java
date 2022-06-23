package com.override.service;

import com.override.exception.CompilingCodeException;
import com.override.exception.LoadingClassException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class CompilerService {

    private static final String CUSTOM_CLASSES_DIR_NAME = "customClasses";
    private static final String TOP_LEVEL_HELPER_CLASS_NAME = "Main";

    public synchronized Class makeClassFromCode(String studentsCode) {
        new File("customClasses").mkdirs();
        clearCustomClasses();

        try {
            PrintWriter out = new PrintWriter(CUSTOM_CLASSES_DIR_NAME + File.separator + TOP_LEVEL_HELPER_CLASS_NAME + ".java");
            out.write(studentsCode);
            out.flush();
            out.close();

            Process process = Runtime.getRuntime().exec("javac " + CUSTOM_CLASSES_DIR_NAME + File.separator + TOP_LEVEL_HELPER_CLASS_NAME + ".java");
            //не убирать условие, потому что waitFor() не будет ждать из-за оптимизаций при компиляции
            if (process.waitFor() == 0) {
                log.info("compile done for code \n" + studentsCode);
            }
        } catch (Exception e) {
            throw new CompilingCodeException(e);
        }


        File fileDir = new File(CUSTOM_CLASSES_DIR_NAME);
        try {
            //convert the file to URL format
            URL url = fileDir.toURI().toURL();
            URL[] urls = new URL[] {url};

            //load this folder into Class loader
            ClassLoader cl = URLClassLoader.newInstance(urls);

            //load the Address class in 'c:\\other_classes\\'
            Class cls = cl.loadClass(TOP_LEVEL_HELPER_CLASS_NAME);
            //print the location from where this class was loaded
            //ProtectionDomain pDomain = cls.getProtectionDomain();
            //CodeSource cSource = pDomain.getCodeSource();
            return cls;
        } catch (Exception e) {
            throw new LoadingClassException(e);
        }
    }

    private void clearCustomClasses() {
        try {
            File file = new File(CUSTOM_CLASSES_DIR_NAME + File.separator + TOP_LEVEL_HELPER_CLASS_NAME + ".java");
            Files.delete(Paths.get(file.getAbsolutePath()));
            file = new File(CUSTOM_CLASSES_DIR_NAME + File.separator + TOP_LEVEL_HELPER_CLASS_NAME + ".class");
            Files.delete(Paths.get(file.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
