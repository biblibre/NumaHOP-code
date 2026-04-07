package org.numahop.numahop.service.multilotsdelivery.ui;

import org.numahop.numahop.domain.delivery.Delivery;
import org.numahop.numahop.domain.dto.delivery.SimpleDeliveryLotDTO;
import org.numahop.numahop.domain.dto.document.PreDeliveryDocumentDTO;
import org.numahop.numahop.domain.dto.document.PreDeliveryLockedDocsDTO;
import org.numahop.numahop.domain.dto.lot.SimpleLotForDeliveryDTO;
import org.numahop.numahop.domain.dto.multilotsdelivery.MultiLotsDeliveryDTO;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.domain.multilotsdelivery.MultiLotsDelivery;
import org.numahop.numahop.domain.util.CustomUserDetails;
import org.numahop.numahop.exception.PgcnTechnicalException;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnErrorCode;
import org.numahop.numahop.exception.message.PgcnList;
import org.numahop.numahop.security.SecurityUtils;
import org.numahop.numahop.service.delivery.DeliveryAsyncService;
import org.numahop.numahop.service.delivery.DeliveryProcessResults;
import org.numahop.numahop.service.delivery.DeliveryProcessService;
import org.numahop.numahop.service.es.EsDeliveryService;
import org.numahop.numahop.service.lot.mapper.LotMapper;
import org.numahop.numahop.service.multilotsdelivery.MultiLotsDeliveryService;
import org.numahop.numahop.service.multilotsdelivery.mapper.MultiLotsDeliveryMapper;
import org.numahop.numahop.service.multilotsdelivery.mapper.UIMultiLotsDeliveryMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UIMultiLotsDeliveryService {

	private static final Logger LOG = LoggerFactory.getLogger(UIMultiLotsDeliveryService.class);

	private final MultiLotsDeliveryService multiService;

	private final UIMultiLotsDeliveryMapper uiMapper;

	private final DeliveryProcessService deliveryProcessService;

	private final DeliveryAsyncService deliveryAsyncService;

	private final EsDeliveryService esDeliveryService;

	@Autowired
	public UIMultiLotsDeliveryService(final MultiLotsDeliveryService multiService,
			final UIMultiLotsDeliveryMapper uiMapper, final DeliveryProcessService deliveryProcessService,
			final DeliveryAsyncService deliveryAsyncService, final EsDeliveryService esDeliveryService) {
		this.multiService = multiService;
		this.uiMapper = uiMapper;
		this.deliveryProcessService = deliveryProcessService;
		this.deliveryAsyncService = deliveryAsyncService;
		this.esDeliveryService = esDeliveryService;
	}

	/**
	 * Recherche paramétrée
	 * @param search
	 * @param projects
	 * @param lots
	 * @param status
	 * @param dateFrom
	 * @param dateTo
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<MultiLotsDeliveryDTO> search(final String search, final List<String> libraries,
			final List<String> projects, final List<String> lots, final List<String> providers,
			final List<Delivery.DeliveryStatus> status, final LocalDate dateFrom, final LocalDate dateTo,
			final Integer page, final Integer size) {

		final Page<MultiLotsDelivery> multiDeliveries = multiService.search(search, libraries, projects, lots,
				providers, status, dateFrom, dateTo, page, size);
		return multiDeliveries.map(MultiLotsDeliveryMapper.INSTANCE::objToDto);
	}

	@Transactional(readOnly = true)
	public MultiLotsDelivery getOne(final String id) {

		return multiService.getOne(id);
	}

	@Transactional(readOnly = true)
	public MultiLotsDeliveryDTO findOneByIdWithDeliveries(final String id) throws PgcnValidationException {

		final MultiLotsDelivery multiDeliv = multiService.findOneByIdWithDeliveries(id);
		return MultiLotsDeliveryMapper.INSTANCE.objToDto(multiDeliv);
	}

	@Transactional
	public MultiLotsDeliveryDTO create(final MultiLotsDeliveryDTO request) throws PgcnValidationException {
		validate(request);
		final MultiLotsDelivery multiDeliv = new MultiLotsDelivery();
		uiMapper.mapInto(request, multiDeliv);

		final MultiLotsDelivery savedMulti = multiService.save(multiDeliv);
		return MultiLotsDeliveryMapper.INSTANCE.objToDto(savedMulti);
	}

	@Transactional
	public MultiLotsDeliveryDTO update(final MultiLotsDeliveryDTO request) throws PgcnValidationException {
		validate(request);
		final MultiLotsDelivery multiDeliv = multiService.getOne(request.getIdentifier());
		uiMapper.mapInto(request, multiDeliv);

		final MultiLotsDelivery savedMulti = multiService.save(multiDeliv);
		return MultiLotsDeliveryMapper.INSTANCE.objToDto(savedMulti);
	}

	@Transactional
	public void delete(final String identifier) {
		multiService.delete(identifier);
	}

	/**
	 * Validation du multiLotsDelivery DTO avant le merge
	 */
	private PgcnList<PgcnError> validate(final MultiLotsDeliveryDTO dto) throws PgcnValidationException {
		final PgcnList<PgcnError> errors = new PgcnList<>();
		final PgcnError.Builder builder = new PgcnError.Builder();

		// TODO : Le label est unique PAR LIBRARY
		// if (StringUtils.isNotBlank(dto.getLabel())) {
		// final Delivery duplicate = deliveryService.findOneByLabel(dto.getLabel());
		//
		// if (duplicate != null && (dto.getIdentifier() == null ||
		// !duplicate.getIdentifier().equalsIgnoreCase(dto.getIdentifier()))) {
		// errors.add(builder.reinit().setCode(PgcnErrorCode.DELIVERY_DUPLICATE_LABEL).setField("label").build());
		// }
		// }

		// Au moins 1 lot obligatoire.
		if (CollectionUtils.isEmpty(dto.getLots())) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.DELIVERY_LOT_MANDATORY).setField("lot").build());
		}

		// Retour
		if (!errors.isEmpty()) {
			dto.setErrors(errors);
			throw new PgcnValidationException(dto, errors);
		}
		return errors;
	}

	/**
	 * @param multiDelivery
	 * @param lockedDocs
	 * @param metaDatas
	 * @param prefixToExclude
	 * @throws PgcnTechnicalException
	 */
	public void deliver(final MultiLotsDeliveryDTO multiDelivery, final List<PreDeliveryLockedDocsDTO> lockedDocs,
			final List<PreDeliveryDocumentDTO> metaDatas, final List<String> prefixToExclude)
			throws PgcnTechnicalException {

		final Map<String, DeliveryProcessResults> allProcessElems = new HashMap<>();
		final List<String> deliveryIds = multiDelivery.getDeliveries()
			.stream()
			.map(SimpleDeliveryLotDTO::getIdentifier)
			.collect(Collectors.toList());

		final CustomUserDetails user = SecurityUtils.getCurrentUser();
		final String libraryId = user != null ? user.getLibraryId() : null;

		// Synchrone
		for (final SimpleDeliveryLotDTO delivery : multiDelivery.getDeliveries()) {

			final Optional<PreDeliveryLockedDocsDTO> lockedIds = lockedDocs.stream()
				.filter(o -> StringUtils.equals(delivery.getLabel(), o.getDeliveryLabel()))
				.findFirst();
			final List<String> lockedForDeliv;
			if (lockedIds.isPresent()) {
				lockedForDeliv = lockedIds.get().getLockedDocsIdentifiers();
			}
			else {
				lockedForDeliv = new ArrayList<>();
			}

			final DeliveryProcessResults processElement = deliveryProcessService.deliver(delivery.getIdentifier(),
					lockedForDeliv, prefixToExclude, metaDatas, libraryId);
			allProcessElems.put(delivery.getIdentifier(), processElement);
			LOG.debug("LIVRAISON MULTI : Preparation pour la livraison {} [{}]", delivery.getLabel(),
					delivery.getIdentifier());
		}

		// Asynchrone
		for (final String identifier : deliveryIds) {
			esDeliveryService.indexAsync(identifier);
			LOG.debug("LIVRAISON MULTI : lancement process asynchrone - delivery {}", identifier);
			deliveryAsyncService.processDelivery(identifier, allProcessElems.get(identifier));
		}

	}

	@Transactional(readOnly = true)
	public List<SimpleLotForDeliveryDTO> findLotsByTrainIdentifier(final String trainId) {

		final List<Lot> lots = multiService.findLotsByTrainIdentifier(trainId);
		return lots.stream().map(LotMapper.INSTANCE::lotToSimpleLotForDeliveryDTO).collect(Collectors.toList());
	}

}
