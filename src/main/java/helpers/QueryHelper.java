package helpers;

import entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Category> query = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = query.from(Category.class);
        Join<Category,List<Item>> join =categoryRoot.join(Category_.ITEMS);

        Predicate predicate = criteriaBuilder.lessThan(join.get(Item_.PRICE), 100);
        CriteriaQuery<Category> criteriaQuery = query.where(predicate);
        criteriaQuery.select(categoryRoot);


        Query<Category> longQuery = session.createQuery(criteriaQuery);
        List<Category> longs = longQuery.getResultList();
        int size = longs.size();

    }
}
