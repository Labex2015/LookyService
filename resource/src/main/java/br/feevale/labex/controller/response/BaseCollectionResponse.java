package br.feevale.labex.controller.response;
import br.feevale.labex.controller.utils.ConvertListAction;

import java.util.List;

public class BaseCollectionResponse{

    public List<Object> data;

    public BaseCollectionResponse(ConvertListAction listAction) {
        this.data = listAction.convertList();
    }
}
