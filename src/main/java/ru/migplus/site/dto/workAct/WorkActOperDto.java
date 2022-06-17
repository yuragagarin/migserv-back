package ru.migplus.site.dto.workAct;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkActOperDto {
    private Long id;
    private String name;

    @Builder
    public WorkActOperDto(String name) {
        this.name = name;
    }
}

