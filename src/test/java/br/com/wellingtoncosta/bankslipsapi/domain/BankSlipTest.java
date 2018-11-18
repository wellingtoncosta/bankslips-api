package br.com.wellingtoncosta.bankslipsapi.domain;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip.DECIMAL_PRECISION;
import static br.com.wellingtoncosta.bankslipsapi.mocks.BankSlipMocks.oneBankSlip;
import static br.com.wellingtoncosta.bankslipsapi.mocks.BankSlipMocks.oneBankSlipWithOneDayLate;
import static java.util.Objects.isNull;
import static org.junit.Assert.assertEquals;

/**
 * @author Wellington Costa on 18/11/18
 */
@RunWith(JUnit4.class)
public class BankSlipTest {

    private BankSlip bankSlip;
    private BigDecimal fine;

    private static final BigDecimal ZERO_FINE_RATE = new BigDecimal("0.00");

    @Test public void bankSlipWithValidDueDate() {
        givenThatHaveABankSlipWithValidDueDate();
        whenCalculateInterest();
        thenReceiveZeroFineRate();
    }

    @Test public void bankSlipWithOneDayLateDueDate() {
        givenThatHaveABankSlipWithOutdatedDueDate();
        whenCalculateInterest();
        thenReceiveFineRateForOneDay();
    }

    // ----------------------------------------------------------------------------------------- //

    private void givenThatHaveABankSlipWithValidDueDate() {
        bankSlip = oneBankSlip();
    }

    private void givenThatHaveABankSlipWithOutdatedDueDate() {
        bankSlip = oneBankSlipWithOneDayLate();
    }

    private void whenCalculateInterest() {
        fine = isNull(bankSlip) ? ZERO_FINE_RATE : bankSlip.calculateInterest();
    }

    private void thenReceiveZeroFineRate() {
        assertEquals(fine, new BigDecimal("0.00"));
    }

    private void thenReceiveFineRateForOneDay() {
        BigDecimal expectedFine = new BigDecimal("5.00")
                .setScale(DECIMAL_PRECISION, BigDecimal.ROUND_HALF_EVEN);

        assertEquals(fine, expectedFine);
    }

}
