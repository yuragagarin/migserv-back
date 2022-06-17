package ru.migplus.site.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class DateItem {
    private Date date;

    /**
     * @return the date
     */
    @Id
    @Column(name = "DATE_VALUE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
