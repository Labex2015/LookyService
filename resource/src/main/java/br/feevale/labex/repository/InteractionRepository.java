package br.feevale.labex.repository;

import br.feevale.labex.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 0126128 on 03/06/2015.
 */
@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long>{

    public static final String ACTIVE_INTERACTIONS_BY_USER = "Select i.* from interaction as i "+
            "inner join request_help as rh "+
            "on i.request_help_id = rh.id "+
            "where i.open = 1 and (rh.status = 'A' ||  rh.status = 'O') " +
            "and (rh.helper_id = :idUser || rh.requester_id = :idUser)";

    @Query(nativeQuery = true, value = ACTIVE_INTERACTIONS_BY_USER)
    List<Interaction> findByUser(@Param("idUser") Long validId);

}
