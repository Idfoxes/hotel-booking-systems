package com.project.HostelBooking.web.dto.user;

import com.project.HostelBooking.model.user.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Имя пользователя не должно быть пустым.")
    private String username;

    @NotBlank(message = "Пароль пользователя не должен быть пустым.")
    private String password;

    @NotBlank(message = "Поле с почтой пользователя не должно быть пустым.")
    private String email;

    @NotNull(message = "Укажите роль пользователя.")
    private RoleType roleType;
}
