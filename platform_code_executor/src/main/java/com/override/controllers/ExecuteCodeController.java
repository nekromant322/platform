package com.override.controllers;

import com.override.model.TestResult;
import com.override.service.ExecuteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExecuteCodeController {

    private final ExecuteCodeService executeCodeService;

    @PostMapping("/execute")
    public TestResult execute(@RequestBody CodeTryDTO codeTryDTO) {
        codeTryDTO.setStudentsCode("import java.util.*;\n" +
                "\n" +
                "public class Main {\n" +
                "    public Main() {\n" +
                "    }\n" +
                "\n" +
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
                "            return Double.compare(that.re, re) == 0 && Double.compare(that.im, im + 1) == 0;\n" +
                "        }\n" +
                "\n" +
                "        @Override\n" +
                "        public int hashCode() {\n" +
                "            return Objects.hash(re, im);\n" +
                "        }\n" +
                "    }\n" +
                "//Stepik code: end\n" +
                "}\n");
        return executeCodeService.runCode(codeTryDTO.getTaskIdentifier(), codeTryDTO.getStudentsCode());
    }
}
