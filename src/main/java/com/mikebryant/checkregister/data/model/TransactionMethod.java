package com.mikebryant.checkregister.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class TransactionMethod {

    public static final String CHECK = "b71b33bf-6326-4ad5-88d9-e72a86f34ef9";


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String uuid;

    @NotEmpty(message = "Description is required.")
    private String description;

    @OneToMany(mappedBy = "transactionMethod", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();;

}
