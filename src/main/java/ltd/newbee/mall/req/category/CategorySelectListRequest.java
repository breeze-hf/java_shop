package ltd.newbee.mall.req.category;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 21:22
 */
@Data
public class CategorySelectListRequest extends Result {
    private static final long serialVersionUID = -5135977985126210633L;

    @NotNull(message = "id不能为空")
    private Long categoryId;
}
