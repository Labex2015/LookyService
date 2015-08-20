package br.feevale.labex.repository;

import br.feevale.labex.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 0126128 on 01/06/2015.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
