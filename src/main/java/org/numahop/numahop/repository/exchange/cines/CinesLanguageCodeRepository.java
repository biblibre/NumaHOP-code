package org.numahop.numahop.repository.exchange.cines;

import org.numahop.numahop.domain.exchange.cines.CinesLanguageCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinesLanguageCodeRepository extends JpaRepository<CinesLanguageCode, String> {

	List<CinesLanguageCode> findByOrderByIdentifier();

	CinesLanguageCode findOneByIdentifier(String identifier);

	CinesLanguageCode findOneByLangDC(String langDC);

}
