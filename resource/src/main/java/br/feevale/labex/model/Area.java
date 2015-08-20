package br.feevale.labex.model;

import javax.persistence.*;

/**
 * Created by grimmjowjack on 8/18/15.
 */
@Entity(name = "area")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 90)
    private String name;

    public Area() {
    }

    public Area(String name) {
        this.name = name;
    }

    public Area(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
