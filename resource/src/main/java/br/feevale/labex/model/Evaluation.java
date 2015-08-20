package br.feevale.labex.model;

import br.feevale.labex.controller.mod.EvaluationMod;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 0126128 on 28/05/2015.
 */
@Entity(name = "evaluation")
public class Evaluation implements Serializable {

    @Transient
    public static final int UTIL = 3; //TODO: implementar pontuacao

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer helpPoints;
    @Column(nullable = false)
    private Integer answerPoints;

    private String comment;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date evaluated;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "interaction_id")
    private Interaction interaction;

    public Evaluation() {}

    public Evaluation(Integer helpPoints, Integer answerPoints,
                      Date evaluated, Interaction interaction) {
        this.helpPoints = helpPoints;
        this.answerPoints = answerPoints;
        this.evaluated = evaluated;
        this.interaction = interaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHelpPoints() {
        return helpPoints;
    }

    public void setHelpPoints(Integer helpPoints) {
        this.helpPoints = helpPoints;
    }

    public Integer getAnswerPoints() {
        return answerPoints;
    }

    public void setAnswerPoints(Integer answerPoints) {
        this.answerPoints = answerPoints;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(Date evaluated) {
        this.evaluated = evaluated;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public void loadEvaluationToSave(EvaluationMod mod){
        this.setAnswerPoints(mod.getAnswerPoints());
        this.setComment(mod.getComment());
        this.setEvaluated(new Date());
        this.setHelpPoints(mod.getHelpPoints());
    }
}
