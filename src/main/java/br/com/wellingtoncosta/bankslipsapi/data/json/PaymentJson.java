package br.com.wellingtoncosta.bankslipsapi.data.json;

import br.com.wellingtoncosta.bankslipsapi.domain.model.Payment;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Date;

/**
 * @author Wellington Costa on 17/11/18
 */
@Value public class PaymentJson {

    Date paymentDate;

    @JsonCreator public PaymentJson(
            @JsonProperty("payment_date") Date paymentDate
    ) {
        this.paymentDate = paymentDate;
    }

    public Payment toModel() {
        return new Payment(paymentDate);
    }

}
