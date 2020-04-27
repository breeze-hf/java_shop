package ltd.newbee.mall.controller.mall.request;

import java.io.Serializable;

public class NewBeeMallOrderDetailRequest implements Serializable {
    private static final long serialVersionUID = -4784098996479218323L;

    String orderNo;

    Long userId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
