package ru.migplus.site.converters;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConvertConfig {
    @EventListener(ApplicationReadyEvent.class)
    public void config() {
        DefaultConversionService conversionService = (DefaultConversionService) DefaultConversionService.getSharedInstance();
        conversionService.addConverter(new TimestampToZonedDateTimeConverter());
    }
}
