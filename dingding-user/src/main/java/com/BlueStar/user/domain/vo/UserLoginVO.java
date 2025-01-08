package com.BlueStar.user.domain.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginVO {
    private Long userId;
    private String openId;
    private String token;
}
