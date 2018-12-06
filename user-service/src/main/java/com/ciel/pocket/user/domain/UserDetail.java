package com.ciel.pocket.user.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDetail {
    private Long accountId;

    private String userName;
}
