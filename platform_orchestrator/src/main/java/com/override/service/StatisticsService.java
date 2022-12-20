package com.override.service;

import com.override.mapper.CodeTryStatMapper;
import com.override.mapper.GeneralIncomeMapper;
import com.override.mapper.IncomeFromUsersMapper;
import com.override.mapper.InterviewReportMapper;
import com.override.model.InterviewReport;
import com.override.model.Payment;
import com.override.model.enums.Status;
import com.override.repository.CodeTryRepository;
import com.override.repository.InterviewReportRepository;
import com.override.repository.PaymentRepository;
import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.MONTHS;

@Service
public class StatisticsService {
    @Autowired
    private CodeTryRepository codeTryRepository;
    @Autowired
    private CodeTryStatMapper codeTryStatMapper;
    @Autowired
    private InterviewReportRepository interviewReportRepository;
    @Autowired
    private InterviewReportMapper interviewReportMapper;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private IncomeFromUsersMapper incomeFromUsersMapper;
    @Autowired
    private GeneralIncomeMapper generalIncomeMapper;

    public CodeTryStatDTO getCodeTryStatistics(int size) {
        return codeTryStatMapper.entityToDto(codeTryRepository.countStatsOfHardTasks(size),
                codeTryRepository.countStatsOfUsers(), codeTryRepository.countStatsOfStatus(),
                codeTryRepository.countCodeTryByChapterAndStep());
    }

    public Map<String, Long> countStatsOfHardTasks(int size) {
        return codeTryStatMapper.listToMapHardTask(
                codeTryRepository.countStatsOfHardTasks(size));
    }

    public SalaryDTO getSalaryStatistics() {
        LocalDate firstSalaryDate = interviewReportRepository.findAll().stream().min(Comparator.comparing(InterviewReport::getDate)).get().getDate();

        List<LocalDate> labels = Stream.iterate(firstSalaryDate, date -> date.plus(1, MONTHS))
                .limit(MONTHS.between(firstSalaryDate, LocalDate.now()) + 1)
                .collect(Collectors.toList());

        List<SalaryStatDTO> userSalaries = new ArrayList<>();

        List<String> allUserNames = interviewReportRepository.findAllByStatus(Status.ACCEPTED)
                .stream()
                .map(InterviewReport::getUserLogin)
                .distinct()
                .collect(Collectors.toList());

        for (String userLogin : allUserNames) {
            List<Integer> salaries = new ArrayList<>();
            List<InterviewReport> allStudentSalaries = interviewReportRepository.findAllByUserLoginAndStatus(userLogin, Status.ACCEPTED);

            for (LocalDate label : labels) {
                int salaryIfNotMatched = salaries.size() > 0 ? salaries.get(salaries.size() - 1) : 0;
                Integer salaryForLabel = allStudentSalaries.stream()
                        .filter(salary -> salary.getDate().getYear() == label.getYear() &&
                                salary.getDate().getMonthValue() == label.getMonthValue())
                        .map(InterviewReport::getMaxSalary)
                        .findFirst()
                        .orElse(salaryIfNotMatched);

                salaries.add(salaryForLabel);
            }
            userSalaries.add(interviewReportMapper.loginAndSalariesToDto(userLogin, salaries));
        }
        return interviewReportMapper.salaryStatDtoToSalaryDto(labels, userSalaries);
    }

    public IncomeFromUsersDTO getAllPayment() {
        List<String> studentsName = paymentRepository.findDistinctStudentNameValues();
        List<Long> sum = paymentRepository.findSumForStudent();

        return incomeFromUsersMapper.entityToDto(studentsName, sum);
    }

    public GeneralIncomeDTO getGeneralPayment() {
        List<Double> income = new ArrayList<>();
        LocalDate firstPaymentDate = paymentRepository.findFirstDate();
        LocalDate firstMonthPayment = LocalDate.of(firstPaymentDate.getYear(), firstPaymentDate.getMonth(), 1);

        List<LocalDate> labels = Stream.iterate(firstMonthPayment, date -> date.plus(1, MONTHS))
                .limit(MONTHS.between(firstMonthPayment, LocalDate.now()) + 1)
                .collect(Collectors.toList());
        labels.forEach(label -> income.add(paymentRepository.getAllBetweenDates(label, label.plusMonths(1).minusDays(1))
                .stream()
                .map(Payment::getSum)
                .mapToDouble(Double::doubleValue)
                .sum()));
        return generalIncomeMapper.entityToDto(labels, income);
    }
}
