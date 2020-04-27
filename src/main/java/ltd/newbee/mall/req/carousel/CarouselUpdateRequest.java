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
public class CarouselUpdateRequest extends Result {

    private static final long serialVersionUID = -3188254194434299790L;

    @NotBlank(message = "id不能为空")
    private String carouselId;

    @NotBlank(message = "轮播图不能为空")
    private String carouselUrl;

    @NotNull(message = "排序值不能为空")
    private Integer carouselRank;

    @NotBlank(message = "跳转路径不能为空")
    private String redirectUrl;
}
