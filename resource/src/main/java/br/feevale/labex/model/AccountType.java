package br.feevale.labex.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 0126128 on 29/05/2015.
 */
@Entity(name = "account_type")
public class AccountType implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 25)
    private String type;

    public AccountType() {}

    public AccountType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
