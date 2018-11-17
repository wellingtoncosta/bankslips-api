package br.com.wellingtoncosta.bankslipsapi.web;

import br.com.wellingtoncosta.bankslipsapi.Application;
import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.service.BankSlipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static br.com.wellingtoncosta.bankslipsapi.mocks.BankSlipMocks.threeBankSlips;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wellington Costa on 17/11/18
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class BankSlipControllerTest {

    @Inject private WebApplicationContext context;

    @Inject private ObjectMapper mapper;

    @Inject private BankSlipService service;

    private MockMvc mvc;

    private static final String URL = "/rest/bankslips/";

    private MockHttpServletRequestBuilder request;

    private BankSlip bankSlip;

    @Before public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test public void listBankSlipsWithEmptyDb() throws Exception {
        givenThatHaveNoBankSlips();
        whenListAllBankSlips();
        thenReceiveAEmptyList();
    }

    @Test public void listBankSlipsWithThreeBankSlipsInDb() throws Exception {
        givenThatHaveThreeBankSlipsInDb();
        whenListAllBankSlips();
        thenReceiveAListWithSize(3);
    }

    // ----------------------------------------------------------------------------------------- //

    private void givenThatHaveNoBankSlips() {
        service.listAll().forEach(service::delete);
    }

    private void givenThatHaveThreeBankSlipsInDb() {
        threeBankSlips().forEach(service::create);
    }

    private void whenListAllBankSlips() {
        request = get(URL);
    }

    private void thenReceiveAEmptyList() throws Exception {
        mvc.perform(request).andExpect(status().isNoContent());
    }

    private void thenReceiveAListWithSize(int size) throws Exception {
        mvc.perform(request).andExpect(jsonPath("$", hasSize(size)));
    }

}
