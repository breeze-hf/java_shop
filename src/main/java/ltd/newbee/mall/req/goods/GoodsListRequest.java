package ltd.newbee.mall.req.goods;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 20:48
 */
@Data
public class GoodsListRequest implements Serializable{
    private static final long serialVersionUID = -56567800570103037L;

    @NotBlank(message = "当前页码不能为空")
    private String page;

    @NotBlank(message = "每页条数不能为空")
    private String limit;

    private String goodsId;
}
