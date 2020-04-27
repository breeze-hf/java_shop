package ltd.newbee.mall.controller.mall.response;

import ltd.newbee.mall.controller.vo.NewBeeMallIndexCarouselVO;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexCategoryVO;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexConfigGoodsVO;

import java.io.Serializable;
import java.util.List;

public class IndexPageResult implements Serializable {
    private static final long serialVersionUID = -4178189711713382739L;

    private List<NewBeeMallIndexCarouselVO> carousels;

    List<NewBeeMallIndexConfigGoodsVO> hotGoodses;

    List<NewBeeMallIndexCategoryVO> categories;

    List<NewBeeMallIndexConfigGoodsVO> newGoodses;

    List<NewBeeMallIndexConfigGoodsVO> recommendGoodses;

    public List<NewBeeMallIndexCarouselVO> getCarousels() {
        return carousels;
    }

    public void setCarousels(List<NewBeeMallIndexCarouselVO> carousels) {
        this.carousels = carousels;
    }

    public List<NewBeeMallIndexConfigGoodsVO> getHotGoodses() {
        return hotGoodses;
    }

    public void setHotGoodses(List<NewBeeMallIndexConfigGoodsVO> hotGoodses) {
        this.hotGoodses = hotGoodses;
    }

    public List<NewBeeMallIndexCategoryVO> getCategories() {
        return categories;
    }

    public void setCategories(List<NewBeeMallIndexCategoryVO> categories) {
        this.categories = categories;
    }

    public List<NewBeeMallIndexConfigGoodsVO> getNewGoodses() {
        return newGoodses;
    }

    public void setNewGoodses(List<NewBeeMallIndexConfigGoodsVO> newGoodses) {
        this.newGoodses = newGoodses;
    }

    public List<NewBeeMallIndexConfigGoodsVO> getRecommendGoodses() {
        return recommendGoodses;
    }

    public void setRecommendGoodses(List<NewBeeMallIndexConfigGoodsVO> recommendGoodses) {
        this.recommendGoodses = recommendGoodses;
    }
}
