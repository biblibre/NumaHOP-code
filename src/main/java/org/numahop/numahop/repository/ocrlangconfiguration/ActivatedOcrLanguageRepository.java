package org.numahop.numahop.repository.ocrlangconfiguration;

import org.numahop.numahop.domain.ocrlangconfiguration.ActivatedOcrLanguage;
import org.numahop.numahop.domain.ocrlangconfiguration.OcrLangConfiguration;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivatedOcrLanguageRepository extends JpaRepository<ActivatedOcrLanguage, String> {

	List<ActivatedOcrLanguage> findByOcrLangConfiguration(OcrLangConfiguration config);

}
