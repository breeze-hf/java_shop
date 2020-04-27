package ltd.newbee.mall.req.order;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 23:10
 */
@Data
public class OrderDetailRequest extends Result {
    private static final long serialVersionUID = 3426573307344749301L;

    @NotNull(message = "订单ID不能为空")
    private Long id;
}
