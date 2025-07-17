package fr.progilone.pgcn.service.exchange.cines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import fr.progilone.pgcn.service.administration.MailboxConfigurationService;
import fr.progilone.pgcn.service.document.DocUnitService;
import fr.progilone.pgcn.service.exchange.cines.CinesRequestHandlerService.CinesResponse;
import fr.progilone.pgcn.service.exchange.mail.MailboxService;
import fr.progilone.pgcn.service.storage.FileStorageManager;
import fr.progilone.pgcn.service.util.transaction.TransactionService;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class CinesRequestHandlerServiceTest {

	private final String[] instanceLibraries = { "lib_test" };

	private static final String WORKING_DIR = FileUtils.getTempDirectoryPath() + "/pgcn_test";
    private static final String CINES_NOTIFICATIONS_MAIL = "test@test.com";

	private final String RECEIPT_MAIL = "/mail/cines/receipt.eml";
	private final String REJECTION_MAIL = "/mail/cines/rejection.eml";
	private final String CERTIFICATION_MAIL = "/mail/cines/certification.eml";

	@Mock
	private CinesReportService cinesReportService;

	@Mock
	private ExportCinesService exportCinesService;

	@Mock
	private MailboxService mailboxService;

	@Mock
	private MailboxConfigurationService mailboxConfigurationService;

	@Mock
	private FileStorageManager fm;

	@Mock
	private DocUnitService docUnitService;

	@Mock
	private TransactionService transactionService;

	private CinesRequestHandlerService service;

	@BeforeEach
	public void setUp() {
		service = new CinesRequestHandlerService(exportCinesService, cinesReportService, mailboxService,
				mailboxConfigurationService, fm, docUnitService, transactionService);
		ReflectionTestUtils.setField(service, "instanceLibraries", instanceLibraries);
		ReflectionTestUtils.setField(service, "cinesUpdatingEnabled", true);
		ReflectionTestUtils.setField(service, "workingDir", WORKING_DIR);
		ReflectionTestUtils.setField(service, "cacheDir", WORKING_DIR + "/cache");
	}

    private Message loadMessage(String messagePath) throws Exception {
        Properties props = new Properties();
        Session mailSession = Session.getDefaultInstance(props, null);
        // Read the .eml file
        InputStream emlFile = getClass().getResourceAsStream(messagePath);

        if (emlFile == null) {
                throw new RuntimeException("Failed to load the eml file from the classpath.");
        }
        // Parse the .eml file into a MimeMessage
        MimeMessage message = new MimeMessage(mailSession, emlFile);
        emlFile.close();
        return message;
    }

	@Test
	public void parseAllCinesMailTypes() throws Exception {
        {
            Message message = loadMessage(CERTIFICATION_MAIL);
            CinesResponse resp = service.parseCinesMessage(message).orElseThrow();
            assertNotNull(resp.getAip());
            assertNotNull(resp.getAvis());
            assert(resp.hasId(CinesRequestHandlerService.REP_CERTIFICAT_ARCHIVAGE));
        }
        {
            Message message = loadMessage(RECEIPT_MAIL);
            CinesResponse resp = service.parseCinesMessage(message).orElseThrow();
            assertNull(resp.getAip());
            assertNotNull(resp.getAvis());
            assert(resp.hasId(CinesRequestHandlerService.REP_ACCUSE_RECEPTION_DE_VERSEMENT));
        }
        {
            Message message = loadMessage(REJECTION_MAIL);
            CinesResponse resp = service.parseCinesMessage(message).orElseThrow();
            assertNull(resp.getAip());
            assertNotNull(resp.getAvis());
            assert(resp.hasId(CinesRequestHandlerService.REP_REJET_VERSEMENT));
        }
    }
}
