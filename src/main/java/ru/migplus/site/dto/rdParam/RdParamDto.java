package ru.migplus.site.dto.rdParam;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RdParamDto {
    @JsonView(Views.IdName.class)
    long id;
    @JsonView(Views.IdName.class)
    String name;
    private long consumerId;

    @Builder
    public RdParamDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}

