package fr.progilone.pgcn.web.rest.document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.progilone.pgcn.domain.document.DocPropertyType;
import fr.progilone.pgcn.domain.dto.document.DocPropertyTypeDTO;
import fr.progilone.pgcn.service.document.DocPropertyTypeService;
import fr.progilone.pgcn.service.document.ui.UIDocPropertyTypeService;
import fr.progilone.pgcn.util.TestUtil;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class DocPropertyTypeControllerTest {

	@Mock
	private DocPropertyTypeService docPropertyTypeService;

	@Mock
	private UIDocPropertyTypeService uiDocPropertyTypeService;

	private MockMvc restMockMvc;

	@BeforeEach
	public void setUp() {
		final DocPropertyTypeController controller = new DocPropertyTypeController(docPropertyTypeService,
				uiDocPropertyTypeService);
		this.restMockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testFindAll() throws Exception {
		final DocPropertyTypeDTO dto = new DocPropertyTypeDTO();
		dto.setIdentifier("ID-DTO");
		when(uiDocPropertyTypeService.findAllDTO()).thenReturn(Collections.singletonList(dto));

		this.restMockMvc
			.perform(get("/api/rest/docpropertytype").param("dto", "true").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].identifier").value(dto.getIdentifier()));
	}

	@Test
	public void testFindAllBySuperType() throws Exception {
		final DocPropertyTypeDTO dto = new DocPropertyTypeDTO();
		dto.setIdentifier("ID-DTO");
		when(uiDocPropertyTypeService.findAllDTOBySuperType(DocPropertyType.DocPropertySuperType.CUSTOM))
			.thenReturn(Collections.singletonList(dto));

		this.restMockMvc
			.perform(get("/api/rest/docpropertytype").param("dto", "true")
				.param("supertype", "CUSTOM")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].identifier").value(dto.getIdentifier()));
	}

	@Test
	public void testGetById() throws Exception {
		final DocPropertyType type = new DocPropertyType();
		type.setIdentifier("ID-DTO");
		when(docPropertyTypeService.findOne(type.getIdentifier())).thenReturn(type);

		this.restMockMvc
			.perform(get("/api/rest/docpropertytype/{id}", type.getIdentifier()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("identifier").value(type.getIdentifier()));
	}

	@Test
	public void testCreate() throws Exception {
		final DocPropertyType type = new DocPropertyType();
		type.setIdentifier("ID-DTO");
		type.setSuperType(DocPropertyType.DocPropertySuperType.DC);
		when(docPropertyTypeService.save(any(DocPropertyType.class))).thenReturn(type);

		// 403
		this.restMockMvc
			.perform(post("/api/rest/docpropertytype").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(type)))
			.andExpect(status().isForbidden());

		// 201
		type.setSuperType(DocPropertyType.DocPropertySuperType.CUSTOM);
		this.restMockMvc
			.perform(post("/api/rest/docpropertytype").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(type)))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("identifier").value(type.getIdentifier()));
	}

	@Test
	public void testDelete() throws Exception {
		final DocPropertyType type = new DocPropertyType();
		type.setIdentifier("ID-DTO");
		type.setSuperType(DocPropertyType.DocPropertySuperType.DC);
		when(docPropertyTypeService.findOne(type.getIdentifier())).thenReturn(type);

		// 403
		this.restMockMvc
			.perform(delete("/api/rest/docpropertytype/{id}", type.getIdentifier())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden());
		verify(docPropertyTypeService, never()).delete(type);

		// 200
		type.setSuperType(DocPropertyType.DocPropertySuperType.CUSTOM);

		this.restMockMvc
			.perform(delete("/api/rest/docpropertytype/{id}", type.getIdentifier())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		verify(docPropertyTypeService).delete(type);
	}

	@Test
	public void testUpdate() throws Exception {
		final DocPropertyType type = new DocPropertyType();
		type.setIdentifier("ID-DTO");
		type.setSuperType(DocPropertyType.DocPropertySuperType.DC);
		when(docPropertyTypeService.save(any(DocPropertyType.class))).thenReturn(type);

		// 200
		this.restMockMvc
			.perform(
					post("/api/rest/docpropertytype/{id}", type.getIdentifier()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(type)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("identifier").value(type.getIdentifier()));
	}

}
