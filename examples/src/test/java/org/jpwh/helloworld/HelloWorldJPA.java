package org.jpwh.helloworld;

import org.jpwh.env.TransactionManagerTest;
import org.jpwh.model.helloworld.Message;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class HelloWorldJPA extends TransactionManagerTest {

    @Test
    public void storeLoadMessage() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HelloWorldPU");

        try {
            {
                UserTransaction transaction = TM.getUserTransaction();
                transaction.begin();

                EntityManager entityManager = emf.createEntityManager();

                Message message = new Message();
                message.setText("Hello world!");

                entityManager.persist(message);
                transaction.commit();
                entityManager.close();
            }
            {
                UserTransaction transaction = TM.getUserTransaction();
                transaction.begin();
                EntityManager entityManager = emf.createEntityManager();

                List<Message> messages = entityManager.createQuery("select m from Message m").getResultList();

                assertEquals(messages.size(), 1);
                assertEquals(messages.get(0).getText(), "Hello world!");

                messages.get(0).setText("Take me to your leader!");

                transaction.commit();
                entityManager.close();
            }
        } finally {
            TM.rollback();
            emf.close();
        }
    }
}
