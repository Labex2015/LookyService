package br.feevale.labex.repository;

import br.feevale.labex.model.RequestHelp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 0126128 on 01/07/2015.
 */
@Repository
public interface RequestHelpRepository extends JpaRepository<RequestHelp, Long>{

    static final String FIND_BY_USERS = "";

    @Query(nativeQuery = true, value = FIND_BY_USERS)
    RequestHelp findByRequesterAndHelper(@Param("idUser") Long idUser, @Param("idHelper") Long idHelper);
}
