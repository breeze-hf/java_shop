package ltd.newbee.mall.res.mall;

import lombok.Data;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.util.Result;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/20 21:01
 */
@Data
public class AdminCustomerQueryResponse extends Result<MallUser> {
    private static final long serialVersionUID = 2775035487515099087L;
}
