package ru.migplus.site.dao.main.filter;

import net.kaczmarzyk.spring.data.jpa.domain.NotIn;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.Permission;


@Spec(path = "name", params = "nameNotIn", paramSeparator = ',', spec = NotIn.class)
public interface RoleSpec extends Specification<Permission> {
}
