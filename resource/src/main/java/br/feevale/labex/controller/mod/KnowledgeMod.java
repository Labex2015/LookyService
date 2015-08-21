package br.feevale.labex.controller.mod;

import br.feevale.labex.controller.utils.APIDoc;
import br.feevale.labex.model.Knowledge;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by grimmjowjack on 8/19/15.
 */
@ApiObject(group = APIDoc.GROUP_KNOWLEDGE,
           description = APIDoc.DESC_MOD_KNOWLEDGE)
public class KnowledgeMod {

    @ApiObjectField(required = true, description = "id do usuário enviando dados.")
    public Long idUser;

    @ApiObjectField(required = false, description = "Se preenchido, significa uma nova área.")
    public String name;
    @ApiObjectField(required = false, description = "Se enviado, deverá ser de uma área existente, ou retornará erros.")
    public Long area;
    @ApiObjectField(required = false, description = "Id de uma dada disciplina.")
    public Long subject;
    public String subjectName;

    public KnowledgeMod() {
    }

    public KnowledgeMod(Knowledge knowledge) {
        this.idUser = knowledge.getId().getUser().getId();
        this.name = knowledge.getId().getArea().getName();
        this.area = knowledge.getId().getArea().getId();
        this.subject = knowledge.getSubject() != null ? knowledge.getSubject().getId() : 0L;
        this.subjectName = knowledge.getSubject() != null ? knowledge.getSubject().getName() : null;
    }

    public KnowledgeMod(Long idUser, String name, Long area, Long subject) {
        this.idUser = idUser;
        this.name = name;
        this.area = area;
        this.subject = subject;
    }
}
