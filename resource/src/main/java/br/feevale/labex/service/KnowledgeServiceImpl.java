package br.feevale.labex.service;

import br.feevale.labex.controller.data.KnowledgesData;
import br.feevale.labex.controller.mod.KnowledgeMod;
import br.feevale.labex.model.Area;
import br.feevale.labex.model.Knowledge;
import br.feevale.labex.model.Subject;
import br.feevale.labex.repository.AreaRepository;
import br.feevale.labex.repository.KnowledgeRepository;
import br.feevale.labex.repository.SubjectRepository;
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
    private final SubjectRepository subjectRepository;

    @Inject
    public KnowledgeServiceImpl(KnowledgeRepository repository, AreaRepository areaRepository,
                                SubjectRepository subjectRepository) {
        this.repository = repository;
        this.areaRepository = areaRepository;
        this.subjectRepository = subjectRepository;
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
    public List<KnowledgeMod> listKnowledgesByUser(Long id) {
        List<Knowledge> knowledges = repository.findByUserId(id);
        List<KnowledgeMod> mods = new ArrayList<>();
        for(Knowledge k : knowledges)
            mods.add(new KnowledgeMod(k));

        return mods;
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
    public Area findAreaById(Long area) {
        return areaRepository.findOne(area);
    }

    @Override
    public List<Subject> listSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findSubjectById(Long subject) {
        return subjectRepository.findOne(subject);
    }

    @Override
    public Knowledge save(Knowledge entity) {
        Knowledge verifier = repository.findKnowledge(entity.getId().getArea().getId(),
                                                      entity.getId().getUser().getId());
        if(verifier != null)
            return verifier;

        return repository.saveAndFlush(entity);
    }

    @Override
    public Boolean delete(Long idArea, Long idUser) {
        Knowledge knowledge = repository.findKnowledge(idArea, idUser);
        if(knowledge == null)
            return false;

        repository.delete(knowledge);
        return true;
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
