package ltd.newbee.mall.req.mall;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/20 21:00
 */
@Data
public class AdminCustomerQueryRequest extends Result {
    private static final long serialVersionUID = -6200081476941940132L;

    @NotNull(message = "用户id不能为空")
    private Long id;
}
