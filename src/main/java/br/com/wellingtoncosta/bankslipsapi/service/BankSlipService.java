package br.com.wellingtoncosta.bankslipsapi.service;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.domain.model.Payment;
import br.com.wellingtoncosta.bankslipsapi.domain.repository.BankSlipRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip.Status.CANCELED;
import static br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip.Status.PAID;

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

    public BankSlip findById(UUID uuid) {
        return repository.findById(uuid);
    }

    @Transactional public void delete(UUID uuid) {
        repository.delete(uuid);
    }

    @Transactional public void pay(Payment payment, BankSlip bankSlip) {
        BankSlip bankSlipToPay = BankSlip.builder()
                .id(bankSlip.id)
                .dueDate(bankSlip.dueDate)
                .paymentDate(payment.paymentDate)
                .totalInCents(bankSlip.totalInCents)
                .customer(bankSlip.customer)
                .status(PAID)
                .build();

        repository.update(bankSlipToPay);
    }

    @Transactional public void cancel(BankSlip bankSlip) {
        BankSlip bankSlipToCancel = BankSlip.builder()
                .id(bankSlip.id)
                .dueDate(bankSlip.dueDate)
                .totalInCents(bankSlip.totalInCents)
                .customer(bankSlip.customer)
                .status(CANCELED)
                .build();

        repository.update(bankSlipToCancel);
    }

}
