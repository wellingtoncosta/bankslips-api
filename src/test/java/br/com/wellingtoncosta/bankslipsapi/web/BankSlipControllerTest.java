package br.com.wellingtoncosta.bankslipsapi.web;

import br.com.wellingtoncosta.bankslipsapi.Application;
import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.service.BankSlipService;
import br.com.wellingtoncosta.bankslipsapi.web.json.BankSlipJson;
import br.com.wellingtoncosta.bankslipsapi.web.json.NewBankSlipJson;
import br.com.wellingtoncosta.bankslipsapi.web.json.PaymentJson;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.UUID;

import static br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip.Status.*;
import static br.com.wellingtoncosta.bankslipsapi.mocks.BankSlipMocks.oneBankSlip;
import static br.com.wellingtoncosta.bankslipsapi.mocks.BankSlipMocks.oneBankSlipWithOneDayLate;
import static br.com.wellingtoncosta.bankslipsapi.mocks.BankSlipMocks.threeBankSlips;
import static java.time.LocalDate.now;
import static java.util.Objects.isNull;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        bankSlip = null;
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test public void listBankSlipsWithEmptyDb() throws Exception {
        givenThatHaveNoBankSlipsInDb();
        whenListAllBankSlips();
        thenReceiveNothing();
    }

    @Test public void listBankSlipsWithThreeBankSlipsInDb() throws Exception {
        givenThatHaveThreeBankSlipsInDb();
        whenListAllBankSlips();
        thenReceiveThreeBankSlips();
    }

    @Test public void findExistentBankSlipById() throws Exception {
        givenThatHaveOneBankSlipInDb();
        whenFetchBankSlipById();
        thenReceiveABankSlip();
    }

    @Test public void findInexistentBankSlipById() throws Exception {
        givenThatHaveNoBankSlipsInDb();
        whenFetchBankSlipByInexistentId();
        thenReceiveNotFoundStatus();
    }

    @Test public void createNewValidBankSlip() throws Exception {
        givenThatHaveNoBankSlipsInDb();
        whenCreateANewValidBankSlip();
        thenReceiveACreatedBankSlip();
    }

    @Test public void createNewInvalidBankSlip() throws Exception {
        givenThatHaveNoBankSlipsInDb();
        whenCreateANewInvalidBankSlip();
        thenReceiveUnprocessableEntityStatus();
    }

    @Test public void payABankSlipExistentInDb() throws Exception {
        givenThatHaveOneBankSlipInDb();
        whenPayAExistentBankSlip();
        thenReceiveAPaidBankSlip();
    }

    @Test public void payABankSlipInexistentInDb() throws Exception {
        givenThatHaveNoBankSlipsInDb();
        whenPayAInexistentBankSlip();
        thenReceiveNotFoundStatus();
    }

    @Test public void cancelABankSlipExistentInDb() throws Exception {
        givenThatHaveOneBankSlipInDb();
        whenCancelAExistentBankSlip();
        thenReceiveACanceledBankSlip();
    }

    @Test public void cancelABankSlipInexistentInDb() throws Exception {
        givenThatHaveNoBankSlipsInDb();
        whenCancelAInexistentBankSlip();
        thenReceiveNotFoundStatus();
    }

    @Test public void createNewBankSlipWithValidDueDate() throws Exception {
        givenThatHaveNoBankSlipsInDb();
        whenCreateANewValidBankSlip();
        thenReceiveACreatedBankSlipWithZeroFineRate();
    }

    @Test public void createNewBankSlipWithOneDayLateDueDate() throws Exception {
        givenThatHaveNoBankSlipsInDb();
        whenCreateANewBankSlipWithOneDayLateDueDate();
        thenReceiveACreatedBankSlipWithOneDayFineRate();
    }

    // ----------------------------------------------------------------------------------------- //

    private void givenThatHaveNoBankSlipsInDb() {
        service.listAll().forEach(bankSlip -> service.delete(bankSlip.id));
    }

    private void givenThatHaveThreeBankSlipsInDb() {
        threeBankSlips().forEach(service::create);
    }

    private void givenThatHaveOneBankSlipInDb() {
        bankSlip = service.create(oneBankSlip());
    }

    private void whenListAllBankSlips() {
        request = get(URL);
    }

    private void whenFetchBankSlipById() {
        if(!isNull(bankSlip)) {
            request = get(URL + "/" + bankSlip.id);
        }
    }

    private void whenFetchBankSlipByInexistentId() {
        request = get(URL + "/" + UUID.randomUUID());
    }

    private void whenCreateANewValidBankSlip() throws Exception {
        bankSlip = oneBankSlip();
        NewBankSlipJson json = new NewBankSlipJson(
                bankSlip.dueDate,
                bankSlip.totalInCents,
                bankSlip.customer
        );

        request = post(URL)
                .content(toJson(json))
                .contentType(APPLICATION_JSON);
    }

    private void whenCreateANewInvalidBankSlip() throws Exception {
        bankSlip = oneBankSlip();
        NewBankSlipJson json = new NewBankSlipJson();
        json.setDueDate(bankSlip.dueDate);

        request = post(URL)
                .content(toJson(json))
                .contentType(APPLICATION_JSON);
    }

    private void whenPayAExistentBankSlip() throws Exception {
        if(!isNull(bankSlip)) {
            PaymentJson json = new PaymentJson();
            json.setPaymentDate(now());

            request = post(URL + "/" + bankSlip.id + "/payments")
                    .content(toJson(json))
                    .contentType(APPLICATION_JSON);
        }
    }

    private void whenPayAInexistentBankSlip() throws Exception {
        PaymentJson json = new PaymentJson();
        json.setPaymentDate(now());

        request = post(URL + "/" + UUID.randomUUID() + "/payments")
                .content(toJson(json))
                .contentType(APPLICATION_JSON);
    }

    private void whenCancelAExistentBankSlip() throws Exception {
        if(!isNull(bankSlip)) {
            PaymentJson json = new PaymentJson();
            json.setPaymentDate(now());

            request = delete(URL + "/" + bankSlip.id)
                    .content(toJson(json))
                    .contentType(APPLICATION_JSON);
        }
    }

    private void whenCancelAInexistentBankSlip() throws Exception {
        PaymentJson json = new PaymentJson();
        json.setPaymentDate(now());

        request = delete(URL + "/" + UUID.randomUUID())
                .content(toJson(json))
                .contentType(APPLICATION_JSON);
    }

    private void whenCreateANewBankSlipWithOneDayLateDueDate() throws Exception {
        bankSlip = oneBankSlipWithOneDayLate();
        NewBankSlipJson json = new NewBankSlipJson(
                bankSlip.dueDate,
                bankSlip.totalInCents,
                bankSlip.customer
        );

        request = post(URL)
                .content(toJson(json))
                .contentType(APPLICATION_JSON);
    }

    private void thenReceiveNothing() throws Exception {
        mvc.perform(request).andExpect(status().isNoContent());
    }

    private void thenReceiveThreeBankSlips() throws Exception {
        mvc.perform(request).andExpect(jsonPath("$", hasSize(3)));
    }

    private void thenReceiveABankSlip() throws Exception {
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bankSlip.id.toString())));
    }

    private void thenReceiveNotFoundStatus() throws Exception {
        mvc.perform(request).andExpect(status().isNotFound());
    }

    private void thenReceiveACreatedBankSlip() throws Exception {
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer", is(bankSlip.customer)))
                .andExpect(jsonPath("$.total_in_cents", is(bankSlip.totalInCents.toString())))
                .andExpect(jsonPath("$.status", is(PENDING.toString())));
    }

    private void thenReceiveUnprocessableEntityStatus() throws Exception {
        mvc.perform(request).andExpect(status().isUnprocessableEntity());
    }

    private void thenReceiveAPaidBankSlip() throws Exception {
        mvc.perform(request).andExpect(status().isNoContent());

        if(!isNull(bankSlip)) {
            MockHttpServletRequestBuilder anoterRequest = get(URL + "/" + bankSlip.id);
            mvc.perform(anoterRequest)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", is(PAID.toString())));
        }
    }

    private void thenReceiveACanceledBankSlip() throws Exception {
        mvc.perform(request).andExpect(status().isNoContent());

        if(!isNull(bankSlip)) {
            MockHttpServletRequestBuilder anoterRequest = get(URL + "/" + bankSlip.id);
            mvc.perform(anoterRequest)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", is(CANCELED.toString())));
        }
    }

    private MvcResult getCreatedBankSlip() throws Exception {
        return mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer", is(bankSlip.customer)))
                .andExpect(jsonPath("$.total_in_cents", is(bankSlip.totalInCents.toString())))
                .andExpect(jsonPath("$.status", is(PENDING.toString())))
                .andReturn();
    }

    private void thenReceiveACreatedBankSlipWithZeroFineRate() throws Exception {
        MvcResult result = getCreatedBankSlip();
        BankSlipJson json = mapper.readValue(
                result.getResponse().getContentAsString(),
                BankSlipJson.class
        );

        request = get(URL + "/" + json.getId());
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fine", is("0.00")));
    }

    private void thenReceiveACreatedBankSlipWithOneDayFineRate() throws Exception {
        MvcResult result = getCreatedBankSlip();
        BankSlipJson json = mapper.readValue(
                result.getResponse().getContentAsString(),
                BankSlipJson.class
        );

        request = get(URL + "/" + json.getId());
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fine", is("5.00")));
    }

    // ----------------------------------------------------------------------------------------- //

    private String toJson(Object source) throws JsonProcessingException {
        return mapper.writeValueAsString(source);
    }

}
