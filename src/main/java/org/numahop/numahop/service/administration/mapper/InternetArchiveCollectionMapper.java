package org.numahop.numahop.service.administration.mapper;

import org.numahop.numahop.domain.administration.InternetArchiveCollection;
import org.numahop.numahop.domain.dto.administration.InternetArchiveCollectionDTO;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author jbrunet Créé le 19 avr. 2017
 */
@Mapper()
public interface InternetArchiveCollectionMapper {

	InternetArchiveCollectionMapper INSTANCE = Mappers.getMapper(InternetArchiveCollectionMapper.class);

	InternetArchiveCollectionDTO collectionIAToDto(InternetArchiveCollection conf);

	Set<InternetArchiveCollectionDTO> collectionIAToDtos(Set<InternetArchiveCollection> conf);

}
