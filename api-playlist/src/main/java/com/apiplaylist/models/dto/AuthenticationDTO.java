package com.apiplaylist.models.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(@NotNull String userName, @NotNull String password) {

}
