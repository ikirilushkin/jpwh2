package org.jpwh.env;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

public class TransactionManagerSetup {

    private final Context context = new InitialContext();

    private final PoolingDataSource dataSource;

    public TransactionManagerSetup() throws NamingException {
        dataSource = new PoolingDataSource();
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(5);
        dataSource.setUniqueName("myDS");
        dataSource.setClassName(JdbcDataSource.class.getName());
        dataSource.getDriverProperties().put("URL", "jdbc:h2:mem:test");
        dataSource.getDriverProperties().put("user", "sa");
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
        dataSource.close();
    }
}
