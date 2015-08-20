package br.feevale.labex.controller.mod;

import br.feevale.labex.model.Subject;

/**
 * Created by grimmjowjack on 8/19/15.
 */
public class SubjectMod {

    private Long id;
    private String name;
    private String degree;

    public SubjectMod(Subject subject) {
        id = subject.getId();
        name = subject.getName();
        degree = subject.getDegree().getName();
    }


}
