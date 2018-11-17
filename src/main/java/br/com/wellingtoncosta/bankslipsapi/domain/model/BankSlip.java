package br.com.wellingtoncosta.bankslipsapi.domain.model;

import br.com.wellingtoncosta.bankslipsapi.data.entity.BankSlipEntity;
import br.com.wellingtoncosta.bankslipsapi.web.json.BankSlipDetailsJson;
import br.com.wellingtoncosta.bankslipsapi.web.json.BankSlipJson;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author Wellington Costa on 16/11/18
 */
@Value @Builder public class BankSlip {

    public final UUID id;

    @NotNull(message = "Due date cannot be null")
    public Date dueDate;

    public final Date paymentDate;

    @NotNull(message = "Total in cents date cannot be null")
    public BigDecimal totalInCents;

    @NotNull(message = "Customer cannot be null")
    public String customer;

    @NotNull(message = "Status cannot be null")
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
