package ltd.newbee.mall.req.mall;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/19 15:54
 */
@Data
public class PersonLoginRequest extends Result {
    private static final long serialVersionUID = -1561590170979584257L;

    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @NotBlank(message = "登录密码不能为空")
    private String password;
}
