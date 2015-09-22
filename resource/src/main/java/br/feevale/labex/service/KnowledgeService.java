package br.feevale.labex.service;

import br.feevale.labex.controller.data.KnowledgesData;
import br.feevale.labex.controller.mod.KnowledgeMod;
import br.feevale.labex.model.Area;
import br.feevale.labex.model.Knowledge;
import br.feevale.labex.model.Subject;

import java.util.List;

/**
 * Created by grimmjowjack on 8/19/15.
 */
public interface KnowledgeService{

    List<KnowledgesData> findKnowledgesByUser(Long id);
    List<KnowledgeMod> listKnowledgesByUser(Long id);
    Knowledge save(Knowledge entity);
    Boolean delete(Long idArea, Long idUser);
    Knowledge findById(Long id);
    List<Knowledge> findAll();
    List<Knowledge> findByParam(Object... params);

    List<Area> listAreas();
    Area saveArea(Area area);
    Area findAreaById(Long area);

    List<Subject> listSubjects();

    Subject findSubjectById(Long subject);
}
