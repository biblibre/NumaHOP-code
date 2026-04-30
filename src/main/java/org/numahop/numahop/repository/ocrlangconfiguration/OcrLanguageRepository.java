package org.numahop.numahop.repository.ocrlangconfiguration;

import org.numahop.numahop.domain.ocrlangconfiguration.OcrLanguage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcrLanguageRepository extends JpaRepository<OcrLanguage, String> {

	List<OcrLanguage> findAllByOrderByLabelAsc();

}
