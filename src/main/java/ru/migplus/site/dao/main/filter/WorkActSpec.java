package ru.migplus.site.dao.main.filter;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.WorkAct;


@Join(path = "workActOpers", alias = "wao")
//@Spec(path = "wao.name", params = "operName", spec = Equal.class)
@Join(path = "wao.user", alias = "u")
@And({@Spec(path = "wao.name", params = "operName", spec = Equal.class),
        @Spec(path = "u.id", params = "userId", spec = Equal.class)})
public interface WorkActSpec extends Specification<WorkAct> {
}
