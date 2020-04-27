package ltd.newbee.mall.req.goods;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 20:34
 */
@Data
public class GoodsEidtDetailRequest extends Result {
    private static final long serialVersionUID = -8481549441939305788L;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;
}
