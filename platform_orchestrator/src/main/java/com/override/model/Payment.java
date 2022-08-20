package com.override.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name")
    private String studentName;

    private LocalDate date;

    private Long sum;

    @ApiModelProperty(value = "Номер рассчетного счета, куда делается платеж (номер карты получателя платежа)")
    @Column(name = "account_number")
    private Long accountNumber;

    private String message;
}
