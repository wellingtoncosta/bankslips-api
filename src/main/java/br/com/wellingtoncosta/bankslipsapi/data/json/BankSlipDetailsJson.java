package br.com.wellingtoncosta.bankslipsapi.data.json;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author Wellington Costa on 17/11/18
 */
@Value @Builder public class BankSlipDetailsJson {

    UUID id;
    Date dueDate;
    Date paymentDate;
    BigDecimal totalInCents;
    String customer;
    String status;

    @JsonCreator public BankSlipDetailsJson(
            @JsonProperty("id") final UUID id,
            @JsonProperty("due_date") final Date dueDate,
            @JsonProperty("payment_date") final Date paymentDate,
            @JsonProperty("total_in_cents") final BigDecimal totalInCents,
            @JsonProperty("customer") final String customer,
            @JsonProperty("status") final String status
    ) {
        this.id = id;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.totalInCents = totalInCents;
        this.customer = customer;
        this.status = status;
    }

}
