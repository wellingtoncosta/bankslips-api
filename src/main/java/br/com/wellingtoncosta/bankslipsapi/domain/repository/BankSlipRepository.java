package br.com.wellingtoncosta.bankslipsapi.domain.repository;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;

import java.util.List;

/**
 * @author Wellington Costa on 16/11/18
 */
public interface BankSlipRepository {

    BankSlip create(BankSlip bankSlip);

    BankSlip update(BankSlip bankSlip);

    List<BankSlip> listAll();

    BankSlip findById(String uuid);

    void delete(BankSlip bankSlip);

}
