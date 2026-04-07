package org.numahop.numahop.repository.administration.z3950;

import org.numahop.numahop.domain.administration.exchange.z3950.Z3950Server;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Sébastien on 21/12/2016.
 */
public interface Z3950ServerRepository extends JpaRepository<Z3950Server, String> {

	List<Z3950Server> findByActive(boolean active);

	Z3950Server findOneByNameAndIdentifierNot(String name, String id);

	Z3950Server findOneByName(String name);

}
