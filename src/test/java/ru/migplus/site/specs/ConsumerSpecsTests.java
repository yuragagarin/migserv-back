package ru.migplus.site.specs;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.migplus.site.dao.main.filter.ConsumerSpecs;
import ru.migplus.site.dao.main.repo.ConsumerRepository;
import ru.migplus.site.domain.Consumer;

import java.util.List;

@SpringBootTest
public class ConsumerSpecsTests {

    @Autowired
    ConsumerRepository consumerRepo;

    @Test
    void equalId() {
        List<Consumer> consumers = consumerRepo.findAll(ConsumerSpecs.equalId(Long.valueOf(1)));
        assertNotNull(consumers);
        assertEquals(1, consumers.size());
    }

    @Test
    void equalName() {
        List<Consumer> consumers = consumerRepo.findAll(ConsumerSpecs.equalName("Калужская 123"));
        assertNotNull(consumers);
        assertEquals(1, consumers.size());
    }

    @Test
    void equalStatus() {
        List<Consumer> consumers = consumerRepo.findAll(ConsumerSpecs.equalName("ACTIVE"));
        assertNotNull(consumers);
    }

    @Test
    void withAllSpecs() {
        List<Consumer> consumers = consumerRepo.findAll(ConsumerSpecs.equalName("ACTIVE"));
        assertNotNull(consumers);
    }
}
