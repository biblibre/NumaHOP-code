package org.numahop.numahop.service.administration.mapper;

import org.numahop.numahop.domain.administration.omeka.OmekaList;
import org.numahop.numahop.domain.dto.administration.omeka.OmekaListDTO;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface OmekaListMapper {

	OmekaListMapper INSTANCE = Mappers.getMapper(OmekaListMapper.class);

	OmekaListDTO objToDto(OmekaList conf);

	OmekaList dtoToObj(OmekaListDTO dto);

	Set<OmekaListDTO> objsToDtos(Set<OmekaList> conf);

	List<OmekaList> dtosToObjs(List<OmekaListDTO> dto);

}
