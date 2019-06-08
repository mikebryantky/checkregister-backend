package com.mikebryant.checkregister.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Transaction {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String uuid;


    private LocalDateTime txDate;
    private String description;
    private String checkNumber;
    private Double depositAmount;
    private Double withdrawalAmount;
    private LocalDate reconciledDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "transaction_type_uuid", referencedColumnName = "uuid", nullable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "transaction_method_uuid", referencedColumnName = "uuid", nullable = false)
    private TransactionMethod transactionMethod;

    @ManyToOne
    @JoinColumn(name = "transaction_category_uuid", referencedColumnName = "uuid", nullable = false)
    private TransactionCategory transactionCategory;

}
