package ltd.newbee.mall.req.order;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 23:20
 */
@Data
public class OrderCloseRequest extends Result {

    private static final long serialVersionUID = -8952180001833760699L;

    /**订单Id*，123,456,48*/
    @NotBlank(message = "订单ID不能为空")
    private String ids;
}
