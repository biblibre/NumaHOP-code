package org.numahop.numahop.repository.administration;

import org.numahop.numahop.domain.administration.MailboxConfiguration;
import java.util.List;

public interface MailboxConfigurationRepositoryCustom {

	List<MailboxConfiguration> search(String search, List<String> libraries, boolean active);

}
