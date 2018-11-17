package br.com.wellingtoncosta.bankslipsapi.domain.repository;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;

import java.util.List;
import java.util.UUID;

/**
 * @author Wellington Costa on 16/11/18
 */
public interface BankSlipRepository {

    BankSlip create(BankSlip bankSlip);

    BankSlip update(BankSlip bankSlip);

    List<BankSlip> listAll();

    BankSlip findById(UUID uuid);

    void delete(UUID uuid);

}
