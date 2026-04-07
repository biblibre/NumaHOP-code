package org.numahop.numahop.service.document.conditionreport;

import static org.numahop.numahop.exception.message.PgcnErrorCode.DESC_VALUE_PROPERTY_MANDATORY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.numahop.numahop.domain.document.conditionreport.DescriptionProperty;
import org.numahop.numahop.domain.document.conditionreport.DescriptionValue;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.exception.message.PgcnErrorCode;
import org.numahop.numahop.repository.document.conditionreport.DescriptionRepository;
import org.numahop.numahop.repository.document.conditionreport.DescriptionValueRepository;
import org.numahop.numahop.util.TestUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DescriptionValueServiceTest {

	@Mock
	private DescriptionRepository descriptionRepository;

	@Mock
	private DescriptionValueRepository descriptionValueRepository;

	private DescriptionValueService service;

	@BeforeEach
	public void setUp() {
		service = new DescriptionValueService(descriptionRepository, descriptionValueRepository);
	}

	@Test
	public void testFindByProperty() {
		final List<DescriptionValue> values = new ArrayList<>();
		when(descriptionValueRepository.findByPropertyIdentifier("id")).thenReturn(values);

		final List<DescriptionValue> actual = service.findByPropertyIdentifier("id");
		assertSame(values, actual);
	}

	@Test
	public void testFindAll() {
		final List<DescriptionValue> values = new ArrayList<>();
		when(descriptionValueRepository.findAll()).thenReturn(values);

		final List<DescriptionValue> actual = service.findAll();
		assertSame(values, actual);
	}

	@Test
	public void testDelete() {
		final String id = "ID-001";
		final DescriptionValue value = new DescriptionValue();
		when(descriptionValueRepository.findById(id)).thenReturn(Optional.of(value));

		service.delete(id);

		verify(descriptionValueRepository).delete(value);
	}

	@Test
	public void testSave() {
		when(descriptionValueRepository.save(any(DescriptionValue.class))).then(new ReturnsArgumentAt(0));

		// #1
		try {
			final DescriptionValue value = new DescriptionValue();
			service.save(value);
			fail("testSave failed");
		}
		catch (final PgcnValidationException e) {
			TestUtil.checkPgcnException(e, PgcnErrorCode.DESC_VALUE_LABEL_MANDATORY, DESC_VALUE_PROPERTY_MANDATORY);
		}

		// #2
		try {
			final DescriptionValue value = new DescriptionValue();
			value.setLabel("Label");
			value.setProperty(new DescriptionProperty());

			final DescriptionValue actual = service.save(value);
			assertSame(value, actual);

		}
		catch (final PgcnValidationException e) {
			fail("testSave failed");
		}
	}

}
