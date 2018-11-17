package br.com.wellingtoncosta.bankslipsapi.domain.model;

import br.com.wellingtoncosta.bankslipsapi.data.entity.BankSlipEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author Wellington Costa on 16/11/18
 */
@Data @Builder public class BankSlip {

    private UUID id;
    private Date dueDate;
    private Date paymentDate;
    private BigDecimal totalInCents;
    private String customer;
    private Status status;

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

}
