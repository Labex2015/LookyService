package br.feevale.labex.repository;

import br.feevale.labex.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by grimmjowjack on 8/18/15.
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{

    public static final String FIND_BY_NAME = "";

    @Query(nativeQuery = true, value = FIND_BY_NAME)
    Area findAreaByName(@Param("name") String param);
}
