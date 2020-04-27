package ltd.newbee.mall.req.goods;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 21:26
 */
@Data
public class GoodsDeleteRequest extends Result {
    private static final long serialVersionUID = 324886312464216520L;

    @NotBlank(message = "商品ID不能为空")
    private String ids;
}
