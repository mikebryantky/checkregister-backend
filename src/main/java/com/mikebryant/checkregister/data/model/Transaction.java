package com.mikebryant.checkregister.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String uuid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Transaction date is required.")
    private LocalDate txDate;

    @NotEmpty(message = "Description is required.")
    private String description;

    private String checkNumber;

    @NotNull(message = "Amount is required.")
    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reconciledDate;

    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_type_uuid", referencedColumnName = "uuid", nullable = false)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_method_uuid", referencedColumnName = "uuid", nullable = false)
    private TransactionMethod transactionMethod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_category_uuid", referencedColumnName = "uuid", nullable = false)
    private TransactionCategory transactionCategory;

}
