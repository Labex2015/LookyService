package mocks;

import br.feevale.labex.controller.mod.KnowledgeMod;
import br.feevale.labex.model.Area;

/**
 * Created by grimmjowjack on 8/19/15.
 */
public class KnowledgesAndAreasMock {


    public static Area getArea(String nome, Long id) {
        return new Area(id, nome);
    }

    public static KnowledgeMod getKnowledgeWithAreaId(Long userValid, String areaValidName, Long subjectId) {
        return new KnowledgeMod(userValid, areaValidName, null, subjectId);
    }
}
