package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.IndexConfigTypeEnum;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.controller.mall.response.IndexPageResult;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexCarouselVO;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexCategoryVO;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexConfigGoodsVO;
import ltd.newbee.mall.service.NewBeeMallCarouselService;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallIndexConfigService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@ResponseBody
public class IndexController {

    @Resource private NewBeeMallCarouselService newBeeMallCarouselService;

    @Resource private NewBeeMallIndexConfigService newBeeMallIndexConfigService;

    @Resource private NewBeeMallCategoryService newBeeMallCategoryService;

    @PostMapping("/index")
    public Result indexPage() {
        List<NewBeeMallIndexCategoryVO> categories =
                newBeeMallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            throw new NewBeeMallException("categories为空异常");
        }
        List<NewBeeMallIndexCarouselVO> carousels =
                newBeeMallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<NewBeeMallIndexConfigGoodsVO> hotGoodses =
                newBeeMallIndexConfigService.getConfigGoodsesForIndex(
                        IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(),
                        Constants.INDEX_GOODS_HOT_NUMBER);
        List<NewBeeMallIndexConfigGoodsVO> newGoodses =
                newBeeMallIndexConfigService.getConfigGoodsesForIndex(
                        IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(),
                        Constants.INDEX_GOODS_NEW_NUMBER);
        List<NewBeeMallIndexConfigGoodsVO> recommendGoodses =

        newBeeMallIndexConfigService.getConfigGoodsesForIndex(
                IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(),
                Constants.INDEX_GOODS_RECOMMOND_NUMBER);
        IndexPageResult indexPageResult = new IndexPageResult();
        indexPageResult.setCarousels(carousels);
        indexPageResult.setCategories(categories);
        indexPageResult.setHotGoodses(hotGoodses);
        indexPageResult.setNewGoodses(newGoodses);
        indexPageResult.setRecommendGoodses(recommendGoodses);
        return ResultGenerator.genSuccessResult(indexPageResult);
    }
}
