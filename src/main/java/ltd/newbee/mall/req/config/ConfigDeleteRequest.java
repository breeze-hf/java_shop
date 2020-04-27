package ltd.newbee.mall.req.config;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 22:45
 */
@Data
public class ConfigDeleteRequest extends Result {
    private static final long serialVersionUID = 7463112712560703965L;

    /**
     * id，例：123,456,789
     */
    @NotBlank(message = "id不能为空")
    private String ids;
}
