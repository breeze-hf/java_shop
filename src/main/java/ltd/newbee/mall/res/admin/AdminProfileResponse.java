package ltd.newbee.mall.res.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ltd.newbee.mall.util.Result;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 19:14
 */
@Data
public class AdminProfileResponse extends Result {
    private static final long serialVersionUID = 1300818041249720026L;

    private String loginUserName;

    private String nickName;
}
