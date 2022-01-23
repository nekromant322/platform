package com.override.testing;

// Don't edit this file

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author meanmail
 */
public class MainTest {
    private static final String MESSAGE_TEMPLATE_HASHCODE = "\nComplexNumber a = new ComplexNumber(%f, %f);\n" +
            "ComplexNumber b = new ComplexNumber(%f, %f);\n" +
            "a.hashCode() == b.hashCode()";
    private static final String MESSAGE_TEMPLATE_EQUALS = "\nComplexNumber a = new ComplexNumber(%f, %f);\n" +
            "ComplexNumber b = new ComplexNumber(%f, %f);\n" +
            "a.equals(b)";
    private static Constructor<?> constructor;
    private static Class<?> mainClass;


    private static String studentsCode = "import java.util.*;\n" +
            "\n" +
            "class Main {\n" +
            "    //Stepik code: start\n" +
            "    public final class ComplexNumber {\n" +
            "        private final double re;\n" +
            "        private final double im;\n" +
            "\n" +
            "        public ComplexNumber(double re, double im) {\n" +
            "            this.re = re;\n" +
            "            this.im = im;\n" +
            "        }\n" +
            "\n" +
            "        public double getRe() {\n" +
            "            return re;\n" +
            "        }\n" +
            "\n" +
            "        public double getIm() {\n" +
            "            return im;\n" +
            "        }\n" +
            "\n" +
            "        @Override\n" +
            "        public boolean equals(Object o) {\n" +
            "            if (this == o) return true;\n" +
            "            if (o == null || getClass() != o.getClass()) return false;\n" +
            "            ComplexNumber that = (ComplexNumber) o;\n" +
            "            return Double.compare(that.re, re) == 0 && Double.compare(that.im, im) == 0;\n" +
            "        }\n" +
            "\n" +
            "        @Override\n" +
            "        public int hashCode() {\n" +
            "            return Objects.hash(re, im);\n" +
            "        }\n" +
            "    }\n" +
            "//Stepik code: end\n" +
            "}\n";

    public static void main(String[] args) throws Exception {
        File file = new File("customerClasses/Main.java");

        if (file.delete()) {
            System.out.println("File .java successfully");
        } else {
            System.out.println("Failed to delete .java the file");
        }

        file = new File("customerClasses/Main.class");

        if (file.delete()) {
            System.out.println("File .class deleted successfully");
        } else {
            System.out.println("Failed to delete .class the file");
        }
        PrintWriter out = new PrintWriter("platform_code_executor/customerClasses/Main.java");
        out.write(studentsCode);
        out.flush();
        out.close();


        Process process = Runtime.getRuntime().exec("javac platform_code_executor/customerClasses/Main.java");


        ////
//        ClassLoader.getSystemClassLoader().loadClass("Main").newInstance();


        File fileDir = new File("platform_code_executor/customerClasses");

        //convert the file to URL format
        URL url = fileDir.toURI().toURL();
        URL[] urls = new URL[] {url};

        //load this folder into Class loader
        ClassLoader cl = new URLClassLoader(urls);

        //load the Address class in 'c:\\other_classes\\'
        Class cls = cl.loadClass("Main");

        //print the location from where this class was loaded
        ProtectionDomain pDomain = cls.getProtectionDomain();
        CodeSource cSource = pDomain.getCodeSource();
        URL urlfrom = cSource.getLocation();
        System.out.println(urlfrom.getFile());



        ////
        mainClass = cls;
//        mainClass = TestUtils.getUserClass("Main");
        Class<?> complexNumberClass = TestUtils.getInnerClass(mainClass, "ComplexNumber");

        constructor = TestUtils.getConstructor(complexNumberClass,
                new int[] {Modifier.PUBLIC, 0},
                mainClass,
                Double.TYPE,
                Double.TYPE);





        /////

        Object mainInstance = mainClass.newInstance();

        Object a = TestUtils.newInstance(constructor, mainInstance, 1, 1);
        Object b = TestUtils.newInstance(constructor, mainInstance, 1, 1);

        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 1.0, 1.0, 1.0, 1.0);
        assertEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 1.0, 1.0, 1.0, 1.0);
        assertEquals(message, a, b);
//        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
//        String line;
//        while ((line = br.readLine()) != null) {
//            System.out.println(line + "\n");
//        }

//
//        Class.forName("customerClasses/Main.class");
//        mainClass = TestUtils.getUserClass("Main");
    }

    private static Class prepareClassFromSpecialFile() throws Exception {
        File fileDir = new File("customerClasses");

        //convert the file to URL format
        URL url = fileDir.toURI().toURL();
        URL[] urls = new URL[] {url};

        //load this folder into Class loader
        ClassLoader cl = new URLClassLoader(urls);

        Class cls = cl.loadClass("Main");

        //print the location from where this class was loaded
        ProtectionDomain pDomain = cls.getProtectionDomain();
        CodeSource cSource = pDomain.getCodeSource();
        URL urlfrom = cSource.getLocation();
        System.out.println(urlfrom.getFile());
        return cls;
    }


    @BeforeClass
    public static void beforeClass() throws Exception {

//        mainClass = prepareClassFromSpecialFile();
        mainClass = TestUtils.getUserClass("Main");
        Class<?> complexNumberClass = TestUtils.getInnerClass(mainClass, "ComplexNumber");

        constructor = TestUtils.getConstructor(complexNumberClass,
                new int[] {Modifier.PUBLIC, 0},
                mainClass,
                Double.TYPE,
                Double.TYPE);
    }

    @Test(timeout = 8000L)
    public void sample1() throws IllegalAccessException, InstantiationException {
        Object mainInstance = mainClass.newInstance();

        Object a = TestUtils.newInstance(constructor, mainInstance, 1, 1);
        Object b = TestUtils.newInstance(constructor, mainInstance, 1, 1);

        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 1.0, 1.0, 1.0, 1.0);
        assertEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 1.0, 1.0, 1.0, 1.0);
        assertEquals(message, a, b);
    }

    @Test(timeout = 8000L)
    public void sample2() throws IllegalAccessException, InstantiationException {
        Object mainInstance = mainClass.newInstance();

        Object a = TestUtils.newInstance(constructor, mainInstance, 1, 1);
        Object b = TestUtils.newInstance(constructor, mainInstance, 1, 2);

        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 1.0, 1.0, 1.0, 2.0);
        assertNotEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 1.0, 1.0, 1.0, 2.0);
        assertNotEquals(message, a, b);
    }

    @Test(timeout = 8000L)
    public void sample3() throws IllegalAccessException, InstantiationException {
        Object mainInstance = mainClass.newInstance();

        Object a = TestUtils.newInstance(constructor, mainInstance, 1, 1);
        Object b = TestUtils.newInstance(constructor, mainInstance, 42, 1);

        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 1.0, 1.0, 42.0, 1.0);
        assertNotEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 1.0, 1.0, 42.0, 1.0);
        assertNotEquals(message, a, b);
    }

    @Test(timeout = 8000L)
    public void sample4() throws IllegalAccessException, InstantiationException {
        Object mainInstance = mainClass.newInstance();

        Object a = TestUtils.newInstance(constructor, mainInstance, 10.25, 1.69);
        Object b = TestUtils.newInstance(constructor, mainInstance, 10.25, 1.69);

        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 10.25, 1.69, 10.25, 1.69);
        assertEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 10.25, 1.69, 10.25, 1.69);
        assertEquals(message, a, b);
    }
}