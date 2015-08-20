package br.feevale.labex.service;

import br.feevale.labex.controller.data.KnowledgesData;
import br.feevale.labex.model.Area;
import br.feevale.labex.model.Knowledge;
import br.feevale.labex.repository.AreaRepository;
import br.feevale.labex.repository.KnowledgeRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grimmjowjack on 8/19/15.
 */
@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    private final KnowledgeRepository repository;
    private final AreaRepository areaRepository;

    @Inject
    public KnowledgeServiceImpl(KnowledgeRepository repository, AreaRepository areaRepository) {
        this.repository = repository;
        this.areaRepository = areaRepository;
    }

    @Transactional
    @Override
    public List<KnowledgesData> findKnowledgesByUser(Long id) {
        List<Knowledge> knowledges = repository.findByUserId(id);

        if(knowledges != null && !knowledges.isEmpty()) {
            List<KnowledgesData> result = new ArrayList<>();
            for (Knowledge k : knowledges) {
                k.getId();
                String subject = k.getSubject() != null ? k.getSubject().getName() : null;
                result.add(new KnowledgesData(k, subject));
            }
            return result;
        }
        return null;
    }

    @Override
    public List<Area> listAreas() {
        return areaRepository.findAll();
    }

    @Override
    public Area saveArea(Area area) {
        Area checkObject = areaRepository.findAreaByName(area.getName());
        if(checkObject != null)
            return checkObject;

        return areaRepository.saveAndFlush(area);
    }

    @Override
    public Knowledge save(Knowledge entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Knowledge findById(Long id) {
        return null;
    }

    @Override
    public List<Knowledge> findAll() {
        return null;
    }

    @Override
    public List<Knowledge> findByParam(Object... params) {
        return null;
    }
}
