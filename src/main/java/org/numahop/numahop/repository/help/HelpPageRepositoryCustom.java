package org.numahop.numahop.repository.help;

import org.numahop.numahop.domain.dto.help.HelpPageDto;
import org.numahop.numahop.domain.help.HelpPageType;
import java.util.List;

public interface HelpPageRepositoryCustom {

	List<HelpPageDto> search(final List<String> modules, final List<HelpPageType> types, String search);

	List<HelpPageDto> searchByTag(String tag);

}
