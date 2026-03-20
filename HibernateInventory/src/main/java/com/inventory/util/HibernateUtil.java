package com.inventory.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.inventory.entity.Product;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}