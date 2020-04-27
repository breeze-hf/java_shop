package ltd.newbee.mall.req.order;

import lombok.Data;
import ltd.newbee.mall.util.Result;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 23:04
 */
@Data
public class OrderUpdateRequest extends Result {

    private static final long serialVersionUID = 1779716039280340950L;

    @NotBlank(message = "配货地址不能为空")
    private String userAddress;

    @NotNull(message = "订单ID不能为空")
    private Long id;
}
