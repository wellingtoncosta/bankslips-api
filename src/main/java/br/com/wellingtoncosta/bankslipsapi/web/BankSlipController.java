package br.com.wellingtoncosta.bankslipsapi.web;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.service.BankSlipService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Wellington Costa on 17/11/18
 */
@RestController
@RequestMapping("rest/bankslips")
public class BankSlipController {

    private BankSlipService service;

    @Inject @Lazy public void setService(BankSlipService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BankSlip>> listAll() {
        List<BankSlip> bankSlips = service.listAll();
        if(bankSlips.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bankSlips);
        }
    }

}
