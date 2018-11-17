package br.com.wellingtoncosta.bankslipsapi.web.json;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Wellington Costa on 17/11/18
 */
@Data public class NewBankSlipJson {

    @JsonProperty("due_date") private Date dueDate;
    @JsonProperty("total_in_cents") private BigDecimal totalInCents;
    @JsonProperty("customer") private String customer;

    public BankSlip toModel() {
        return BankSlip.builder()
                .dueDate(dueDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .status(BankSlip.Status.PENDING)
                .build();
    }

}
