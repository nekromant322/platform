package com.override.repository;

import com.override.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {

    /*@Query(value = "SELECT DISTINCT student_name FROM payment ORDER BY student_name", nativeQuery = true)
    List<String> findDistinctStudentNameValues();*/

    @Query(value = "SELECT student_name as sum FROM payment Group BY student_name", nativeQuery = true)
    List<String> findDistinctStudentNameValues();

    @Query(value = "SELECT SUM(sum) as sum FROM payment Group BY student_name", nativeQuery = true)
    List<Long> findSumForStudent ();

    List<Payment> findAllByStudentName(String studentName);

    @Query(value = "SELECT min(date) FROM payment", nativeQuery = true)
    LocalDate findFirstDate();

    @Query(value = "SELECT * FROM payment WHERE date >= :startDate AND date <= :endDate", nativeQuery = true)
    List<Payment> getAllBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
