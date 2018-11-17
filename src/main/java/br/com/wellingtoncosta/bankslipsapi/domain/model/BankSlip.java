package br.com.wellingtoncosta.bankslipsapi.domain.model;

import br.com.wellingtoncosta.bankslipsapi.data.entity.BankSlipEntity;
import br.com.wellingtoncosta.bankslipsapi.data.json.BankSlipDetailsJson;
import br.com.wellingtoncosta.bankslipsapi.data.json.BankSlipJson;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author Wellington Costa on 16/11/18
 */
@Value @Builder public class BankSlip {

    public final UUID id;
    public final Date dueDate;
    public final Date paymentDate;
    public final BigDecimal totalInCents;
    public final String customer;
    public final Status status;

    public enum Status { PENDING, PAID, CANCELED }

    public BankSlipEntity toEntity() {
        return BankSlipEntity.builder()
                .id(id)
                .dueDate(dueDate)
                .paymentDate(paymentDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .status(status.toString())
                .build();
    }

    public BankSlipJson toJson() {
        return BankSlipJson.builder()
                .id(id)
                .dueDate(dueDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .status(status.toString())
                .build();
    }

    public BankSlipDetailsJson toDetailedJson() {
        return BankSlipDetailsJson.builder()
                .id(id)
                .dueDate(dueDate)
                .paymentDate(paymentDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .status(status.toString())
                .build();
    }

}
