package ltd.newbee.mall.res.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import ltd.newbee.mall.util.Result;

import java.io.Serializable;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 15:04
 */
@Setter
@Getter
public class AdminLoginResponse extends Result {

    private static final long serialVersionUID = -2475884653714252385L;

    private String loginUser;

    private String loginUserId;

    private String token;
}
