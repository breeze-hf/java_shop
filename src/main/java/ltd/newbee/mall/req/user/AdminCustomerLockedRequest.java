package ltd.newbee.mall.req.user;

import lombok.Data;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 22:36
 */
@Data
public class AdminCustomerLockedRequest extends Result {

    private static final long serialVersionUID = 2866896754960531094L;

    @NotBlank(message = "id不能为空")
    private String ids;

    @NotNull(message = "锁定状态不能为空")
    private int lockStatus;
}
