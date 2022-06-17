package ru.migplus.site.dao.main.filter;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.WorkActPos;


@Spec(path = "workAct.id", params = "workActId", spec = Equal.class)
public interface WorkActPosSpec extends Specification<WorkActPos> {
}
