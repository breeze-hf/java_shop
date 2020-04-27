package ltd.newbee.mall.req.admin;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 19:45
 */
@Data
public class AdminProfileNameRequest extends Result {

    private static final long serialVersionUID = 6411301507328473237L;

    @NotBlank(message = "登录名不能为空")
    private String loginUserName;

    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @NotBlank(message = "token不能为空")
    private String token;
}
