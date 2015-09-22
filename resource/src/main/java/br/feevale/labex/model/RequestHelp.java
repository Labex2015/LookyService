package br.feevale.labex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @class Classe que serve somente para 'pendurar' um pedido até ele ser respondido. Assim que respondido o mesmo é removido do banco.
 *
 */
@Entity(name = "request_help")
public class RequestHelp implements Serializable {

    @Transient
    public static char ACCEPTED = 'A', REJECTED = 'R', WAITING = 'W', CLOSED = 'C', OPEN = 'O';
    @Transient
    public static char GLOBAL = 'G', PRIVATE = 'P';

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @NotNull
    @ManyToOne
    protected User requester;

    @Transient
    private Long idRequester;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = true,insertable = true, updatable = true)
    private User helper;

    @Transient
    private Long idHelper;

    protected String text;

    protected String params; //TODO: quando salvar, usar ';' para separar as palavras e termos de busca

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    protected Date date;

    @Column(nullable = false)
    private char status;
    @Column(nullable = false)
    private char type;

    @OneToOne
    @JoinColumn(name = "request_global")
    private RequestHelpGlobal requestGlobal;

    public RequestHelp() {}

    public RequestHelp(User requester, User helper, String text, String params, Date date) {
        this.requester = requester;
        this.text = text;
        this.params = params;
        this.date = date;
        if(getId() == null || getId() ==0){
            this.status = WAITING;
            this.type = PRIVATE;
        }
        this.helper = helper;
    }

    public RequestHelp(User requester, String text, String params, Date date, User helper, char status, char type) {
        this.requester = requester;
        this.text = text;
        this.params = params;
        this.date = date;
        this.helper = helper;
        this.status = status;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requestor) {
        this.requester = requestor;
    }

    public User getHelper() {
        return helper;
    }

    public void setHelper(User helper) {
        this.helper = helper;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setParams(String... params) {
        if(this.params == null)
            this.params = "";
        for(String s : params)
            this.params += s+";";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public RequestHelpGlobal getRequestGlobal() {
        return requestGlobal;
    }

    public void setRequestGlobal(RequestHelpGlobal requestGlobal) {
        this.requestGlobal = requestGlobal;
    }

    public Long getIdRequester() {
        if(this.requester != null)
            this.idRequester = this.requester.getId();
        return idRequester;
    }

    public void setIdRequester(Long idRequester) {
        this.idRequester = idRequester;
    }

    public Long getIdHelper() {
        if(this.helper != null)
            this.idHelper = this.helper.getId();
        return idHelper;
    }

    public void setIdHelper(Long idHelper) {
        this.idHelper = idHelper;
    }
}
