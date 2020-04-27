package ltd.newbee.mall.req.admin;

import lombok.Getter;
import lombok.Setter;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 15:03
 */
@Setter
@Getter
public class AdminLoginRequest extends Result {

    private static final long serialVersionUID = -2800684882209800551L;

    @NotBlank(message = "登录名不能为空")
    private String userName;

    @NotBlank(message = "登录密码不能为空")
    private String password;
}
