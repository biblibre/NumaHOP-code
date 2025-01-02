package fr.progilone.pgcn.service.filesgestionconfiguration;

import fr.progilone.pgcn.domain.dto.filesgestionconfiguration.FilesGestionConfigDTO;
import fr.progilone.pgcn.domain.filesgestionconfiguration.FilesGestionConfig;
import fr.progilone.pgcn.exception.PgcnValidationException;
import fr.progilone.pgcn.repository.filesgestionconfiguration.FilesGestionConfigRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilesGestionConfigService {

	@Autowired
	private FilesGestionConfigRepository filesGestionConfigRepository;

	@Transactional
	public FilesGestionConfigDTO getConfigByLibrary(final String libraryIdentifier) {
		return FilesGestionConfigMapper.INSTANCE
			.configToConfigDto(filesGestionConfigRepository.getOneByLibraryIdentifier(libraryIdentifier));
	}

	@Transactional
	public List<FilesGestionConfig> getConfigs() {
		return filesGestionConfigRepository.findAll();
	}

	@Transactional
	public FilesGestionConfig getOne(final String id) {
		return filesGestionConfigRepository.findById(id).orElse(null);
	}

	@Transactional
	public FilesGestionConfigDTO save(final FilesGestionConfig config) throws PgcnValidationException {
		final FilesGestionConfig saved = filesGestionConfigRepository.save(config);

		return FilesGestionConfigMapper.INSTANCE.configToConfigDto(getOne(saved.getIdentifier()));
	}

}
