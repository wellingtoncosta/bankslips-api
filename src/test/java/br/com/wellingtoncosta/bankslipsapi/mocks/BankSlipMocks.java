package br.com.wellingtoncosta.bankslipsapi.mocks;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Wellington Costa on 17/11/18
 */
public final class BankSlipMocks {

    private BankSlipMocks() { }

    public static BankSlip oneBankSlip() {
        LocalDate localDate = LocalDate.now().plusDays(5);
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.of("GMT")));
        return BankSlip.builder()
                .dueDate(Date.from(instant))
                .paymentDate(null)
                .totalInCents(new BigDecimal("50.00"))
                .customer("Ford Prefect Company")
                .status(BankSlip.Status.PENDING)
                .build();

    }

    public static List<BankSlip> threeBankSlips() {
        LocalDate localDate = LocalDate.now().plusDays(5);
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.of("GMT")));
        return Stream.of(
                BankSlip.builder()
                        .dueDate(Date.from(instant))
                        .paymentDate(null)
                        .totalInCents(new BigDecimal("50.00"))
                        .customer("Ford Prefect Company")
                        .status(BankSlip.Status.PENDING)
                        .build(),
                BankSlip.builder()
                        .dueDate(Date.from(instant))
                        .paymentDate(null)
                        .totalInCents(new BigDecimal("100.00"))
                        .customer("Zaphod Company")
                        .status(BankSlip.Status.PENDING)
                        .build(),
                BankSlip.builder()
                        .dueDate(Date.from(instant))
                        .paymentDate(null)
                        .totalInCents(new BigDecimal("200.00"))
                        .customer("Trillian Company")
                        .status(BankSlip.Status.PENDING)
                        .build()
        ).collect(Collectors.toList());
    }

}
