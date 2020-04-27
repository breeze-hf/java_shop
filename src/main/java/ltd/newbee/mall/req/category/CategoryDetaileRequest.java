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
public class CategoryDetaileRequest extends Result {

    private static final long serialVersionUID = 3037364950922758972L;

    @NotNull(message = "类目ID不能为空")
    private Long id;


}
