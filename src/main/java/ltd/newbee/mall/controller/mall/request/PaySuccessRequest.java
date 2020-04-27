package ltd.newbee.mall.controller.mall.request;

import java.io.Serializable;

public class PaySuccessRequest implements Serializable {
    private static final long serialVersionUID = -8688040296336822018L;

    /** 1-支付宝，2-微信 */
    private int payType;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
