package org.jpwh.env;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TransactionManagerTest {
    public static TransactionManagerSetup TM;

    @BeforeSuite()
    public void beforeSuite() throws Exception {
        TM = new TransactionManagerSetup();
        System.out.println("before");
    }

    @AfterSuite()
    public void afterSuite() {
        System.out.println("after");
        if (TM != null) {
            TM.stop();
        }
    }
}
