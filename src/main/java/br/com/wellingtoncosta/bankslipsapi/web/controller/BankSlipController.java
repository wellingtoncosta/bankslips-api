package br.com.wellingtoncosta.bankslipsapi.web.controller;

import br.com.wellingtoncosta.bankslipsapi.web.json.BankSlipDetailsJson;
import br.com.wellingtoncosta.bankslipsapi.web.json.BankSlipJson;
import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.service.BankSlipService;
import br.com.wellingtoncosta.bankslipsapi.web.json.NewBankSlipJson;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
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

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BankSlipJson> create(@Valid @RequestBody NewBankSlipJson json) {
        if(isNull(json)) {
            return ResponseEntity.badRequest().build();
        }
        BankSlip bankSlip = service.create(json.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(bankSlip.toJson());
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BankSlipJson>> listAll() {
        List<BankSlip> bankSlips = service.listAll();
        if(bankSlips.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(
                    bankSlips.stream().map(BankSlip::toJson).collect(toList())
            );
        }
    }

    @GetMapping(value= "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BankSlipDetailsJson> findById(@PathVariable("id") String uuid) {
        try {
            BankSlip bankSlip = service.findById(UUID.fromString(uuid));
            if(isNull(bankSlip)) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(bankSlip.toDetailedJson());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
