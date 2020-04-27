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
public class AdminUserLockRequest extends Result {

    private static final long serialVersionUID = -7856506003958229285L;

    /**
     * 用户ID，123,456,789
     */
    @NotBlank(message = "id不能为空")
    private String ids;
}
