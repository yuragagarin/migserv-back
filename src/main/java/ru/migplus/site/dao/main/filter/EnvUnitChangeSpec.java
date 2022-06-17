package ru.migplus.site.dao.main.filter;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.EnvUnit;

@Join(path = "user", alias = "u")
//@Join(path = "env_unit_opers", alias = "euo")
@Spec(path = "u.id", params = "userId", spec = Equal.class)
/*@And(
@Spec(path = "euo.id", params = "env_unit_id", spec = Equal.class)
)*/
public interface EnvUnitChangeSpec extends Specification<EnvUnit> {
}
