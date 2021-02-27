package helpers;

import entity.Item;
import entity.Storage;
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
        CriteriaQuery<Item> criteriaQuery =  criteriaBuilder.createQuery(Item.class);

        Root<Item> itemRoot = criteriaQuery.from(Item.class);

        Selection [] selections = {itemRoot.get("id"), itemRoot.get("name")};

        Predicate price = criteriaBuilder.lessThan(itemRoot.get("price"), 12);

        criteriaQuery.select(criteriaBuilder.construct(Item.class, selections));
        criteriaQuery.where(price);
        Query query = session.createQuery(criteriaQuery);
        List resultList = query.getResultList();
        System.out.println(resultList.size());

    }
}
