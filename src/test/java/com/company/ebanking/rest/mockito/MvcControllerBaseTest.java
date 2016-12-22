package com.company.ebanking.rest.mockito;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class MvcControllerBaseTest extends MockitoBaseTest {

    protected MockMvc mockMvc;

    protected final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
	    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName(StandardCharsets.UTF_8.name()));

    @Before
    public void setUp() {
	mockMvc = MockMvcBuilders.standaloneSetup(getController()).build();
    }

    protected abstract Object getController();

}