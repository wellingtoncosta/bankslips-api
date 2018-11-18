package br.com.wellingtoncosta.bankslipsapi.web.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

/**
 * @author Wellington Costa on 17/11/18
 */
@Data @Builder public class BankSlipJson {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("due_date")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @JsonFormat(shape = STRING)
    @JsonProperty("total_in_cents")
    private BigDecimal totalInCents;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("status")
    private String status;

}
