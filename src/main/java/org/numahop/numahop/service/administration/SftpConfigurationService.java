package org.numahop.numahop.service.administration;

import org.numahop.numahop.domain.administration.CinesPAC;
import org.numahop.numahop.domain.administration.SftpConfiguration;
import org.numahop.numahop.domain.dto.administration.SftpConfigurationDTO;
import org.numahop.numahop.domain.jaxb.ppdi.FondsType;
import org.numahop.numahop.domain.jaxb.ppdi.PpdiType;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.exception.PgcnTechnicalException;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnErrorCode;
import org.numahop.numahop.exception.message.PgcnList;
import org.numahop.numahop.repository.administration.SftpConfigurationRepository;
import org.numahop.numahop.service.administration.mapper.SftpConfigurationMapper;
import org.numahop.numahop.service.util.CryptoService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Sébastien on 30/12/2016.
 */
@Service
public class SftpConfigurationService {

	private static final Logger LOG = LoggerFactory.getLogger(SftpConfigurationService.class);

	private final SftpConfigurationRepository sftpConfigurationRepository;

	private final CryptoService cryptoService;

	@Autowired
	public SftpConfigurationService(final SftpConfigurationRepository sftpConfigurationRepository,
			final CryptoService cryptoService) {
		this.sftpConfigurationRepository = sftpConfigurationRepository;
		this.cryptoService = cryptoService;
	}

	@Transactional(readOnly = true)
	public Set<SftpConfigurationDTO> findAllDto(final Boolean active) {
		final Set<SftpConfiguration> confs = Boolean.TRUE.equals(active)
				? sftpConfigurationRepository.findByActiveWithDependencies(true)
				: sftpConfigurationRepository.findAllWithDependencies();
		return SftpConfigurationMapper.INSTANCE.configurationSftpToDtos(confs);
	}

	@Transactional(readOnly = true)
	public Set<SftpConfiguration> findByLibrary(final String libraryId) {
		final Library library = new Library();
		library.setIdentifier(libraryId);
		return sftpConfigurationRepository.findByLibrary(library);
	}

	@Transactional(readOnly = true)
	public Set<SftpConfiguration> findByLibrary(final Library library, final boolean active) {
		return Boolean.TRUE.equals(active) ? sftpConfigurationRepository.findByLibraryAndActive(library, true)
				: sftpConfigurationRepository.findByLibrary(library);

	}

	@Transactional(readOnly = true)
	public Set<SftpConfigurationDTO> findDtoByLibrary(final Library library, final Boolean active) {
		final Set<SftpConfiguration> confs = Boolean.TRUE.equals(active)
				? sftpConfigurationRepository.findByLibraryAndActive(library, true)
				: sftpConfigurationRepository.findByLibrary(library);
		return SftpConfigurationMapper.INSTANCE.configurationSftpToDtos(confs);
	}

	@Transactional(readOnly = true)
	public SftpConfiguration findOne(final String id) {
		return sftpConfigurationRepository.findOneWithDependencies(id);
	}

	/**
	 * Récupération des résultats de recherche
	 * @param search
	 * @param libraries
	 * @param page
	 * @param size
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<SftpConfigurationDTO> search(String search, List<String> libraries, Integer page, Integer size) {
		final Pageable pageRequest = PageRequest.of(page, size);
		Page<SftpConfiguration> configurations = sftpConfigurationRepository.search(search, libraries, pageRequest);

		List<SftpConfigurationDTO> results = configurations.getContent()
			.stream()
			.map(SftpConfigurationMapper.INSTANCE::configurationSftpToDto)
			.collect(Collectors.toList());
		return new PageImpl<>(results,
				PageRequest.of(configurations.getNumber(), configurations.getSize(), configurations.getSort()),
				configurations.getTotalElements());
	}

	@Transactional
	public void delete(final String id) {
		sftpConfigurationRepository.deleteById(id);
	}

	@Transactional
	public SftpConfiguration save(final SftpConfiguration conf) throws PgcnValidationException, PgcnTechnicalException {
		setDefaultValues(conf);
		validate(conf);
		final SftpConfiguration savedConf = sftpConfigurationRepository.save(conf);
		return sftpConfigurationRepository.findOneWithDependencies(savedConf.getIdentifier());
	}

	/**
	 * Update path for an existing configuration. Reconciles the detached request against
	 * the managed entity instead of going through {@code EntityManager.merge}, which
	 * would choke on duplicate detached representations of the same {@link CinesPAC} that
	 * the JSON request graph can carry through JSOG decoding.
	 */
	@Transactional
	public SftpConfiguration update(final SftpConfiguration detached)
			throws PgcnValidationException, PgcnTechnicalException {
		setDefaultValues(detached);
		validate(detached);

		final SftpConfiguration managed = sftpConfigurationRepository.findOneWithDependencies(detached.getIdentifier());
		if (managed == null) {
			return null;
		}

		managed.setLabel(detached.getLabel());
		managed.setUsername(detached.getUsername());
		managed.setPassword(detached.getPassword());
		managed.setHost(detached.getHost());
		managed.setPort(detached.getPort());
		managed.setTargetDir(detached.getTargetDir());
		managed.setActive(detached.isActive());
		managed.setLibrary(detached.getLibrary());

		final List<CinesPAC> incoming = detached.getPacs() == null ? List.of() : detached.getPacs();

		// Dedupe by Java reference (catches JSOG @id+@ref decoding to one instance)
		// and by identifier (catches a second Java instance with the same DB id).
		final Set<CinesPAC> seenRefs = Collections.newSetFromMap(new IdentityHashMap<>());
		final Set<String> seenIds = new HashSet<>();
		final List<CinesPAC> deduped = new ArrayList<>(incoming.size());
		for (final CinesPAC pac : incoming) {
			if (!seenRefs.add(pac)) {
				continue;
			}
			if (pac.getIdentifier() != null && !seenIds.add(pac.getIdentifier())) {
				continue;
			}
			deduped.add(pac);
		}

		final Set<String> incomingIds = deduped.stream()
			.map(CinesPAC::getIdentifier)
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());

