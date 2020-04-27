package ltd.newbee.mall.controller.mall.request;

import java.io.Serializable;

public class SearchPageRequest implements Serializable {

  private static final long serialVersionUID = -7802269675156439762L;

  private String page;

  private String keyword;
  private String orderBy;
  private String goodsCategoryId;

  public String getGoodsCategoryId() {
    return goodsCategoryId;
  }

  public void setGoodsCategoryId(String goodsCategoryId) {
    this.goodsCategoryId = goodsCategoryId;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }
}
