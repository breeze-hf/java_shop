package ltd.newbee.mall.req.carousel;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 20:20
 */
@Data
public class CarouselDeletedRequest extends Result {

    private static final long serialVersionUID = -8911663889570089824L;

    /**id，例：123,456,789*/
    @NotBlank(message = "id不能为空")
    private String ids;

}
