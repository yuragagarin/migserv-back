package ru.migplus.site.payload.response;

import lombok.*;
import ru.migplus.site.exceptions.Error;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class SuccessResponse {
    private Boolean success = true;
    @NonNull
    private Object data;

   /* @Builder
    public SuccessResponse(T data){
        this.data = data;
    }*/
}
