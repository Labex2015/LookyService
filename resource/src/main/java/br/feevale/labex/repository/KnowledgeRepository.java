package br.feevale.labex.repository;

import br.feevale.labex.model.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grimmjowjack on 8/19/15.
 */
@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>{

    public static final String FIND_BY_USER = "Select k.* from knowledge as k where k.user_id = :user";

    @Query(nativeQuery = true, value = FIND_BY_USER)
    List<Knowledge> findByUserId(@Param("user") Long id);
}
