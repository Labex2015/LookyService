package br.feevale.labex.repository;

import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.model.RequestHelpGlobal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 0126128 on 01/07/2015.
 */
@Repository
public interface RequestHelpRepository extends JpaRepository<RequestHelp, Long>{

    static final String FIND_BY_USERS = "";

    public static final String PENDING_INTERACTIONS_BY_USER = "Select rh.* from request_help as rh "+
            "where rh.status = 'W' and (rh.helper_id = :idUser || rh.requester_id = :idUser)";


    @Query(nativeQuery = true, value = FIND_BY_USERS)
    RequestHelp findByRequesterAndHelper(@Param("idUser") Long idUser, @Param("idHelper") Long idHelper);

    @Query(nativeQuery = true, value = PENDING_INTERACTIONS_BY_USER)
    List<RequestHelp> findAllAsWaitingStatus(@Param("idUser") Long validId);

    List<RequestHelp> findAllByStatus(char status);
}
