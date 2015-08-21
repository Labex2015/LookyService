package br.feevale.labex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by grimmjowjack on 8/18/15.
 */
@Entity
@Table(name = "knowledge")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "pk.area",
                joinColumns = @JoinColumn(name = "area_id"))
})
public class Knowledge implements Serializable{

    @JsonIgnore
    @EmbeddedId
    private KnowledgeId id;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = true)
    private Subject subject;

    public Knowledge() {
    }

    public Knowledge(User user, Area area) {
        this(user, area, null);
    }

    public Knowledge(User user, Area area, Subject subject) {
        this.subject = subject;
        this.id = new KnowledgeId(user, area);
    }

    public KnowledgeId getId() {
        return id;
    }

    public void setId(KnowledgeId id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
