package com.ciel.pocket.user.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {

    Long accountId;

    String username;

    String nickname;

    Date lastSeen;

    List<Theme> themes;
}
