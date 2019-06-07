package com.mikebryant.checkregister.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
public class TransactionCategory {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String uuid;

    private String description;

    @OneToMany(mappedBy = "transactionCategory")
    @JsonIgnore
    private Collection<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "transaction_type_uuid", referencedColumnName = "uuid", nullable = false)
    private TransactionType transactionType;

}
