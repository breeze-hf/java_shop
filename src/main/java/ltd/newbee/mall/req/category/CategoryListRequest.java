package ltd.newbee.mall.req.category;

import lombok.Data;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 21:04
 */
@Data
public class CategoryListRequest extends Result<PageQueryUtil> {
    private static final long serialVersionUID = 29181392418722347L;

    @NotBlank(message = "页码")
    private String page;

    @NotBlank(message = "每页条数不能为空")
    private String limit;

    /**
     * 分类级别(1-一级分类 2-二级分类 3-三级分类)
     */
    @NotBlank(message = "分类级别不能为空")
    private String categoryLevel;

    @NotBlank(message = "父类ID不能为空")
    private String parentId;

    private String categoryName;
}
