package br.com.wellingtoncosta.bankslipsapi.mocks;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.LocalDate.now;

/**
 * @author Wellington Costa on 17/11/18
 */
public final class BankSlipMocks {

    private BankSlipMocks() { }

    public static BankSlip oneBankSlip() {
        return BankSlip.builder()
                .dueDate(now().plusDays(1))
                .paymentDate(null)
                .totalInCents(new BigDecimal("100.00"))
                .customer("Ford Prefect Company")
                .status(BankSlip.Status.PENDING)
                .build();
    }

    public static BankSlip oneBankSlipWithOneDayLate() {
        return BankSlip.builder()
                .dueDate(now().minusDays(1))
                .paymentDate(null)
                .totalInCents(new BigDecimal("100.00"))
                .customer("Ford Prefect Company")
                .status(BankSlip.Status.PENDING)
                .build();
    }

    public static List<BankSlip> threeBankSlips() {
        return Stream.of(
                BankSlip.builder()
                        .dueDate(now().plusDays(5))
                        .paymentDate(null)
                        .totalInCents(new BigDecimal("100.00"))
                        .customer("Ford Prefect Company")
                        .status(BankSlip.Status.PENDING)
                        .build(),
                BankSlip.builder()
                        .dueDate(now().plusDays(5))
                        .paymentDate(null)
                        .totalInCents(new BigDecimal("200.00"))
                        .customer("Zaphod Company")
                        .status(BankSlip.Status.PENDING)
                        .build(),
                BankSlip.builder()
                        .dueDate(now().plusDays(5))
                        .paymentDate(null)
                        .totalInCents(new BigDecimal("300.00"))
                        .customer("Trillian Company")
                        .status(BankSlip.Status.PENDING)
                        .build()
        ).collect(Collectors.toList());
    }

}
