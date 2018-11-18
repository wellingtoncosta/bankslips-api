package br.com.wellingtoncosta.bankslipsapi.domain.model;

import br.com.wellingtoncosta.bankslipsapi.data.entity.BankSlipEntity;
import br.com.wellingtoncosta.bankslipsapi.web.json.BankSlipDetailsJson;
import br.com.wellingtoncosta.bankslipsapi.web.json.BankSlipJson;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * @author Wellington Costa on 16/11/18
 */
@Value @Builder public class BankSlip {

    public UUID id;
    public LocalDate dueDate;
    public LocalDate paymentDate;
    public BigDecimal totalInCents;
    public String customer;
    public Status status;

    public enum Status { PENDING, PAID, CANCELED }

    public BankSlipEntity toEntity() {
        return BankSlipEntity.builder()
                .id(id)
                .dueDate(dueDate)
                .paymentDate(paymentDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .status(status.toString())
                .build();
    }

    public BankSlipJson toJson() {
        return BankSlipJson.builder()
                .id(id)
                .dueDate(dueDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .status(status.toString())
                .build();
    }

    public BankSlipDetailsJson toDetailedJson() {
        return BankSlipDetailsJson.builder()
                .id(id)
                .dueDate(dueDate)
                .paymentDate(paymentDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .fine(calculateInterest())
                .status(status.toString())
                .build();
    }

    public static final int DECIMAL_PRECISION = 2;

    private int LIMIT_OF_DAYS_TO_APPLY_HALF_INTEREST = 10;

    private final BigDecimal HALF_PERCENT_INTEREST_PER_DAY = new BigDecimal("0.05");
    private final BigDecimal ONE_PERCENT_INTEREST_PER_DAY = new BigDecimal("0.10");

    public BigDecimal calculateInterest() {
        if(isNull(getDueDate())) {
            return new BigDecimal("0.00");
        }

        LocalDate now = LocalDate.now();

        if(now.isBefore(dueDate)) {
            return new BigDecimal("0.00");
        }

        Period difference = Period.between(dueDate, now);
        int days = difference.getDays();

        if(days <= LIMIT_OF_DAYS_TO_APPLY_HALF_INTEREST) {
            return applyPercentInterestPerDay(days, HALF_PERCENT_INTEREST_PER_DAY);
        } else {
            return applyPercentInterestPerDay(days, ONE_PERCENT_INTEREST_PER_DAY);
        }
    }

    private BigDecimal applyPercentInterestPerDay(int days, BigDecimal interest) {
        return totalInCents
                .multiply(interest.multiply(new BigDecimal(days)))
                .setScale(DECIMAL_PRECISION, BigDecimal.ROUND_HALF_EVEN);
    }

    public static void main(String[] args) {
        System.out.println(LocalDate.now().toString());
    }

}
