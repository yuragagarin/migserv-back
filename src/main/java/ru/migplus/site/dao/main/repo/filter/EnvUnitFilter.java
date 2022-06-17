package ru.migplus.site.dao.main.repo.filter;

import ru.migplus.site.domain.EnvUnit;
import ru.migplus.site.dto.envUnit.EnvUnitDto;

import java.util.List;

public interface EnvUnitFilter {

    List<EnvUnit> getFilter(EnvUnitDto filter);

}
