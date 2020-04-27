package ltd.newbee.mall.req.config;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 22:16
 */
@Data
public class ConfigDetailRequest extends Result {
    private static final long serialVersionUID = -8487795071299217236L;

    @NotNull(message = "id不能为空")
    private Long id;
}
