package ru.migplus.site.dao.main.filter;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.NotIn;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.Permission;

@Spec(path = "type", params = "type", spec = Equal.class)
@Join(path = "roles", alias = "r")
@And({@Spec(path = "r.id", params = "roleId", spec = Equal.class),
        @Spec(path = "id", params = "permissionsNotIn", paramSeparator = ',', spec = NotIn.class)})
public interface PermissionSpec extends Specification<Permission> {
}
