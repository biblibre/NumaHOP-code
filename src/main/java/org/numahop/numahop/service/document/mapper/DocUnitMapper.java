package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.DocUnit;
import org.numahop.numahop.domain.dto.document.DocUnitDTO;
import org.numahop.numahop.domain.dto.document.SummaryDocUnitDTO;
import org.numahop.numahop.domain.dto.document.SummaryDocUnitWithLotDTO;
import org.numahop.numahop.service.administration.mapper.CinesPACMapper;
import org.numahop.numahop.service.administration.mapper.InternetArchiveCollectionMapper;
import org.numahop.numahop.service.administration.mapper.OmekaConfigurationMapper;
import org.numahop.numahop.service.administration.mapper.OmekaListMapper;
import org.numahop.numahop.service.check.mapper.AutomaticCheckResultMapper;
import org.numahop.numahop.service.library.mapper.LibraryMapper;
import org.numahop.numahop.service.lot.mapper.LotMapper;
import org.numahop.numahop.service.ocrlangconfiguration.mapper.OcrLanguageMapper;
import org.numahop.numahop.service.project.mapper.ProjectMapper;
import org.numahop.numahop.service.project.mapper.SimpleProjectMapper;
import org.numahop.numahop.service.workflow.mapper.WorkflowMapper;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AutomaticCheckResultMapper.class, BibliographicRecordMapper.class, DigitalDocumentMapper.class,
		LibraryMapper.class, LotMapper.class, PhysicalDocumentMapper.class, SimpleProjectMapper.class,
		ProjectMapper.class, InternetArchiveCollectionMapper.class, CinesPACMapper.class, WorkflowMapper.class,
		OmekaListMapper.class, OmekaConfigurationMapper.class, OcrLanguageMapper.class })
public abstract class DocUnitMapper {

	public static final DocUnitMapper INSTANCE = Mappers.getMapper(DocUnitMapper.class);

	@Mappings({ @Mapping(target = "parentIdentifier", ignore = true), @Mapping(target = "parentPgcnId", ignore = true),
			@Mapping(target = "parentLabel", ignore = true), @Mapping(target = "nbChildren", ignore = true),
			@Mapping(target = "omekaConfiguration", source = "doc.lot.omekaConfiguration") })
	public abstract DocUnitDTO docUnitToDocUnitDTO(DocUnit doc);

	public abstract SummaryDocUnitDTO docUnitToDocUnitSummaryDTO(DocUnit doc);

	public abstract SummaryDocUnitWithLotDTO docUnitToDocUnitSummaryWithLotDTO(DocUnit doc);

	/**
	 * Alimentation des infos concernant la hiérarchie au statut AVAILABLE
	 * @param doc
	 * @param dto
	 */
	@AfterMapping
	protected void updateHierarchy(final DocUnit doc, @MappingTarget final DocUnitDTO dto) {
		final DocUnit parent = doc.getParent();
		if (parent != null && parent.getState() == DocUnit.State.AVAILABLE) {
			dto.setParentIdentifier(parent.getIdentifier());
			dto.setParentLabel(parent.getLabel());
			dto.setParentPgcnId(parent.getPgcnId());
		}
		final long nbChildren = doc.getChildren()
			.stream()
			.filter(ch -> ch.getState() == DocUnit.State.AVAILABLE)
			.count();
		dto.setNbChildren((int) nbChildren);
		final long nbSiblings = doc.getSibling() == null ? 0
				: doc.getSibling()
					.getDocUnits()
					.stream()
					.filter(ch -> ch.getState() == DocUnit.State.AVAILABLE
							&& !StringUtils.equals(ch.getIdentifier(), doc.getIdentifier()))
					.count();
		dto.setNbSiblings((int) nbSiblings);
		// on en profite pour recuperer le statut
		dto.setState(doc.getState().name());
	}

}
