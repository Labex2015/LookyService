package br.feevale.labex.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by grimmjowjack on 8/18/15.
 */
@Embeddable
public class KnowledgeId implements Serializable{

    @ManyToOne
    private User user;
    @ManyToOne
    private Area area;

    public KnowledgeId() {
    }

    public KnowledgeId(User user, Area area) {
        this.user = user;
        this.area = area;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
