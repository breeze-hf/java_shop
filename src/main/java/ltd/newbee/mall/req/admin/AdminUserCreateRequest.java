package ltd.newbee.mall.req.admin;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 23:01
 */
@Data
public class AdminUserCreateRequest extends Result {

    private static final long serialVersionUID = 5459085758723402584L;

    @NotBlank(message = "登录名不能为空")
    private String loginUserName;

    @NotBlank(message = "密码不能为空")
    private String loginPassword;

    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @NotBlank(message = "锁定状态不能为空")
    private String locked;
}
