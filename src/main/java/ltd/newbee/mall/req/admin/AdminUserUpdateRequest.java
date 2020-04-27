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
public class AdminUserUpdateRequest extends Result {

    private static final long serialVersionUID = -7702530816122671234L;


    @NotBlank(message = "用户ID不能为空")
    private String adminUserId;

    @NotBlank(message = "登录名不能为空")
    private String loginUserName;

    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @NotBlank(message = "锁定状态不能为空")
    private String locked;
}
