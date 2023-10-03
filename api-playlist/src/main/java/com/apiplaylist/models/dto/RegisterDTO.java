package com.apiplaylist.models.dto;

import com.apiplaylist.models.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String userName, @NotNull String password, @NotNull UserRole role ) {
}
