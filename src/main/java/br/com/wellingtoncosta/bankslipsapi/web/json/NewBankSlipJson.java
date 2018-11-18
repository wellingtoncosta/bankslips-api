package br.com.wellingtoncosta.bankslipsapi.web.json;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

/**
 * @author Wellington Costa on 17/11/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewBankSlipJson {

    @JsonProperty("due_date")
    @NotNull(message = "Due date cannot be null")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;

    @JsonFormat(shape = STRING)
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
