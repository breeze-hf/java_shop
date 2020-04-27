package ltd.newbee.mall.controller.mall.request;

import java.io.Serializable;

public class NewBeeMallGoodsDetailRequest implements Serializable {
    private static final long serialVersionUID = -3724400502294480746L;

    private String goodsId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
