package br.feevale.labex.controller.data;

import br.feevale.labex.model.Knowledge;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by grimmjowjack on 8/14/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)//Como o nome diz, any prop who don't match will be ignored
public class KnowledgesData {

    private Long idArea;
    private String name;
    private String subject;
    private String degree;

    public KnowledgesData() {
    }

    public KnowledgesData(Knowledge knowledge, String subject){
        this.idArea = knowledge.getId().getArea().getId();
        this.name = knowledge.getId().getArea().getName();
        this.subject = subject;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

}
