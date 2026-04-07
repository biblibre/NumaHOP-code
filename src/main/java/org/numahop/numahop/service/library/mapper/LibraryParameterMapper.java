package org.numahop.numahop.service.library.mapper;

import org.numahop.numahop.domain.dto.library.AbstractLibraryParameterValueDTO;
import org.numahop.numahop.domain.dto.library.LibraryParameterDTO;
import org.numahop.numahop.domain.dto.library.LibraryParameterValueCinesDTO;
import org.numahop.numahop.domain.dto.library.LibraryParameterValuedDTO;
import org.numahop.numahop.domain.library.AbstractLibraryParameterValue;
import org.numahop.numahop.domain.library.LibraryParameter;
import org.numahop.numahop.domain.library.LibraryParameterValueCines;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SimpleLibraryMapper.class })
public interface LibraryParameterMapper {

	LibraryParameterMapper INSTANCE = Mappers.getMapper(LibraryParameterMapper.class);

	LibraryParameterValueCinesDTO libParamCinesValueToDTO(LibraryParameterValueCines libraryParameterValueCines);

	LibraryParameterDTO libraryParameterToLibraryParameterDTO(LibraryParameter libraryParameter);

	@Mappings({ @Mapping(target = "values", ignore = true) })
	LibraryParameterValuedDTO libParamToLibParamValuedDTO(LibraryParameter libraryParameter);

	AbstractLibraryParameterValueDTO abstractParameterValueToDTO(AbstractLibraryParameterValue libraryParameterValue);

}
