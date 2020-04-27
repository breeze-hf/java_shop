package ltd.newbee.mall.req.goods;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 20:59
 */
@Data
public class GoodsUpdateStatusRequest extends Result {

    private static final long serialVersionUID = -8411693158167497223L;

    @NotBlank(message = "商品ID不能为空")
    private String ids;

    @NotNull(message = "上架状态不能为空")
    private Integer sellStatus;
}
