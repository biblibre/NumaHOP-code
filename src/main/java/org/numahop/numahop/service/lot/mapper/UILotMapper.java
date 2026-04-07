package org.numahop.numahop.service.lot.mapper;

import org.numahop.numahop.domain.administration.CinesPAC;
import org.numahop.numahop.domain.administration.ExportFTPDeliveryFolder;
import org.numahop.numahop.domain.administration.InternetArchiveCollection;
import org.numahop.numahop.domain.administration.omeka.OmekaConfiguration;
import org.numahop.numahop.domain.administration.omeka.OmekaList;
import org.numahop.numahop.domain.dto.administration.CinesPACDTO;
import org.numahop.numahop.domain.dto.administration.InternetArchiveCollectionDTO;
import org.numahop.numahop.domain.dto.administration.omeka.OmekaConfigurationDTO;
import org.numahop.numahop.domain.dto.administration.omeka.OmekaListDTO;
import org.numahop.numahop.domain.dto.document.SimpleDocUnitDTO;
import org.numahop.numahop.domain.dto.lot.LotDTO;
import org.numahop.numahop.domain.dto.user.SimpleUserDTO;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.repository.document.DocUnitRepository;
import org.numahop.numahop.repository.ocrlangconfiguration.OcrLanguageRepository;
import org.numahop.numahop.repository.project.ProjectRepository;
import org.numahop.numahop.service.administration.CinesPACService;
import org.numahop.numahop.service.administration.InternetArchiveCollectionService;
import org.numahop.numahop.service.administration.omeka.OmekaConfigurationService;
import org.numahop.numahop.service.administration.omeka.OmekaListService;
import org.numahop.numahop.service.administration.viewsformat.ViewsFormatConfigurationService;
import org.numahop.numahop.service.checkconfiguration.CheckConfigurationService;
import org.numahop.numahop.service.exportftpconfiguration.ExportFTPConfigurationService;
import org.numahop.numahop.service.ftpconfiguration.FTPConfigurationService;
import org.numahop.numahop.service.user.UserService;
import org.numahop.numahop.service.workflow.WorkflowModelService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UILotMapper {

	@Autowired
	private DocUnitRepository docUnitRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private FTPConfigurationService ftpConfigurationService;

	@Autowired
	private ExportFTPConfigurationService exportFTPConfigurationService;

	@Autowired
	private CheckConfigurationService checkConfigurationService;

	@Autowired
	private ViewsFormatConfigurationService viewsFormatConfigurationService;

	@Autowired
	private WorkflowModelService workflowModelService;

	@Autowired
	private UserService userService;

	@Autowired
	private InternetArchiveCollectionService iaCollectionService;

	@Autowired
	private CinesPACService cinesPACService;

	@Autowired
	private OmekaListService omekaListService;

	@Autowired
	private OmekaConfigurationService omekaConfigurationService;

	@Autowired
	private OcrLanguageRepository ocrLangRepository;

	public UILotMapper() {
	}

	public void mapInto(final LotDTO lotDTO, final Lot lot) {
		lot.setIdentifier(lotDTO.getIdentifier());
		lot.setLabel(lotDTO.getLabel());
		lot.setCode(lotDTO.getCode());
		// Type
		if (lotDTO.getType() != null) {
			lot.setType(Lot.Type.valueOf(lotDTO.getType()));
		}
		lot.setDescription(lotDTO.getDescription());
		lot.setActive(lotDTO.getActive());
		lot.setCondNotes(lotDTO.getCondNotes());
		lot.setNumNotes(lotDTO.getNumNotes());
		lot.setDeliveryDateForseen(lotDTO.getDeliveryDateForseen());
		lot.setRequiredFormat(lotDTO.getRequiredFormat());
		lot.setRequiredTypeCompression(lotDTO.getRequiredTypeCompression());
		lot.setRequiredTauxCompression(lotDTO.getRequiredTauxCompression());
		lot.setRequiredResolution(lotDTO.getRequiredResolution());
		lot.setRequiredColorspace(lotDTO.getRequiredColorspace());

		final Set<SimpleDocUnitDTO> docUnits = lotDTO.getDocUnits();
		if (docUnits != null) {
			lot.setDocUnits(docUnits.stream()
				.map(docUnit -> docUnitRepository.findById(docUnit.getIdentifier()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet()));
		}
		else {
			lot.setDocUnits(new HashSet<>());
		}
		if (lotDTO.getProject() != null) {
			lot.setProject(projectRepository.findOneByIdentifier(lotDTO.getProject().getIdentifier()));
		}
		if (lotDTO.getActiveFTPConfiguration() != null) {
			lot.setActiveFTPConfiguration(
					ftpConfigurationService.getOne(lotDTO.getActiveFTPConfiguration().getIdentifier()));
		}
		if (lotDTO.getActiveExportFTPConfiguration() != null) {
			lot.setActiveExportFTPConfiguration(
					exportFTPConfigurationService.getOne(lotDTO.getActiveExportFTPConfiguration().getIdentifier()));
			// delivery folders update
			if (lot.getActiveExportFTPConfiguration() != null && lotDTO.getActiveExportFTPDeliveryFolder() != null) {
				final ExportFTPDeliveryFolder newFolder = new ExportFTPDeliveryFolder();
				newFolder.setIdentifier(lotDTO.getActiveExportFTPDeliveryFolder().getIdentifier());
				newFolder.setName(lotDTO.getActiveExportFTPDeliveryFolder().getName());
				lot.setActiveExportFTPDeliveryFolder(newFolder);
			}
			else {
				lot.setActiveExportFTPDeliveryFolder(null);
			}
		}
		if (lotDTO.getActiveCheckConfiguration() != null) {
			lot.setActiveCheckConfiguration(
					checkConfigurationService.findOne(lotDTO.getActiveCheckConfiguration().getIdentifier()));
		}
		if (lotDTO.getActiveFormatConfiguration() != null) {
			lot.setActiveFormatConfiguration(
					viewsFormatConfigurationService.findOne(lotDTO.getActiveFormatConfiguration().getIdentifier()));
		}
		if (lotDTO.getWorkflowModel() != null) {
			lot.setWorkflowModel(workflowModelService.getOne(lotDTO.getWorkflowModel().getIdentifier()));
		}
		if (lotDTO.getActiveOcrLanguage() != null) {
			lot.setActiveOcrLanguage(ocrLangRepository.getOne(lotDTO.getActiveOcrLanguage().getIdentifier()));
		}

		final InternetArchiveCollectionDTO iaCollection = lotDTO.getCollectionIA();
		if (iaCollection != null && iaCollection.getIdentifier() != null) {
			final InternetArchiveCollection internetArchiveCollection = iaCollectionService
				.findOne(iaCollection.getIdentifier());
			lot.setCollectionIA(internetArchiveCollection);
		}

		final CinesPACDTO cinesPACDTO = lotDTO.getPlanClassementPAC();
		if (cinesPACDTO != null && cinesPACDTO.getIdentifier() != null) {
			final CinesPAC cinesPAC = cinesPACService.findOne(cinesPACDTO.getIdentifier());
			lot.setPlanClassementPAC(cinesPAC);
		}

		final OmekaListDTO collecOmeka = lotDTO.getOmekaCollection();
		if (collecOmeka != null && collecOmeka.getIdentifier() != null) {
			final OmekaList omekaCollection = omekaListService.findOne(collecOmeka.getIdentifier());
			lot.setOmekaCollection(omekaCollection);
		}
		else {
			lot.setOmekaCollection(null);
		}

		final OmekaListDTO itemOmeka = lotDTO.getOmekaItem();
		if (itemOmeka != null && itemOmeka.getIdentifier() != null) {
			final OmekaList omekaItem = omekaListService.findOne(itemOmeka.getIdentifier());
			lot.setOmekaItem(omekaItem);
		}
		else {
			lot.setOmekaItem(null);
		}

		final OmekaConfigurationDTO omekaConfDTO = lotDTO.getOmekaConfiguration();
		if (omekaConfDTO != null && omekaConfDTO.getIdentifier() != null) {
			final OmekaConfiguration omekaConf = omekaConfigurationService.findOne(omekaConfDTO.getIdentifier());
			lot.setOmekaConfiguration(omekaConf);
		}
		else {
			lot.setOmekaConfiguration(null);
		}

		final SimpleUserDTO providerDto = lotDTO.getProvider();
		if (providerDto != null && providerDto.getIdentifier() != null) {
			lot.setProvider(userService.findByIdentifier(providerDto.getIdentifier()));
		}
		else {
			lot.setProvider(null);
		}

	}

}
