package ru.migplus.site.dao.main.repo;

import ru.migplus.site.dto.consumer.ParamPerDayDto;
import ru.migplus.site.dto.consumer.ParamPerHourDto;
import ru.migplus.site.dto.rdParam.RdParamDto;

import java.util.List;

public interface RdParamJdbcRepository {
    List<RdParamDto> getRdParams(long consumerId);

    List<ParamPerDayDto> getParamsPerDay(ParamPerDayDto filt);

    List<ParamPerHourDto> getParamsPerHour(ParamPerHourDto filt);

    List<ParamPerHourDto> getParamsPerLastHour(ParamPerHourDto filt);

    List<ParamPerHourDto> getParamsPerLastHourAll(ParamPerHourDto filt);
}
