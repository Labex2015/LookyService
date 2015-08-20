package br.feevale.labex.controller.mod;

import br.feevale.labex.model.Area;

/**
 * Created by grimmjowjack on 8/19/15.
 */
public class KnowledgeMod {

    public Long idUser;
    public String name;
    public Long area;
    public Long subject;

    public KnowledgeMod() {
    }

    public KnowledgeMod(Long idUser, String name, Long area, Long subject) {
        this.idUser = idUser;
        this.name = name;
        this.area = area;
        this.subject = subject;
    }
}
