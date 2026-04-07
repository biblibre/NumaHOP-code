package org.numahop.numahop.repository.sample;

import org.numahop.numahop.domain.document.sample.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SampleRepository extends JpaRepository<Sample, String> {

	Sample findByDeliveryIdentifier(String deliveryIdId);

	@Query("""
			select s from Sample s
			left join fetch s.delivery d
			left join fetch d.lot
			where s.identifier = ?1
			""")
	Sample getSampleWithDep(String identifier);

}
