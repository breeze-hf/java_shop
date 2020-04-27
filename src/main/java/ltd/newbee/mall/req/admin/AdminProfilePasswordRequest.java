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
public class AdminProfilePasswordRequest extends Result {

    private static final long serialVersionUID = 8402978351404113287L;

    @NotBlank(message = "旧密码不能为空")
    private String originalPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    @NotBlank(message = "token不能为空")
    private String token;
}
