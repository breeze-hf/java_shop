package ltd.newbee.mall.res.mall;

import lombok.Data;
import ltd.newbee.mall.util.Result;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/19 15:56
 */
@Data
public class PersonLoginResponse extends Result {
    private static final long serialVersionUID = 6235411525944290296L;

    private String token;
}
