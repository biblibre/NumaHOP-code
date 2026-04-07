package org.numahop.numahop.service.project.mapper;

import org.numahop.numahop.domain.administration.CinesPAC;
import org.numahop.numahop.domain.administration.ExportFTPDeliveryFolder;
import org.numahop.numahop.domain.administration.InternetArchiveCollection;
import org.numahop.numahop.domain.administration.omeka.OmekaList;
import org.numahop.numahop.domain.dto.administration.CinesPACDTO;
import org.numahop.numahop.domain.dto.administration.InternetArchiveCollectionDTO;
import org.numahop.numahop.domain.dto.administration.omeka.OmekaListDTO;
import org.numahop.numahop.domain.dto.library.SimpleLibraryDTO;
import org.numahop.numahop.domain.dto.project.ProjectDTO;
import org.numahop.numahop.domain.dto.user.SimpleUserDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.project.Project;
import org.numahop.numahop.service.administration.CinesPACService;
import org.numahop.numahop.service.administration.InternetArchiveCollectionService;
import org.numahop.numahop.service.administration.omeka.OmekaConfigurationService;
import org.numahop.numahop.service.administration.omeka.OmekaListService;
import org.numahop.numahop.service.administration.viewsformat.ViewsFormatConfigurationService;
import org.numahop.numahop.service.checkconfiguration.CheckConfigurationService;
import org.numahop.numahop.service.exportftpconfiguration.ExportFTPConfigurationService;
import org.numahop.numahop.service.ftpconfiguration.FTPConfigurationService;
import org.numahop.numahop.service.library.LibraryService;
import org.numahop.numahop.service.user.UserService;
import org.numahop.numahop.service.workflow.WorkflowModelService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UIProjectMapper {

	@Autowired
	private LibraryService libraryService;

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

	public void mapInto(final ProjectDTO projectDTO, final Project project) {
		// Library
		// Récupère libraryDTO du projectDTO
		final SimpleLibraryDTO libraryDTO = projectDTO.getLibrary();

		if (libraryDTO.getIdentifier() != null) {
			// Récupère bibliotheque depuis repository
			final Library library = libraryService.findOne(libraryDTO.getIdentifier());
			project.setLibrary(library);
		}

		project.setName(projectDTO.getName());
		project.setDescription(projectDTO.getDescription());
		project.setActive(projectDTO.isActive());
		project.setStartDate(projectDTO.getStartDate());
		project.setForecastEndDate(projectDTO.getForecastEndDate());
		project.setRealEndDate(projectDTO.getRealEndDate());

		// AssociatedLibraries
		final Set<SimpleLibraryDTO> libraryDTOs = projectDTO.getAssociatedLibraries();
		if (libraryDTOs != null) {
			project.setAssociatedLibraries(libraryDTOs.stream()
				.map(library -> libraryService.findOne(library.getIdentifier()))
				.collect(Collectors.toSet()));
		}
		// AssociatedUsers
		final Set<SimpleUserDTO> userDTOs = projectDTO.getAssociatedUsers();
		if (userDTOs != null) {
			project.setAssociatedUsers(userDTOs.stream()
				.map(user -> userService.findByIdentifier(user.getIdentifier()))
				.collect(Collectors.toSet()));
		}

		if (projectDTO.getActiveFTPConfiguration() != null) {
			project.setActiveFTPConfiguration(
					ftpConfigurationService.getOne(projectDTO.getActiveFTPConfiguration().getIdentifier()));
		}
		if (projectDTO.getActiveExportFTPConfiguration() != null) {
			project.setActiveExportFTPConfiguration(
					exportFTPConfigurationService.getOne(projectDTO.getActiveExportFTPConfiguration().getIdentifier()));
			// delivery folders update
			if (project.getActiveExportFTPConfiguration() != null
					&& projectDTO.getActiveExportFTPDeliveryFolder() != null) {
				ExportFTPDeliveryFolder newFolder = new ExportFTPDeliveryFolder();
				newFolder.setIdentifier(projectDTO.getActiveExportFTPDeliveryFolder().getIdentifier());
				newFolder.setName(projectDTO.getActiveExportFTPDeliveryFolder().getName());
				project.setActiveExportFTPDeliveryFolder(newFolder);
			}
			else {
				project.setActiveExportFTPDeliveryFolder(null);
			}
		}
		if (projectDTO.getActiveCheckConfiguration() != null) {
			project.setActiveCheckConfiguration(
					checkConfigurationService.findOne(projectDTO.getActiveCheckConfiguration().getIdentifier()));
		}
		if (projectDTO.getActiveFormatConfiguration() != null) {
			project.setActiveFormatConfiguration(
					viewsFormatConfigurationService.findOne(projectDTO.getActiveFormatConfiguration().getIdentifier()));
		}
		if (projectDTO.getWorkflowModel() != null) {
			project.setWorkflowModel(workflowModelService.getOne(projectDTO.getWorkflowModel().getIdentifier()));
		}
		if (projectDTO.getOmekaConfiguration() != null) {
			project.setOmekaConfiguration(
					omekaConfigurationService.findOne(projectDTO.getOmekaConfiguration().getIdentifier()));
		}

		final InternetArchiveCollectionDTO iaCollection = projectDTO.getCollectionIA();
		if (iaCollection != null && iaCollection.getIdentifier() != null) {
			final InternetArchiveCollection internetArchiveCollection = iaCollectionService
				.findOne(iaCollection.getIdentifier());
			project.setCollectionIA(internetArchiveCollection);
		}
		else {
			project.setCollectionIA(null);
		}

		if (projectDTO.getLicenseUrl() != null) {
			project.setLicenseUrl(projectDTO.getLicenseUrl());
		}

		final CinesPACDTO cinesPACDTO = projectDTO.getPlanClassementPAC();
		if (cinesPACDTO != null && cinesPACDTO.getIdentifier() != null) {
			final CinesPAC cinesPAC = cinesPACService.findOne(cinesPACDTO.getIdentifier());
			project.setPlanClassementPAC(cinesPAC);
		}
		else {
			project.setPlanClassementPAC(null);
		}

		final OmekaListDTO collecOmeka = projectDTO.getOmekaCollection();
		if (collecOmeka != null && collecOmeka.getIdentifier() != null) {
			final OmekaList omekaCollection = omekaListService.findOne(collecOmeka.getIdentifier());
			project.setOmekaCollection(omekaCollection);
		}
		else {
			project.setOmekaCollection(null);
		}

		final OmekaListDTO itemOmeka = projectDTO.getOmekaItem();
		if (itemOmeka != null && itemOmeka.getIdentifier() != null) {
			final OmekaList omekaItem = omekaListService.findOne(itemOmeka.getIdentifier());
			project.setOmekaItem(omekaItem);
		}
		else {
			project.setOmekaItem(null);
		}

		final SimpleUserDTO providerDto = projectDTO.getProvider();
		if (providerDto != null && providerDto.getIdentifier() != null) {
			project.setProvider(userService.findByIdentifier(providerDto.getIdentifier()));
		}
		else {
			project.setProvider(null);
		}

		if (projectDTO.getLibRespName() != null) {
			project.setLibRespName(projectDTO.getLibRespName());
		}
		if (projectDTO.getLibRespPhone() != null) {
			project.setLibRespPhone(projectDTO.getLibRespPhone());
		}
		if (projectDTO.getLibRespEmail() != null) {
			project.setLibRespEmail(projectDTO.getLibRespEmail());
		}
	}

}
