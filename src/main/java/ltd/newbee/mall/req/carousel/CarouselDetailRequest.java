package ltd.newbee.mall.req.carousel;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 20:47
 */
@Data
public class CarouselDetailRequest extends Result {
    private static final long serialVersionUID = 1788879530021702546L;

    @NotNull(message = "id不能为空")
    private Integer id;
}
