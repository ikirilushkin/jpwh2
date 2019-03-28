package org.jpwh.helloworld;

import org.jpwh.env.TransactionManagerTest;
import org.testng.annotations.Test;

public class HelloWorldHibernate extends TransactionManagerTest {

    @Test
    public void doSome() {
        System.out.println("test some");
        TM.getUserTransaction();
    }
}
