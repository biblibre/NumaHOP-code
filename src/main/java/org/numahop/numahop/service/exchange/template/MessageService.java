package org.numahop.numahop.service.exchange.template;

import org.numahop.numahop.security.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

	private final MessageSource messageSource;

	@Autowired
	public MessageService(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Traduction du code en message localisé
	 * @param prefix
	 * @param code
	 * @return
	 */
	public String getMessage(final String prefix, final String code) {
		final String fullCode = StringUtils.joinWith(".", prefix, code);

		try {
			return messageSource.getMessage(fullCode, null, SecurityUtils.getCurrentLanguage().getLocale());

		}
		catch (NoSuchMessageException e) {
			LOG.error(e.getMessage(), e);
			return code;
		}
	}

}
