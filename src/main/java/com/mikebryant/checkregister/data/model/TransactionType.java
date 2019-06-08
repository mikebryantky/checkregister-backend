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

    public final static String WITHDRAWAL = "32b1e209-ee18-4340-b00c-b6fa64a80e33";
    public final static String DEPOSIT = "8c8fa709-d826-4613-9fbd-2d9fd6554c1b";


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
