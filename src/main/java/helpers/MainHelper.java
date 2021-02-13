package helpers;

import entity.Category;
import entity.Item;
import entity.Purchase;
import entity.Storage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class MainHelper {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();



    @Transactional
    public void generate_item() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx =  session.beginTransaction();

        Category category = new Category();
        category.setName("cars");
        Item truck = new Item();

        truck.setName("volvo");
        truck.setCategory(category);

        Storage storage = new Storage();
        storage.setItem(truck);
        session.persist(storage);
        storage.setArea(12.3);
        truck.setPrice(33.4);

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
       // transaction.commit();
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
