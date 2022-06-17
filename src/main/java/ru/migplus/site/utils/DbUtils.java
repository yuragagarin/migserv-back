package ru.migplus.site.utils;

import ru.migplus.site.domain.DateItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;

public class DbUtils {
    @PersistenceContext
    EntityManager em;

    public Date getServerDate() {
        Query query = (Query) em.createNativeQuery(
                "SELECT CURRENT_TIMESTAMP", DateItem.class);
        DateItem dateItem = (DateItem) query.getSingleResult();
        return dateItem.getDate();
    }
}
