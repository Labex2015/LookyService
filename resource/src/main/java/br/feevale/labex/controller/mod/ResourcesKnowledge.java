package br.feevale.labex.controller.mod;

import br.feevale.labex.model.Area;
import br.feevale.labex.model.Subject;

import java.util.List;

/**
 * Created by grimmjowjack on 9/4/15.
 */

public class ResourcesKnowledge {

    public List<Area> areas;
    public List<Subject> subjects;

    public ResourcesKnowledge() {}

    public ResourcesKnowledge(List<Area> areas, List<Subject> subjects) {
        this.areas = areas;
        this.subjects = subjects;
    }
}
