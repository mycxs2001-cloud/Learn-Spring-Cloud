package org.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-05 18:55
 */
@Data
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

}
