package com.jmlessous.repositories;

import com.jmlessous.entities.CompteEpargne;
import com.jmlessous.entities.CreditImmobilier;
import org.springframework.data.repository.CrudRepository;

public interface CreditImobRepository extends CrudRepository<CreditImmobilier,Long> {
}
