package helpers;

import entity.Category;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class CategoryHelper {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public void categories_save_test() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Category books = new Category();
        books.setName("literature");

        Item war_and_peace = new Item();
        war_and_peace.setPrice(12.2);
        war_and_peace.setName("war_and_peace");

        Item magazine = new Item();
        magazine.setName("magazinus");
        magazine.setPrice(12.3);

        List<Item> items = new ArrayList<>();
        items.add(war_and_peace);
        items.add(magazine);

        books.setItems(items);

        session.saveOrUpdate(books);

        tx.commit();
        session.close();
    }

    @Transactional
    public void changeItemListInCategory() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Category category = session.find(Category.class, 3L);
        System.out.println("dirty: " + session.isDirty());
        category.getItems().forEach(item -> System.out.println(item.getName()));


        Item newItem_1 = new Item();
        newItem_1.setName("newItem_1");
        newItem_1.setPrice(444d);

        Item newItem_2 = new Item();
        newItem_2.setName("newItem_2");
        newItem_2.setPrice(443334d);

        List<Item>  newItems = new ArrayList<>();
        newItems.add(newItem_1);
        newItems.add(newItem_2);

        category.getItems().clear();
        category.getItems().addAll(newItems);
        tx.commit();
        session.close();

    }
}
