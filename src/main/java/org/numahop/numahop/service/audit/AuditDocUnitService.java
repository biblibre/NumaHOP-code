package org.numahop.numahop.service.audit;

import org.numahop.numahop.domain.document.DocUnit;
import org.numahop.numahop.domain.dto.audit.AuditDocUnitRevisionDTO;
import org.numahop.numahop.repository.audit.AuditDocUnitRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditDocUnitService {

	private final AuditDocUnitRepository auditDocUnitRepository;

	@Autowired
	public AuditDocUnitService(AuditDocUnitRepository auditDocUnitRepository) {
		this.auditDocUnitRepository = auditDocUnitRepository;
	}

	/**
	 * Récupère dans l'historique le docUnit correspondant à l'identifiant id et à la
	 * révision rev
	 * @param id
	 * @param rev
	 * @return
	 */
	@Transactional(readOnly = true)
	public DocUnit getEntity(final String id, final int rev) {
		return auditDocUnitRepository.getEntity(id, rev);
	}

	/**
	 * Liste les révisions du docUnit (et de ses règles) correspondant à l'identifiant id
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<AuditDocUnitRevisionDTO> getRevisions(final String id) {
		return auditDocUnitRepository.getRevisions(id);
	}

}
