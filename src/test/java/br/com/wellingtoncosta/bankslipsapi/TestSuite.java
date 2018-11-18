package br.com.wellingtoncosta.bankslipsapi;

import br.com.wellingtoncosta.bankslipsapi.domain.BankSlipTest;
import br.com.wellingtoncosta.bankslipsapi.web.BankSlipControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Wellington Costa on 17/11/18
 */
@RunWith(Suite.class)
@SuiteClasses({
        BankSlipTest.class,
        BankSlipControllerTest.class
})
public class TestSuite { }
