package ltd.newbee.mall.controller.mall.request;

import java.io.Serializable;

public class OrderListPageRequest implements Serializable {
    private static final long serialVersionUID = -5062142108922117808L;

    private String userId;
    private String page;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
