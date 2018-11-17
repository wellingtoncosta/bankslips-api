package br.com.wellingtoncosta.bankslipsapi.web.json;

import br.com.wellingtoncosta.bankslipsapi.domain.model.Payment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Wellington Costa on 17/11/18
 */
@Data public class PaymentJson {

    @JsonProperty("payment_date") private Date paymentDate;

    public Payment toModel() {
        return new Payment(paymentDate);
    }

}
