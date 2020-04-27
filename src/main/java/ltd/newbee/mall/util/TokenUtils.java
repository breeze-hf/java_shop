package ltd.newbee.mall.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 19:48
 */
@Slf4j
public class TokenUtils {

    public static Integer parseToken(String token) {
        Integer loginUserId;
        try {
            byte[] loginId = Base64Utils.decodeFromString(token);
            String loginUserIds = new String(loginId, "utf-8");
            loginUserId = Integer.valueOf(loginUserIds);
        } catch (UnsupportedEncodingException e) {
            log.error("解析token失败", e);
            loginUserId = null;
        }
        return loginUserId;
    }
}
