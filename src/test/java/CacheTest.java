import entity.Item;
import helpers.ConcurrencyCacheHelper;
import helpers.MainHelper;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.Arrays;


public class CacheTest {
    @BeforeAll
    public static void setUp(){
        new MainHelper().generate_item();
    }

    @Test
    public void firstLevelCacheTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        session.close();
    }

    @Test
    public void concurrencyCacheTest(){
      new ConcurrencyCacheHelper().readDataManyTimes();
      new ConcurrencyCacheHelper().readDataManyTimes_2();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Statistics statistics = HibernateUtil.getSessionFactory().getStatistics();

        System.out.println(   statistics.getConnectCount());
        System.out.println(   statistics.getSecondLevelCacheHitCount());
        System.out.println(   statistics.getOptimisticFailureCount());
        System.out.println(   statistics.getSuccessfulTransactionCount());
        System.out.println(   statistics.getTransactionCount());

        System.out.println( "lol");

        System.out.println(   statistics.getCacheRegionStatistics("entity.Item"));

    }


    @Test
    public void secondLevelCacheTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        transaction.commit();
        session.close();

        Session session1 = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();
        System.out.println(session1.get(Item.class, 1L));
        System.out.println(session1.get(Item.class, 1L));
        System.out.println(session1.get(Item.class, 1L));
        transaction1.commit();
        session1.close();

        Session session2 = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();
        System.out.println(session2.get(Item.class, 1L));
        System.out.println(session2.get(Item.class, 1L));
        System.out.println(session2.get(Item.class, 1L));
        transaction2.commit();
        session2.close();

        Statistics statistics = HibernateUtil.getSessionFactory().getStatistics();

        System.out.println(   statistics.getConnectCount());
        System.out.println(   statistics.getCacheRegionStatistics("entity.Item"));

        Arrays.stream(statistics.getSecondLevelCacheRegionNames()).forEach(System.out::println);

        long newMissCount = HibernateUtil.getSessionFactory().getStatistics().getQueryCacheMissCount();
        long newHitCount = HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount();
        System.out.println(HibernateUtil.getSessionFactory().getStatistics());

        System.out.println(newMissCount);
        System.out.println(newHitCount);
    }

}
