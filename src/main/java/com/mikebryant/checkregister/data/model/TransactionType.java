package com.mikebryant.checkregister.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
public class TransactionType {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String uuid;

    private String description;
    private String color;

    @OneToMany(mappedBy = "transactionType")
    @JsonIgnore
    private Collection<Transaction> transactions;

    @OneToMany(mappedBy = "transactionType")
    @JsonIgnore
    private Collection<TransactionCategory> transactionCategories;

}
