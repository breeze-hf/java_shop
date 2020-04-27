package ltd.newbee.mall.req.carousel;

import lombok.Data;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 19:48
 */
@Data
public class CarouselListRequest extends Result<PageQueryUtil> {

    private static final long serialVersionUID = -3394384811939126914L;

    /**
     * 页码
     */
    @NotBlank(message = "页码不能为空")
    private String page;

    /**
     * 每页条数
     */
    @NotBlank(message = "每页条数不能为空")
    private String limit;

    private String carouselId;

}
