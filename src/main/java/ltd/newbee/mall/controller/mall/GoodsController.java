package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.controller.mall.request.NewBeeMallGoodsDetailRequest;
import ltd.newbee.mall.controller.mall.request.SearchPageRequest;
import ltd.newbee.mall.controller.vo.NewBeeMallGoodsDetailVO;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GoodsController {

    @Resource private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource private NewBeeMallCategoryService newBeeMallCategoryService;

    @PostMapping("/search")
    public Result searchPage(@RequestBody SearchPageRequest request) {
        Map<String, Object> params = new HashMap<>();
        String page = "1";
        if (!StringUtils.isEmpty(request.getPage())) {
            page = request.getPage();
        }

        String orderBy = "";
        if (!StringUtils.isEmpty(request.getOrderBy())) {
            orderBy = request.getOrderBy();
        }

        String keyword = "";
        // 对keyword做过滤 去掉空格
        if (!StringUtils.isEmpty(request.getKeyword())) {
            keyword = "%"+request.getKeyword()+ "%";
        }

        String goodsCategoryId = "";
        // 对keyword做过滤 去掉空格
        if (!StringUtils.isEmpty(request.getGoodsCategoryId())) {
            goodsCategoryId = request.getGoodsCategoryId();
        }
        params.put("orderBy", orderBy);
        params.put("keyword", keyword);
        params.put("page", page);
        params.put("goodsCategoryId", goodsCategoryId);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        // 封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult pageResult = newBeeMallGoodsService.searchNewBeeMallGoods(pageUtil);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/goods/detail")
    public Result detailPage(@RequestBody NewBeeMallGoodsDetailRequest request) {
        if (StringUtils.isEmpty(request.getGoodsId())) {
            throw new NewBeeMallException("goodsId 为空异常");
        }
        Long goodsId = Long.valueOf(request.getGoodsId());
        if (goodsId < 1) {
            throw new NewBeeMallException("goodsId < 1异常");
        }
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        if (goods == null) {
            throw new NewBeeMallException("goods == null异常");
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            throw new NewBeeMallException("商品不是上架状态");
        }
        NewBeeMallGoodsDetailVO goodsDetailVO = new NewBeeMallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }
}
