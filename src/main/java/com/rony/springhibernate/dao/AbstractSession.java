package com.rony.springhibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.internal.ast.tree.SessionFactoryAwareNode;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractSession {
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}