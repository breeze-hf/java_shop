package ltd.newbee.mall.req.order;

import lombok.Data;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 22:54
 */
@Data
public class OrderListRequest extends Result<PageQueryUtil> {
    private static final long serialVersionUID = 4365682481822546290L;

    @NotBlank(message = "页码")
    private String page;

    @NotBlank(message = "每页条数不能为空")
    private String limit;

    private String orderNo;
}
