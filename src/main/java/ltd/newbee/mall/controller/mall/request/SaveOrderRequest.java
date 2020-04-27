package ltd.newbee.mall.controller.mall.request;

import java.io.Serializable;

public class SaveOrderRequest implements Serializable{
    private static final long serialVersionUID = -7967867642156361703L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
