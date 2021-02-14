package helpers;

import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;
import util.HibernateUtil;

public class ConcurrencyCacheHelper {

    public void readDataManyTimes() {
        for (int i = 0 ; i < 200; i ++) {
            new Thread(()->{
                for(int j = 0; j < 20; j++) {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction transaction = session.getTransaction();
                    transaction.begin();
                    Item item = session.find(Item.class, 1l);
                    item.setName("name_"+j);
                    transaction.commit();
                    session.close();
                }
            }).start();
        }

    }


    public void readDataManyTimes_2() {
        for (int i = 0 ; i < 200; i ++) {
            new Thread(()->{
                for(int j = 0; j < 20; j++) {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction transaction = session.getTransaction();
                    transaction.begin();
                    Item item = session.get(Item.class, 1l);
                    item.setName("name_"+j+Thread.currentThread().getName());
                    transaction.commit();
                    session.close();
                }
            }).start();
        }

    }


}
