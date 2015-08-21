package br.feevale.labex.model;

import br.feevale.labex.controller.utils.APIDoc;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by grimmjowjack on 8/17/15.
 */
@ApiObject(name = "SearchItem",
           description = APIDoc.DESC_MOD_SEARCHHELP,
           group = APIDoc.GROUP_USER)
public class SearchItem {

    @ApiObjectField(required = true, description = "Campo contendo o conteúdo de busca. Ex: 'Java EE", format = "")
    public String param;
    @ApiObjectField(required = false, description = "Campo usado para vincular a busca com uma disciplina.")
    public Long subject;
    @ApiObjectField(required = true, description = "Usado na paginação de registros.")
    public int position;
    @ApiObjectField(required = true, description = "Máximo de registros a serem retornados. Min = 1 e Máx = 10.")
    public int max;

    public SearchItem() { }

    public SearchItem(String param, Long subject, int position, int max) {
        this.param = param;
        this.subject = subject;
        this.position = position;
        this.max = max;
    }
}
