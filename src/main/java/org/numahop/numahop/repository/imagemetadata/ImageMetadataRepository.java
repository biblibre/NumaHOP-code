package org.numahop.numahop.repository.imagemetadata;

import org.numahop.numahop.domain.imagemetadata.ImageMetadataProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadataProperty, String> {

	ImageMetadataProperty findOneImageMetadataPropertyByIdentifier(String label);

}