		// orphanRemoval=true will delete these on flush
		managed.getPacs().removeIf(p -> !incomingIds.contains(p.getIdentifier()));

		final Map<String, CinesPAC> byId = managed.getPacs()
			.stream()
			.collect(Collectors.toMap(CinesPAC::getIdentifier, Function.identity()));

		for (final CinesPAC pac : deduped) {
			if (pac.getIdentifier() == null) {
				pac.setConfPac(managed);
				managed.getPacs().add(pac);
			}
			else {
				final CinesPAC existing = byId.get(pac.getIdentifier());
				if (existing != null) {
					existing.setName(pac.getName());
				}
			}
		}

		return managed;
	}

	private void setDefaultValues(final SftpConfiguration conf) throws PgcnTechnicalException {
		// Cryptage du mot de passe
		if (conf.getPassword() != null) {
			final String encryptedPassword = cryptoService.encrypt(conf.getPassword());
			conf.setPassword(encryptedPassword);
		}
		// Sinon on reprend le mot de passe existant
		else if (conf.getIdentifier() != null) {
			final String currentPassword = sftpConfigurationRepository.findPasswordByIdentifier(conf.getIdentifier());
			conf.setPassword(currentPassword);
		}
	}

	private PgcnList<PgcnError> validate(final SftpConfiguration conf) throws PgcnValidationException {
		final PgcnList<PgcnError> errors = new PgcnList<>();
		final PgcnError.Builder builder = new PgcnError.Builder();

		// le libellé est obligatoire
		if (StringUtils.isEmpty(conf.getLabel())) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.CONF_SFTP_LABEL_MANDATORY).setField("label").build());
		}
		// la bibliothèque est obligatoire
		if (conf.getLibrary() == null) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.CONF_SFTP_LIBRARY_MANDATORY).setField("library").build());
		}
		// Retour
		if (!errors.isEmpty()) {
			conf.setErrors(errors);
			throw new PgcnValidationException(conf, errors);
		}
		return errors;
	}

	@Transactional
	public SftpConfiguration getPpdiPacs(final SftpConfiguration conf, final MultipartFile file) {

		Optional<PpdiType> opt = Optional.empty();

		if (file != null && !file.isEmpty()) {
			try {
				opt = unmarshallPpdiFile(file.getInputStream());
			}
			catch (JAXBException | IOException e) {
				LOG.error(e.getMessage(), e);
				// pb du fichier ppdi : invalide ? - on fait rien ..
				return conf;
			}
			if (opt.isPresent()) {
				final List<FondsType> fonds = opt.get().getContexte().getFonds();
				fonds.forEach(f -> {
					final CinesPAC pac = new CinesPAC();
					pac.setConfPac(conf);
					pac.setName(f.getIntitule());

					if (!conf.getPacs().contains(pac)) {
						conf.getPacs().add(pac);
					}
				});
			}
		}
		return sftpConfigurationRepository.save(conf);
	}

	private Optional<PpdiType> unmarshallPpdiFile(final InputStream input) throws JAXBException {

		final JAXBContext context = JAXBContext.newInstance(org.numahop.numahop.domain.jaxb.ppdi.ObjectFactory.class);
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final JAXBElement<PpdiType> el = (JAXBElement<PpdiType>) unmarshaller.unmarshal(input);
		if (el.getValue() == null) {
			return Optional.empty();
		}
		return Optional.of(el.getValue());
	}

}
