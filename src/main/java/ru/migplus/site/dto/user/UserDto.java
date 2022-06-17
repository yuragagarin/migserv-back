package ru.migplus.site.dto.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.migplus.site.dto.consumer.Views;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @JsonView(Views.IdName.class)
    Long id;
    @JsonView(Views.IdName.class)
    String fio;
    String username;
    String password;
    Long userPostId;
    String phoneNumber;
    String email;
    Long consumerId;
    Long changedUserId;
    Long userId;
    Integer roleId;
    ZonedDateTime updated;
}
