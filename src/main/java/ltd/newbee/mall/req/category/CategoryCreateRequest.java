package ltd.newbee.mall.req.category;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 21:30
 */
@Data
public class CategoryCreateRequest extends Result {

    private static final long serialVersionUID = 1145321022998150518L;

    @NotNull(message = "类目等级不能为空")
    private Byte categoryLevel;

    @NotNull(message = "父类ID不能为空")
    private Long parentId;

    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    @NotNull(message = "分类排序不能为空")
    private Integer categoryRank;

}
