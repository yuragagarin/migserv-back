package ru.migplus.site.dao.main.filter;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.User;


@Join(path = "roles", alias = "r")
//@Spec(path = "r.name", params = "role", spec = Equal.class)
@Spec(path = "r.name", params = "roleIn", paramSeparator = ',', spec = In.class)
@And(@Spec(path = "status", params = "status", spec = Equal.class))
public interface UserSpec extends Specification<User> {
}
