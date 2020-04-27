package ltd.newbee.mall.req.admin;

import lombok.Data;
import ltd.newbee.mall.util.Result;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 19:11
 */
@Data
public class AdminProfileRequst extends Result {

    private static final long serialVersionUID = -3579722406431490452L;

    @NotBlank(message = "token不能为空")
    private String token;


}
