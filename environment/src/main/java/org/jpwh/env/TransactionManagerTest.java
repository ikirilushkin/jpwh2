package org.jpwh.env;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TransactionManagerTest {
    public static TransactionManagerSetup TM;

    @BeforeSuite()
    public void beforeSuite() throws Exception {
        TM = new TransactionManagerSetup();
    }

    @AfterSuite()
    public void afterSuite() {
        if (TM != null) {
            TM.stop();
        }
    }
}
