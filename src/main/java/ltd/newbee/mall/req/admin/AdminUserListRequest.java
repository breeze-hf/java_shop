package ltd.newbee.mall.req.admin;

import com.sun.org.apache.regexp.internal.RE;
import lombok.Getter;
import lombok.Setter;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 15:03
 */
@Setter
@Getter
public class AdminUserListRequest extends Result<PageQueryUtil> {

    private static final long serialVersionUID = -7886002688820389871L;

    /**
     * 当前页码
     */
    private String page;

    /**
     * 每页条数
     */
    private String limit;
}
