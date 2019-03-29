package org.jpwh.env;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.logging.Logger;

public class TransactionManagerSetup {

    private static final Logger logger = Logger.getLogger(TransactionManagerSetup.class.getName());

    private final Context context = new InitialContext();

    private final PoolingDataSource dataSource;

    public TransactionManagerSetup() throws NamingException {
        logger.fine("Starting database connection pool");
        logger.fine("Setting stable unique identifier for transaction recovery");
        TransactionManagerServices.getConfiguration().setServerId("myServer1234");
        dataSource = new PoolingDataSource();
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(5);
        dataSource.setUniqueName("myDS");
        dataSource.setClassName(JdbcDataSource.class.getName());
        dataSource.getDriverProperties().put("URL", "jdbc:h2:mem:test");
        dataSource.getDriverProperties().put("user", "sa");

        dataSource.setAllowLocalTransactions(true);
        dataSource.init();
        System.out.println(dataSource);
    }

    public Context getNamingContext() {
        return context;
    }

    public UserTransaction getUserTransaction() {
        try {
            return (UserTransaction) getNamingContext().lookup("java:comp/UserTransaction");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        logger.fine("Stopping database connection pool");
        dataSource.close();
        TransactionManagerServices.getTransactionManager().shutdown();
    }

    public void rollback() {
        UserTransaction tx = getUserTransaction();
        try {
            if (tx.getStatus() == Status.STATUS_ACTIVE ||
                    tx.getStatus() == Status.STATUS_MARKED_ROLLBACK)
                tx.rollback();
        } catch (SystemException e) {
            System.err.println("Rollback of transaction failed, trace follows!");
            e.printStackTrace(System.err);
        }
    }
}
