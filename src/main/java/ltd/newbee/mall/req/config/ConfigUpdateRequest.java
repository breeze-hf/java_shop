package ltd.newbee.mall.req.config;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 21:58
 */
@Data
public class ConfigUpdateRequest extends Result {

    private static final long serialVersionUID = 7394843920410655724L;

    @NotNull(message = "广告ID不能为空")
    private Long configId;

    @NotBlank(message = "广告名不能为空")
    private String configName;

    @NotNull(message = "广告类型不能为空")
    private Byte configType;

    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @NotBlank(message = "跳转路径不能为空")
    private String redirectUrl;

    @NotNull(message = "广告排序不能为空")
    private Integer configRank;
}
