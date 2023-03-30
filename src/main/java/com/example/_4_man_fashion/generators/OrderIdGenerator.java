package com.example._4_man_fashion.generators;

import com.example._4_man_fashion.repositories.OrderRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class OrderIdGenerator implements IdentifierGenerator {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        int orderIdMax = this.orderRepository.getMaxOrderId();
        System.out.println(orderIdMax);
        return "HD0"+orderIdMax+1;
    }
}
