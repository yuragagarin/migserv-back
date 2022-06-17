package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.Equip;
import ru.migplus.site.dto.equip.EquipViewDto;

import java.util.List;

@Repository
public interface EquipRepository extends JpaRepository<Equip, Long> {

    @Query(value = "select MAX(e.id) id, c.name consumerName,e.name,coalesce(e.number,'') as number,coalesce(e.year,'') as year, string_agg(eo.name, ', ') operName from equips e \n" +
            "join equip_opers eo on e.id = eo.equip_id\n" +
            "join consumers c on c.id = e.consumer_id\n" +
            "where e.status = 'ACTIVE'\n" +
            "group by c.name,e.number,e.year,e.name\n" +
            "order by c.name,e.name,e.number", nativeQuery = true)
    List<EquipViewDto> getAll();
}
