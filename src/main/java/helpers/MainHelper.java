package helpers;

import entity.Category;
import entity.Item;
import entity.Purchase;
import entity.Storage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MainHelper {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public void clearBeforeCommit() {
        Session session = sessionFactory.openSession();
        Transaction tx =  session.beginTransaction();
        Item item = new Item();
        item.setName("lol");
        session.persist(item);
        session.detach(item);
        tx.commit();
        session.close();
    }


    @Transactional
    public void generate_item() {
        Session session = sessionFactory.openSession();
        Transaction tx =  session.beginTransaction();

        for(int i = 0; i < 100; i++) {
            Category category = new Category();
            category.setName("cars_"+i);
            List<Item> items = new ArrayList<>();
            for(int j = 0; j< 100; j++) {
                Item truck = new Item();

                truck.setName("truck_"+j);
                truck.setCategory(category);
                items.add(truck);



                Storage storage = new Storage();
                storage.setItem(truck);
                session.persist(storage);
                storage.setArea( BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1d, 100000d))
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue());

                truck.setPrice(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1d, 100000d))
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue());
            }

            category.setItems(items);

        }
        session.flush();
        tx.commit();
        session.close();
    }

    @Transactional
    public void changeItemInStorage() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Storage from_storage =(Storage) session.createQuery("from Storage").getSingleResult();
        from_storage.getItem().setName("newName");
        transaction.commit();
        session.close();
    }



    @Transactional
    public void checkLazyInit() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Storage storage = (Storage) session.createQuery("from Storage ").getSingleResult();
        System.out.println("**********************************************************");
        System.out.println(storage.getId());
        System.out.println(storage.getItem().getName());
        transaction.commit();
    }

    @Transactional
    public void orphanRemovalTest() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Storage storage = (Storage) session.createQuery("from Storage ").getSingleResult();
//        storage.setItem(new Item());
        transaction.commit();
    }

    @Transactional
    public void purchaseHelper(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Item> itemList = new ArrayList<>();
        Item item = new Item();
        item.setName("audi");
        Category bikes = new Category();
        bikes.setName("bikes");
        item.setCategory(bikes);
        item.setPrice(123d);
        Item item1 = new Item();
        item1.setName("bmw");
        item1.setPrice(4322d);
        item1.setCategory(bikes);
        itemList.add(item1);
        itemList.add(item);

        Purchase purchase = new Purchase();
        //purchase.setItems(itemList);
        item1.setPurchase(purchase);
        item.setPurchase(purchase);
        session.saveOrUpdate(item);
        session.saveOrUpdate(item1);
        //session.merge(purchase);
        transaction.commit();
    }

}
