package com.example._4_man_fashion.utils.generators;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.exception.spi.Configurable;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

public class GeneratorProductId implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
//        String query = "select max(o.id) from Product o";
//
//        Stream<String> ids = session.createQuery(query).stream();
//
//        long max = ids.map(o -> o.replace(prefix + "-", "")).mapToLong(Long::parseLong)
//                .max()
//                .orElse(0L);
//
        return "";

    }
}
