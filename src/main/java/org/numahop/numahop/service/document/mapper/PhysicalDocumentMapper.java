package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.document.PhysicalDocument;
import org.numahop.numahop.domain.dto.document.ListPhysicalDocumentDTO;
import org.numahop.numahop.domain.dto.document.PhysicalDocumentDTO;
import org.numahop.numahop.domain.dto.document.SimplePhysicalDocumentDTO;
import org.numahop.numahop.service.project.mapper.ProjectMapper;
import org.numahop.numahop.service.train.mapper.SimpleTrainMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ProjectMapper.class, SimpleTrainMapper.class, SimpleDocUnitMapper.class })
public interface PhysicalDocumentMapper {

	PhysicalDocumentMapper INSTANCE = Mappers.getMapper(PhysicalDocumentMapper.class);

	SimplePhysicalDocumentDTO physicalDocumentToSimplePhysicalDocumentDTO(PhysicalDocument physicalDocument);

	PhysicalDocumentDTO physicalDocumentToPhysicalDocumentDTO(PhysicalDocument physicalDocument);

	ListPhysicalDocumentDTO physicalDocumentToListPhysicalDocumentDTO(PhysicalDocument pd);

}
