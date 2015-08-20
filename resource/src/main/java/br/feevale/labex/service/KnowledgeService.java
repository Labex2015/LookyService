package br.feevale.labex.service;

import br.feevale.labex.controller.data.KnowledgesData;
import br.feevale.labex.model.Area;
import br.feevale.labex.model.Knowledge;

import java.util.List;

/**
 * Created by grimmjowjack on 8/19/15.
 */
public interface KnowledgeService extends BaseService<Knowledge>{
    List<KnowledgesData> findKnowledgesByUser(Long id);

    List<Area> listAreas();
    Area saveArea(Area area);
}
