package ltd.newbee.mall.res.order;

import lombok.Data;
import ltd.newbee.mall.controller.vo.NewBeeMallOrderItemVO;
import ltd.newbee.mall.util.Result;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/13 23:11
 */
@Data
public class OrderDetailResponse extends Result<List<NewBeeMallOrderItemVO>> {
    private static final long serialVersionUID = 3573861548366653648L;
}
