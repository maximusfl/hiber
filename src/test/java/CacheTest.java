import entity.Item;
import helpers.CategoryHelper;
import helpers.MainHelper;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;


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
    public void secondLevelCacheTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        System.out.println(session.get(Item.class, 1L));
        session.close();

        Session session1 = HibernateUtil.getSessionFactory().openSession();
        System.out.println(session1.get(Item.class, 1L));
        System.out.println(session1.get(Item.class, 1L));
        System.out.println(session1.get(Item.class, 1L));
        session1.close();

        Session session2 = HibernateUtil.getSessionFactory().openSession();
        System.out.println(session2.get(Item.class, 1L));
        System.out.println(session2.get(Item.class, 1L));
        System.out.println(session2.get(Item.class, 1L));
        session2.close();

        long newMissCount = HibernateUtil.getSessionFactory().getStatistics().getQueryCacheMissCount();
        long newHitCount = HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount();

        System.out.println(newMissCount);
        System.out.println(newHitCount);
    }

}
