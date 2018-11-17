package br.com.wellingtoncosta.bankslipsapi.service;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.domain.model.Payment;
import br.com.wellingtoncosta.bankslipsapi.domain.repository.BankSlipRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Wellington Costa on 16/11/18
 */
@Service public class BankSlipService {

    private BankSlipRepository repository;

    @Inject @Lazy public void setRepository(BankSlipRepository repository) {
        this.repository = repository;
    }

    @Transactional public BankSlip create(BankSlip bankSlip) {
        return repository.create(bankSlip);
    }

    public List<BankSlip> listAll() {
        return repository.listAll();
    }

    public BankSlip findById(String uuid) {
        return repository.findById(uuid);
    }

    @Transactional public void delete(BankSlip bankSlip) {
        repository.delete(bankSlip);
    }

    @Transactional public void pay(String uuid, Payment payment) {

    }

    @Transactional public void cancel(String uuid) { }

}
