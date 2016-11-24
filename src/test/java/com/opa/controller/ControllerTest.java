package com.opa.controller;

import java.nio.charset.Charset;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ControllerTest {

	@Autowired
	public WebApplicationContext context;
	public MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();
	}

	public ResultActions get(final String url) throws Exception {
		return mvc.perform(MockMvcRequestBuilders.get(url));
	}

	public ResultActions post(final String url, final Object postMessage) throws Exception {
		final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(url);
		builder.content(getJson(postMessage));
		builder.contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
				Charset.forName("utf8")));

		return mvc.perform(builder);
	}

	public ResultActions put(final String url, final Object putMessage) throws Exception {
		final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(url);
		builder.content(getJson(putMessage));
		builder.contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
				Charset.forName("utf8")));

		return mvc.perform(builder);
	}

	public ResultActions delete(final String url, final String id) throws Exception {
		return mvc.perform(MockMvcRequestBuilders.delete(url + "/" + id));
	}

	public String getJson(final Object postMessage) throws Exception {
		return new ObjectMapper().writeValueAsString(postMessage);
	}

	public Object parseJson(final MvcResult result, final Class clazz) throws Exception {
		final String content = result.getResponse().getContentAsString();
		return new ObjectMapper().readValue(content, clazz);
	}
}