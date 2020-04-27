package ltd.newbee.mall.req.mall;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/19 16:43
 */
@Data
public class PersonRegisterRequest extends Result {
    private static final long serialVersionUID = 978031200556023174L;

    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    private String password;
}
