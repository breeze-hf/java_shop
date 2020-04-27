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
public class AdminCustomerListRequest extends Result<PageQueryUtil> {
    private static final long serialVersionUID = 8321238550026924413L;

    @NotBlank(message = "页码不能为空")
    private String page;

    @NotBlank(message = "每页条数不能为空")
    private String limit;

    private String id;

    private String loginName;
}
