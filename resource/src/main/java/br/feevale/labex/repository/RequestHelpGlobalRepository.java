package br.feevale.labex.repository;

import br.feevale.labex.model.RequestHelpGlobal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by grimmjowjack on 9/21/15.
 */
public interface RequestHelpGlobalRepository extends JpaRepository<RequestHelpGlobal, Long> {
}

