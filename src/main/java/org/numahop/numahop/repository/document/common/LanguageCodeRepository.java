package org.numahop.numahop.repository.document.common;

import org.numahop.numahop.domain.document.common.LanguageCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageCodeRepository extends JpaRepository<LanguageCode, String> {

	List<LanguageCode> getAllByNameOrIso6392tOrIso6392bOrIso6391(String name, String iso6392t, String iso6392b,
			String iso6391);

}
