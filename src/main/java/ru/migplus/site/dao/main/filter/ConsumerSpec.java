package ru.migplus.site.dao.main.filter;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.migplus.site.domain.Consumer;


@Conjunction({
        @Or({@Spec(path = "serviceman.id", params = "servicemanId", spec = Equal.class),
                @Spec(path = "servicemanGas.id", params = "servicemanId", spec = Equal.class)}),
        @Or(@Spec(path = "status", params = "status", spec = Equal.class))
})
public interface ConsumerSpec extends Specification<Consumer> {
}
