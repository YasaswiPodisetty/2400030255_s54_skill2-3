package com.inventory.main;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class App {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // Insert Data
        session.save(new Product(1, "Laptop", 50000, 10, "Electronics"));
        session.save(new Product(2, "Phone", 20000, 5, "Electronics"));
        session.save(new Product(3, "Shoes", 3000, 20, "Fashion"));
        session.save(new Product(4, "Watch", 2500, 15, "Fashion"));
        session.save(new Product(5, "Book", 500, 50, "Education"));
        session.save(new Product(6, "Bag", 1500, 8, "Accessories"));

        tx.commit();

        // Sorting
        Query<Product> q1 = session.createQuery("FROM Product ORDER BY price ASC", Product.class);
        List<Product> list1 = q1.list();

        Query<Product> q2 = session.createQuery("FROM Product ORDER BY price DESC", Product.class);
        List<Product> list2 = q2.list();

        // Quantity sorting
        Query<Product> q3 = session.createQuery("FROM Product ORDER BY quantity DESC", Product.class);
        List<Product> list3 = q3.list();

        // Pagination
        Query<Product> q4 = session.createQuery("FROM Product", Product.class);
        q4.setFirstResult(0);
        q4.setMaxResults(3);
        List<Product> page1 = q4.list();

        q4.setFirstResult(3);
        q4.setMaxResults(3);
        List<Product> page2 = q4.list();

        // Aggregates
        Long total = (Long) session.createQuery("SELECT COUNT(*) FROM Product").uniqueResult();

        Long count = (Long) session.createQuery("SELECT COUNT(*) FROM Product WHERE quantity > 0").uniqueResult();

        List<Object[]> group = session.createQuery(
                "SELECT description, COUNT(*) FROM Product GROUP BY description"
        ).list();

        Object[] minMax = (Object[]) session.createQuery(
                "SELECT MIN(price), MAX(price) FROM Product"
        ).uniqueResult();

        // Filtering
        List<Product> filtered = session.createQuery(
                "FROM Product WHERE price BETWEEN 1000 AND 30000", Product.class
        ).list();

        // LIKE
        List<Product> starts = session.createQuery(
                "FROM Product WHERE name LIKE 'L%'", Product.class
        ).list();

        List<Product> ends = session.createQuery(
                "FROM Product WHERE name LIKE '%e'", Product.class
        ).list();

        List<Product> contains = session.createQuery(
                "FROM Product WHERE name LIKE '%oo%'", Product.class
        ).list();

        List<Product> length = session.createQuery(
                "FROM Product WHERE LENGTH(name) = 5", Product.class
        ).list();

        session.close();

        System.out.println("HQL Operations Executed Successfully!");
    }
}