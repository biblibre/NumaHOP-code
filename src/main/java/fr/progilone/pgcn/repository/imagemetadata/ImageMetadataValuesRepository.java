package fr.progilone.pgcn.repository.imagemetadata;

import fr.progilone.pgcn.domain.imagemetadata.ImageMetadataProperty;
import fr.progilone.pgcn.domain.imagemetadata.ImageMetadataValue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageMetadataValuesRepository extends JpaRepository<ImageMetadataValue, String> {

	Integer countByMetadata(ImageMetadataProperty metadata);

	List<ImageMetadataValue> findAllByDocUnitIdentifier(String id);

	List<ImageMetadataValue> findByDocUnitIdentifierIn(List<String> docUnitId);

	@Query("""
			select v from ImageMetadataValue v
			join fetch v.metadata
			where v.docUnit.identifier = ?1
			""")
	List<ImageMetadataValue> findAllByDocUnitIdentifierWithDependencies(String id);

	void deleteByDocUnitIdentifier(String identifier);

	@Query("""
			select values from ImageMetadataValue values
			join fetch values.docUnit
			join fetch values.metadata
			where values.identifier = ?1
			""")
	ImageMetadataValue findOneWithDependencies(String identifier);

}
