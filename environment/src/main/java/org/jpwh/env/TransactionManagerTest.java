package org.jpwh.env;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TransactionManagerTest {

    @BeforeSuite()
    public void beforeSuite() {
        System.out.println("before");
    }

    @AfterSuite()
    public void afterSuite() {
        System.out.println("after");
    }
}
