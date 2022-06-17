package ru.migplus.site.dao.main.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.WorkAct;
import ru.migplus.site.domain.WorkActOper;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class WorkActSpecs {
    public static Specification<WorkAct> getWorkActByNumSpec(String num) {
        return (root, query, criteriaBuilder) -> {
            if (num == null) return criteriaBuilder.conjunction();
            Predicate equalPredicate = criteriaBuilder.equal(root.get("num"), num);
            return equalPredicate;
        };
    }

    public static Specification<WorkAct> getWorkActCreated() {
        return (r, q, cb) -> {
            Subquery<WorkActOper> subquery = q.subquery(WorkActOper.class);
            Root<WorkActOper> subqueryRoot = subquery.from(WorkActOper.class);
            subquery.select(subqueryRoot);

            return cb.exists(subquery);
            /*Join<WorkAct, WorkActOper> opers = r.join("workActOpers");
            Predicate eqPredicate = cb.equal(opers.get("name"),"Создание");
            return eqPredicate;*/
        };
    }
}
