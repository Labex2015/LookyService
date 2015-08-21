package br.feevale.labex.model;

import br.feevale.labex.controller.mod.KnowledgeMod;

import javax.persistence.*;

/**
 * Created by grimmjowjack on 8/18/15.
 */
@Entity(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 90)
    private String name;

    @ManyToOne
    @JoinColumn(name = "degree_id", nullable = false)
    private Degree degree;

    public Subject() {
    }

    public Subject(Long id, String name, Degree degree) {
        this.id = id;
        this.name = name;
        this.degree = degree;
    }

    public Long getId() {
        return id;
    }

    public Subject setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }
}
