package com.override.controllers;

import com.override.service.ExecuteCodeService;
import dtos.CodeTryDTO;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExecuteCodeController {

    private final ExecuteCodeService executeCodeService;

    @PostMapping("/execute")
    public TestResultDTO execute(@RequestBody CodeTryDTO codeTryDTO) {
//        codeTryDTO.setStudentsCode(
//                "    public final class ComplexNumber {\n" +
//                "        private final double re;\n" +
//                "        private final double im;\n" +
//                "\n" +
//                "        public ComplexNumber(double re, double im) {\n" +
//                "            this.re = re;\n" +
//                "            this.im = im;\n" +
//                "        }\n" +
//                "\n" +
//                "        public double getRe() {\n" +
//                "            return re;\n" +
//                "        }\n" +
//                "\n" +
//                "        public double getIm() {\n" +
//                "            return im;\n" +
//                "        }\n" +
//                "\n" +
//                "        @Override\n" +
//                "        public boolean equals(Object o) {\n" +
//                "            if (this == o) return true;\n" +
//                "            if (o == null || getClass() != o.getClass()) return false;\n" +
//                "            ComplexNumber that = (ComplexNumber) o;\n" +
//                "            return Double.compare(that.re, re) == 0 && Double.compare(that.im, im) == 0;\n" +
//                "        }\n" +
//                "\n" +
//                "        @Override\n" +
//                "        public int hashCode() {\n" +
//                "            return Objects.hash(re, im);\n" +
//                "        }\n" +
//                "    }\n");
        codeTryDTO.setTaskIdentifier(TaskIdentifierDTO.builder()
                .chapter(1)
                .step(2)
                .lesson(13)
                .build());
        codeTryDTO.setStudentsCode("public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"It's alive! It's alive!\");\n" +
                "    }\n" +
                "}");

        return executeCodeService.runCode(codeTryDTO.getTaskIdentifier(), codeTryDTO.getStudentsCode());
    }
}
