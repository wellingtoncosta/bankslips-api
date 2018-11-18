package br.com.wellingtoncosta.bankslipsapi.web.json;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Wellington Costa on 17/11/18
 */
@Data
@Builder
@JsonDeserialize(builder = NewBankSlipJson.NewBankSlipJsonBuilder.class)
public class NewBankSlipJson {

    @JsonProperty("due_date")
    @NotNull(message = "Due date cannot be null")
    private Date dueDate;

    @JsonProperty("total_in_cents")
    @NotNull(message = "Total in cents date cannot be null")
    private BigDecimal totalInCents;

    @JsonProperty("customer")
    @NotNull(message = "Customer cannot be null")
    private String customer;

    public BankSlip toModel() {
        return BankSlip.builder()
                .dueDate(dueDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .status(BankSlip.Status.PENDING)
                .build();
    }

}
