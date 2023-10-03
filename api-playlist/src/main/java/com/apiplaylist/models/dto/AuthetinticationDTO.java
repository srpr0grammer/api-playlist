package com.apiplaylist.models.dto;

import jakarta.validation.constraints.NotNull;

public record AuthetinticationDTO (@NotNull String userName, @NotNull String password) {

}
