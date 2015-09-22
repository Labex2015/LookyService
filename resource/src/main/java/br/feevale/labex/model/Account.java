package br.feevale.labex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by 0126128 on 29/05/2015.
 */
@Entity(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull@NotEmpty
    @Column(nullable = false)
    private String account;

    @Column(length = 400)
    private String token;

    @JsonIgnore
    @OneToOne(mappedBy = "account")
    private User user;

    private Boolean appStatus;
    private Boolean profileStatus;

    @ManyToOne
    private AccountType accountType;

    public Account() {}

    public Account(String account, AccountType accountType) {
        this.account = account;
        this.accountType = accountType;
    }

    public Account(String account, User user, AccountType accountType) {
        this.account = account;
        this.user = user;
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Boolean appStatus) {
        this.appStatus = appStatus;
    }

    public Boolean getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(Boolean profileStatus) {
        this.profileStatus = profileStatus;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
