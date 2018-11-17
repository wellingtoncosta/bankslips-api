package br.com.wellingtoncosta.bankslipsapi.domain.model;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Wellington Costa on 16/11/18
 */
@Value public class Payment {

    @NotNull(message = "Payment date cannot be null")
    public final Date paymentDate;

}
