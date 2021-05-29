package com.crash.dscatalog.tests.web;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.crash.dscatalog.dto.ProductDTO;
import com.crash.dscatalog.services.ProductService;
import com.crash.dscatalog.services.exceptions.DataBaseException;
import com.crash.dscatalog.services.exceptions.ResourceNotFoundException;
import com.crash.dscatalog.tests.factory.ProductFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductService service;
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductResourceTests.class);

	private long existingId;
	private long nonExistingId;
	private ProductDTO newProductDTO;
	private ProductDTO existingProductDTO;
	private PageImpl<ProductDTO> page;
	private long dependendId;
	private String operatorUsername;
	private String operatorPassword;
	
	@BeforeEach
	void setUp() throws Exception {
		operatorUsername = "alex@gmail.com";
		operatorPassword = "123456";
		existingId = 1L;
		nonExistingId = 2L;
		dependendId = 3L;
		newProductDTO = ProductFactory.createProductDTO(null);
		existingProductDTO = ProductFactory.createProductDTO(existingId);
		page = new PageImpl<>(List.of(existingProductDTO));
		when(service.findById(existingId)).thenReturn(existingProductDTO);
		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		when(service.findAllPaged(any(), anyString(), any())).thenReturn(page);
		when(service.insert(any())).thenReturn(existingProductDTO);
		when(service.update(any(), eq(existingId))).thenReturn(existingProductDTO);
		when(service.update(any(), eq(nonExistingId))).thenThrow(ResourceNotFoundException.class);
		doNothing().when(service).delete(existingId);
		doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
		doThrow(DataBaseException.class).when(service).delete(dependendId);
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
		String accessToken = obtainAccessToken(operatorUsername, operatorPassword);
		String jsonBody = objectMapper.writeValueAsString(newProductDTO);
		
//		String expectedName = newProductDTO.getName();
//		Double expectedPrice = newProductDTO.getPrice();
		
		mockMvc.perform(put("/products/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
//				.andExpect(jsonPath("$.id").exists())
//				.andExpect(jsonPath("$.id").value(existingId))
//				.andExpect(jsonPath("$.name").value(expectedName));
				
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		String accessToken = obtainAccessToken(operatorUsername, operatorPassword);
		String jsonBody = objectMapper.writeValueAsString(newProductDTO);
		
		mockMvc.perform(put("/products/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception{
		mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").exists());
	}
	
	@Test
	public void findByShouldReturnProductWhenIdExists() throws Exception{
		mockMvc.perform(get("/products/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(existingId));
	}

	@Test
	public void findByShouldReturnResourceNotFoundWhenIdDoesNotExists() throws Exception {
		mockMvc.perform(get("/products/{id}", nonExistingId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		 
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("client_id", clientId);
	    params.add("username", username);
	    params.add("password", password);
	 
	    ResultActions result 
	    	= mockMvc.perform(post("/oauth/token")
	    		.params(params)
	    		.with(httpBasic(clientId, clientSecret))
	    		.accept("application/json;charset=UTF-8"))
	        	.andExpect(status().isOk())
	        	.andExpect(content().contentType("application/json;charset=UTF-8"));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    String accessToken = jsonParser.parseMap(resultString).get("access_token").toString();
	    LOG.info("=================================================================" + accessToken);
	    return accessToken;
	}	
}