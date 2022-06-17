package ru.migplus.site.dao.main.filter;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Null;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.EnvUnit;


//@Spec(path = "envUnitType.name", params = "envUnitTypeName", spec = Equal.class)
@Join(path = "envUnitOpers", alias = "euo")
@Join(path = "euo.name", alias = "euon")
@And({
        @Spec(path = "euo.dateFn", params = "dateFn", spec = Null.class),
        @Spec(path = "euon.name", params = "operName", spec = Equal.class)
})
public interface EnvUnitSpec extends Specification<EnvUnit> {
}

