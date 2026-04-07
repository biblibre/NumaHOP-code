package org.numahop.numahop.repository.administration;

import org.numahop.numahop.domain.administration.Transliteration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Sébastien on 29/06/2017.
 */
public interface TransliterationRepository extends JpaRepository<Transliteration, Transliteration.TransliterationId> {

}
