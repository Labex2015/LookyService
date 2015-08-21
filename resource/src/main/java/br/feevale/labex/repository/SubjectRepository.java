package br.feevale.labex.repository;

import br.feevale.labex.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by grimmjowjack on 8/21/15.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{
}
