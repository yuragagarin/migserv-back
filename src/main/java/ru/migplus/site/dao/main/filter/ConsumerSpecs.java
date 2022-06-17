package ru.migplus.site.dao.main.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.Consumer;
import ru.migplus.site.domain.Status;
import ru.migplus.site.domain.User;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;


public class ConsumerSpecs {

    public static Specification<Consumer> equalId(Long id) {

        return (root, query, cb) -> {

            if (id == 0) return cb.conjunction();

            return cb.equal(root.get("id"), id);

        };
    }

    public static Specification<Consumer> equalName(String name) {

        return (root, query, cb) -> {

            if (name == null) return cb.conjunction();

            return cb.equal(root.get("name"), name);

        };

    }

    public static Specification<Consumer> equalStatus(Status val) {

        return (root, query, cb) -> {

            if (val == null) return cb.conjunction();

            return cb.equal(root.get("status"), val);

        };
    }

    public static Specification<Consumer> equalServicemanOrServicemanGas(Long id) {

        return (root, query, cb) -> {

            if (id == 0) return cb.conjunction();

            Join<Consumer, User> serviceman = root.join("serviceman", JoinType.LEFT);
            Join<Consumer, User> servicemanGas = root.join("servicemanGas", JoinType.LEFT);
            Predicate forServiceman = cb.equal(serviceman.get("id"), id);
            Predicate forServicemanGas = cb.equal(servicemanGas.get("id"), id);
            Predicate common = cb.or(forServiceman, forServicemanGas);

            return common;

        };
    }

}
