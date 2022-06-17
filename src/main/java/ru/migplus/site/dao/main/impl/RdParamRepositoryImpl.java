package ru.migplus.site.dao.main.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.migplus.site.dao.main.repo.RdParamJdbcRepository;
import ru.migplus.site.dto.consumer.ParamPerDayDto;
import ru.migplus.site.dto.consumer.ParamPerHourDto;
import ru.migplus.site.dto.rdParam.RdParamDto;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Repository
public class RdParamRepositoryImpl implements RdParamJdbcRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public RdParamRepositoryImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<RdParamDto> getRdParams(long consumerId) {
        return jdbc.query(
                "select rp.id,rp.name from rd_params rp\n" +
                        "join rd_job_confs rjc on rjc.id = rp.rd_job_conf_id\n" +
                        "where rjc.consumer_id = :consumerId\n" +
                        "order by rp.name"
                , new MapSqlParameterSource()
                        .addValue("consumerId", consumerId)
                ,
                (rs, rowNum) -> {
                    return new RdParamDto(
                            rs.getLong("id")
                            , rs.getString("name")
                    );
                }
        );
    }

    @Override
    public List<ParamPerDayDto> getParamsPerDay(ParamPerDayDto filt) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone("\"Europe/Moscow\""));
        String consumer = "rd_consumer_" + filt.getConsumerId();
        return jdbc.query(
                "select rdcmax.val,rdcmax.hour, rdp.name from " + consumer + " rdc \n" +
                        "join (select max(id) as id, to_char(max(time) + '1 hour','HH24') as hour,avg(val) as val, param_id from " + consumer + "\n" +
                        "where param_id in(:paramIds) and time between :dateBegin ::timestamp::date + time '00:00:00' and :dateEnd ::timestamp::date + time '23:59:59'\n" +
                        "group by date_trunc('hour', time),param_id\n" +
                        "order by max(time)) rdcmax  on rdc.id = rdcmax.id\n" +
                        "join rd_params rdp on rdp.id = rdcmax.param_id\n" +
                        "join rd_job_confs rjc on rjc.id = rdp.rd_job_conf_id\n" +
                        "where time between :dateBegin ::timestamp::date + time '00:00:00' and :dateEnd ::timestamp::date + time '23:59:59' and rjc.consumer_id=:consumerId\n" +
                        "and rdp.id in(:paramIds) and rjc.type <> 'Безопасность'\n" +
                        "order by rdcmax.param_id,rdcmax.id"
                , new MapSqlParameterSource()
                        .addValue("dateBegin", new SimpleDateFormat("yyyy-MM-dd").format(filt.getTime()))
                        .addValue("dateEnd", new SimpleDateFormat("yyyy-MM-dd").format(filt.getTime()))
                        .addValue("paramIds", filt.getParamIds())
                        .addValue("consumerId", filt.getConsumerId())
                ,
                (rs, rowNum) -> {
                    return new ParamPerDayDto(
                            rs.getString("name")
                            , rs.getBigDecimal("val").setScale(2, RoundingMode.CEILING)
                            , rs.getString("hour")
                    );
                }
        );
    }

    @Override
    public List<ParamPerHourDto> getParamsPerHour(ParamPerHourDto params) {
        String consumer = "rd_consumer_" + params.getConsumerId();
        int lastHour = Integer.parseInt(params.getHour()) - 1;
        String lastHourS = String.format("%02d", lastHour);
        return jdbc.query(
                "select val,to_char(time,'MI') as minute, rdp.name, time from " + consumer + " rdc join\n" +
                        "(select MAX(id) id, param_id from " + consumer + " " +
                        "where param_id in(:paramIds) and time between :dateBegin ::timestamp::date + time '" + lastHourS + ":00:00' and :dateEnd ::timestamp::date + time '" + params.getHour() + ":00:00'\n" +
                        "group by date_trunc('minute', time),param_id) rdcmax on rdc.id = rdcmax.id\n" +
                        "join rd_params rdp on rdp.id = rdcmax.param_id\n" +
                        "join rd_job_confs rjc on rjc.id = rdp.rd_job_conf_id\n" +
                        "where time between :dateBegin ::timestamp::date + time '" + lastHourS + ":00:00' and :dateEnd ::timestamp::date + time '" + params.getHour() + ":00:00'\n and rjc.consumer_id=:consumerId\n" +
                        "and rdp.id in(:paramIds) and rjc.type <> 'Безопасность'\n" +
                        //"\twhere time between '2020-11-19' ::timestamp::date + time '15:00:00' and '2020-11-19' ::timestamp::date + time '16:03:00' and rdp.consumer_id=1" +
                        "order by rdcmax.param_id,rdc.time"
                , new MapSqlParameterSource()
                        .addValue("dateBegin", new SimpleDateFormat("yyyy-MM-dd").format(params.getTime()))
                        .addValue("dateEnd", new SimpleDateFormat("yyyy-MM-dd").format(params.getTime()))
                        .addValue("paramIds", params.getParamIds())
                        .addValue("consumerId", params.getConsumerId())
                ,
                (rs, rowNum) -> {
                    return new ParamPerHourDto(
                            rs.getString("name")
                            , rs.getBigDecimal("val")
                            , rs.getString("minute")
                    );
                }
        );
    }

    @Override
    public List<ParamPerHourDto> getParamsPerLastHour(ParamPerHourDto params) {
        String consumer = "rd_consumer_" + params.getConsumerId();

        return jdbc.query(
                "select val,to_char(time ,'HH24') ||':'||to_char(time,'MI') as minute, rdp.name, time from " + consumer + " rdc join\n" +
                        "(select MAX(id) id, param_id from " + consumer + " where param_id in(:paramIds) and time between now() - interval '63 minute' and now() group by date_trunc('minute', time),param_id) rdcmax on rdc.id = rdcmax.id\n" +
                        //"(select MAX(id) id, param_id from " + consumer + " where time between now() - interval '62 minute' and now() group by date_trunc('minute', time),param_id) rdcmax on rdc.id = rdcmax.id\n" +
                        "join rd_params rdp on rdp.id = rdcmax.param_id\n" +
                        "join rd_job_confs rjc on rjc.id = rdp.rd_job_conf_id\n" +
                        //"where time between '2020-12-04 00:20:00' and '2020-12-04 01:20:00' and rjc.consumer_id=:consumerId\n" +
                        "where time between now() - interval '63 minute' and now() and rjc.consumer_id=:consumerId\n" +
                        "and rdp.id in(:paramIds) and rjc.type <> 'Безопасность'\n" +
                        "order by rdcmax.param_id,rdc.time"
                , new MapSqlParameterSource()
                        .addValue("consumerId", params.getConsumerId())
                        .addValue("paramIds", params.getParamIds())
                ,
                (rs, rowNum) -> {
                    return new ParamPerHourDto(
                            rs.getString("name")
                            , rs.getBigDecimal("val")
                            , rs.getString("minute")
                    );
                }
        );
    }

    @Override

    public List<ParamPerHourDto> getParamsPerLastHourAll(ParamPerHourDto params) {
        String consumer = "rd_consumer_" + params.getConsumerId();

        return jdbc.query(
                /*"select val,to_char(time ,'HH24') ||':'||to_char(time,'MI') as minute, rdp.name, time from " + consumer + " rdc join\n" +
                        "(select MAX(id) id, param_id from " + consumer + " where time between now() - interval '63 minute' and now() group by date_trunc('minute', time),param_id) rdcmax on rdc.id = rdcmax.id\n" +
                        "join rd_params rdp on rdp.id = rdcmax.param_id\n" +
                        "join rd_job_confs rjc on rjc.id = rdp.rd_job_conf_id\n" +

                        "where time between now() - interval '63 minute' and now() and rjc.consumer_id=:consumerId\n" +
                        "and rjc.type <> 'Безопасность'\n" +
                        "order by rdcmax.param_id,rdc.time"*/
                "select val,to_char(time ,'HH24') ||':'||to_char(time,'MI') as minute, rdp.name, time from " + consumer + " rdc join\n" +
                        "(select MAX(id) id, param_id from " + consumer + " where time between current_timestamp - interval '63 minute' and current_timestamp group by date_trunc('minute', time),param_id) rdcmax on rdc.id = rdcmax.id\n" +
                        "join rd_params rdp on rdp.id = rdcmax.param_id\n" +
                        "join rd_job_confs rjc on rjc.id = rdp.rd_job_conf_id\n" +

                        "where time between current_timestamp - interval '63 minute' and current_timestamp and rjc.consumer_id=:consumerId\n" +
                        "and rjc.type <> 'Безопасность'\n" +
                        "order by rdcmax.param_id,rdc.time"
                , new MapSqlParameterSource()
                        .addValue("consumerId", params.getConsumerId())
                ,
                (rs, rowNum) -> {
                    return new ParamPerHourDto(
                            rs.getString("name")
                            , rs.getBigDecimal("val")
                            , rs.getString("minute")
                    );
                }
        );
    }
}
