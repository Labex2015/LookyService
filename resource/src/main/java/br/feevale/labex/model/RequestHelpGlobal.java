package br.feevale.labex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by 0126128 on 28/05/2015.
 */
@Entity(name = "request_help_global")
public class RequestHelpGlobal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "requestGlobal")
    private RequestHelp requestHelp;

    @NotNull@NotEmpty@Length(max = 45, min = 1)
    @Column(nullable = false, length = 45)
    private String title;

    @NotNull@NotEmpty
    @Column(nullable = false)
    private String description;//descricao do pedido de ajuda, quando for global

    public RequestHelpGlobal() {}

    public RequestHelpGlobal(RequestHelp requestHelp, String title, String description) {
        this.requestHelp = requestHelp;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestHelp getRequestHelp() {
        return requestHelp;
    }

    public void setRequestHelp(RequestHelp requestHelp) {
        this.requestHelp = requestHelp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
