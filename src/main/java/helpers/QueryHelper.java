package helpers;

import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.sql.Select;
import util.HibernateUtil;

import javax.persistence.criteria.*;
import java.util.List;

public class QueryHelper {
    public void criteria_1() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.get(Item.class, 1L);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);

        Root<Item> itemRoot = criteriaQuery.from(Item.class);

        Selection[] selections = {itemRoot.get("id"), itemRoot.get("name")};

        Predicate price = criteriaBuilder.lessThan(itemRoot.get("price"), 12);

        criteriaQuery.select(criteriaBuilder.construct(Item.class, selections));
        criteriaQuery.where(price);
        Query query = session.createQuery(criteriaQuery);
        List resultList = query.getResultList();
        System.out.println(resultList.size());

    }

    public void criteria_2() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Storage> query = criteriaBuilder.createQuery(Storage.class);
        Root<Storage> storageRoot = query.from(Storage.class);
        storageRoot.join(Storage_.ITEM, JoinType.INNER);
        Predicate predicate = criteriaBuilder.between(storageRoot.get(Item_.ID), 12, 230);
        query.where(predicate);
        CriteriaQuery<Storage> select = query.select(storageRoot);
        Query query1 = session.createQuery(select);
        List<Storage> resultList = query1.getResultList();
        System.out.println(resultList.size());

    }

    public void criteria_3() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Category> query = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = query.from(Category.class);
        Join<Category, List<Item>> join = categoryRoot.join(Category_.ITEMS);

        Predicate predicate = criteriaBuilder.lessThan(join.get(Item_.PRICE), 100);
        CriteriaQuery<Category> criteriaQuery = query.where(predicate);
        criteriaQuery.select(categoryRoot);

        Query<Category> longQuery = session.createQuery(criteriaQuery);
        List<Category> longs = longQuery.getResultList();
        int size = longs.size();
    }

    public void criteria_4(String carname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> itemRoot = cq.from(Item.class);
        Join<Item, Category> join = itemRoot.join(Item_.CATEGORY);

        Selection[] selections = {itemRoot.get(Item_.ID), itemRoot.get(Item_.PRICE)};
        ParameterExpression<String> nameParam = cb.parameter(String.class, Category_.NAME);
        cq.select(cb.construct(Item.class, selections)).where(cb.like(join.get(Category_.NAME), nameParam));

        Query query = session.createQuery(cq);
        query.setParameter(Category_.NAME, carname);
        List<Item> items = query.getResultList();
        System.out.println("lol: " + items.size());
    }

    public void hqlTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Item i join Category c on i.category=c.id where c.name like : param");
        query.setParameter("param", "cars_0");
        List<Item> items = query.getResultList();
        System.out.println("lol: " + items.size());
    }
}
