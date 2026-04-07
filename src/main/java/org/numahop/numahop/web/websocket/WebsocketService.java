package org.numahop.numahop.web.websocket;

import org.numahop.numahop.web.websocket.dto.NotificationDTO;
import org.numahop.numahop.web.websocket.dto.NotificationDTO.NotificationCode;
import org.numahop.numahop.web.websocket.dto.NotificationDTO.NotificationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

	private static final Logger LOG = LoggerFactory.getLogger(WebsocketService.class);

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	public void sendNotification(final String userLogin, final NotificationCode notificationCode,
			final NotificationLevel notificationLevel) {
		final NotificationDTO notificationDTO = new NotificationDTO(userLogin, notificationCode, notificationLevel);
		LOG.trace("sendNotification : {}", notificationDTO);
		messagingTemplate.convertAndSend("/topic/notification", notificationDTO);
	}

	public void sendObject(final String identifier, final Object object) {
		LOG.trace("sendObject : {}", identifier);
		messagingTemplate.convertAndSend("/topic/object/" + identifier, object);
	}

}
