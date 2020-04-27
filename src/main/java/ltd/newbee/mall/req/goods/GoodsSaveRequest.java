package ltd.newbee.mall.req.goods;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/18 20:59
 */
@Data
public class GoodsSaveRequest extends Result {

    private static final long serialVersionUID = -1059267566876000648L;

    @NotBlank(message = "商品名称不能为空")
    private String goodsName;

    @NotBlank(message = "商品简介不能为空")
    private String goodsIntro;

    @NotNull(message = "商品分类ID不能为空")
    private Long goodsCategoryId;

    @NotBlank(message = "商品图片不能为空")
    private String goodsCoverImg;

    @NotBlank(message = "商品轮播图不能为空")
    private String goodsCarousel;

    @NotNull(message = "商品价格不能为空")
    private Integer originalPrice;

    @NotNull(message = "商品售价不能为空")
    private Integer sellingPrice;

    @NotNull(message = "商品库存不能为空")
    private Integer stockNum;

    @NotNull(message = "商品标签不能为空")
    private String tag;

    @NotNull(message = "商品上架状态不能为空")
    private Byte goodsSellStatus;
    @NotBlank(message = "商品详情介绍不能为空")
    private String goodsDetailContent;


}
