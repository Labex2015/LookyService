package br.feevale.labex.repository;

import br.feevale.labex.model.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by grimmjowjack on 8/19/15.
 */
@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long>{
}
