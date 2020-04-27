package ltd.newbee.mall.controller.mall.response;

import ltd.newbee.mall.controller.vo.NewBeeMallShoppingCartItemVO;

import java.io.Serializable;
import java.util.List;

public class SettlePageResult implements Serializable {
    private static final long serialVersionUID = -2957106998513137603L;

    private Integer priceTotal;

    private List<NewBeeMallShoppingCartItemVO> myShoppingCartItems;

    public Integer getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Integer priceTotal) {
        this.priceTotal = priceTotal;
    }

    public List<NewBeeMallShoppingCartItemVO> getMyShoppingCartItems() {
        return myShoppingCartItems;
    }

    public void setMyShoppingCartItems(List<NewBeeMallShoppingCartItemVO> myShoppingCartItems) {
        this.myShoppingCartItems = myShoppingCartItems;
    }
}
