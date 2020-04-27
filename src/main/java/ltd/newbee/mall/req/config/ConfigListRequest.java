package ltd.newbee.mall.req.config;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 21:41
 */
@Data
public class ConfigListRequest extends Result {

    private static final long serialVersionUID = 5788989970108505635L;

    @NotBlank(message = "页码")
    private String page;

    @NotBlank(message = "每页条数不能为空")
    private String limit;
}
