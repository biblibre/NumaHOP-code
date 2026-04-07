package org.numahop.numahop.service.exchange.mail;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.numahop.numahop.domain.administration.MailboxConfiguration;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.exception.PgcnTechnicalException;
import org.numahop.numahop.repository.exchange.cines.CinesReportRepository;
import org.numahop.numahop.service.administration.MailboxConfigurationService;
import org.numahop.numahop.service.document.DocUnitService;
import org.numahop.numahop.service.exchange.cines.CinesReportService;
import org.numahop.numahop.service.exchange.cines.CinesRequestHandlerService;
import org.numahop.numahop.service.exchange.cines.ExportCinesService;
import org.numahop.numahop.service.storage.FileStorageManager;
import org.numahop.numahop.service.util.CryptoService;
import org.numahop.numahop.service.util.transaction.TransactionService;
import org.numahop.numahop.service.workflow.WorkflowService;
import org.numahop.numahop.web.websocket.WebsocketService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Created by Sébastien on 03/01/2017.
 */
@ExtendWith(MockitoExtension.class)
public class MailboxServiceTest {

	private MailboxService service;

	private CinesRequestHandlerService cinesRHandlerService;

	@Mock
	private CryptoService cryptoService;

	@Mock
	private CinesReportRepository cinesReportRepository;

	@Mock
	private WebsocketService websocketService;

	@Mock
	private MailboxConfigurationService mailboxConfigurationService;

	@Mock
	private FileStorageManager fm;

	@Mock
	private ExportCinesService exportCinesService;

	@Mock
	private DocUnitService docUnitService;

	@Mock
	private TransactionService transactionService;

	@Mock
	private WorkflowService workflowService;

	@BeforeEach
	public void setUp() throws PgcnTechnicalException {

		service = new MailboxService(cryptoService);
		when(cryptoService.decrypt(anyString())).thenAnswer(new ReturnsArgumentAt(0));

		final CinesReportService cinesReportService = new CinesReportService(cinesReportRepository, websocketService,
				workflowService);
		cinesRHandlerService = new CinesRequestHandlerService(exportCinesService, cinesReportService, service,
				mailboxConfigurationService, fm, docUnitService, transactionService);
	}

	@Disabled
	@Test
	public void testReceptionMsgCines() throws PgcnTechnicalException {

		final MailboxConfiguration conf = getMsgConfig();
		final List<MailboxConfiguration> confs = new ArrayList<>();
		confs.add(conf);
		cinesRHandlerService.updateExportedDocUnits(confs);
	}

	@Disabled
	@Test
	public void testGetMails() throws PgcnTechnicalException, MessagingException {

		final MailboxConfiguration conf = getMsgConfig();
		service.readMailbox(conf, messages -> {
			for (final Message message : messages) {
				try {
					System.out.println("Mail de " + message.getFrom()[0] + ", reçu le " + message.getReceivedDate()
							+ ": " + message.getSubject());
				}
				catch (final MessagingException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private MailboxConfiguration getMsgConfig() {

		final MailboxConfiguration conf = new MailboxConfiguration();
		conf.setHost("mail.gandi.net");
		conf.setUsername("pgcn@progilone.com");
		conf.setPassword("maw1QuoQuoravGi");
		conf.setInbox("Inbox");

		final Library lib = new Library();
		lib.setActive(true);
		lib.setName("Librairie Sciences PO");
		lib.setIdentifier("library_sciencespo");

		conf.setLibrary(lib);

		// IMAP
		// conf.setPort(144);
		// conf.addProperty("mail.store.protocol", "imap");

		// IMAP SSL
		conf.setPort(993);
		conf.addProperty("mail.store.protocol", "imaps");
		conf.addProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		conf.addProperty("mail.imaps.socketFactory.fallback", "false");

		// POP3
		// conf.setPort(110);
		// conf.addProperty("mail.store.protocol", "pop3");

		// POP3 SSL
		// conf.setPort(995);
		// conf.addProperty("mail.store.protocol", "pop3s");
		// conf.addProperty("mail.pop3s.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		// conf.addProperty("mail.pop3s.socketFactory.fallback", "false");
		return conf;
	}

}
