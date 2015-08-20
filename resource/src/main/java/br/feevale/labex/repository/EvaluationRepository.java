package br.feevale.labex.repository;

import br.feevale.labex.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 0126128 on 17/06/2015.
 */
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long>{

    public static final String FIND_EVALUATIONS_TO_ONE = "select e.* from evaluation as e " +
            " where e.interaction_id in "+
            " (select i.id from interaction as i "+
            " where i.open = 0 and i.request_help_id in "+
            " (select rh.id from request_help as rh"+
            " where rh.helper_id = :idUser))";


    @Query(nativeQuery = true, value = FIND_EVALUATIONS_TO_ONE)
    List<Evaluation> findEvaluationsToOne(@Param("idUser") Long idUser);
}
