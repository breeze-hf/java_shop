package ltd.newbee.mall.controller.admin;

import com.sun.org.apache.regexp.internal.RE;
import ltd.newbee.mall.common.ExceptionStatusCodeEnum;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.req.user.AdminCustomerListRequest;
import ltd.newbee.mall.req.user.AdminCustomerLockedRequest;
import ltd.newbee.mall.res.user.AdminCustomerListResponse;
import ltd.newbee.mall.res.user.AdminCustomerLockedResponse;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class NewBeeMallUserController {

    @Resource
    private NewBeeMallUserService newBeeMallUserService;

    @GetMapping("/users")
    public String usersPage(HttpServletRequest request) {
        request.setAttribute("path", "users");
        return "admin/newbee_mall_user";
    }

    /**
     * 列表
     */
    @PostMapping("/users/list")
    public AdminCustomerListResponse list(@RequestBody AdminCustomerListRequest request) {
        AdminCustomerListResponse response = new AdminCustomerListResponse();
        Map<String, Object> params = new HashMap<>();
        params.put("page", request.getPage());
        params.put("limit", request.getLimit());
        params.put("id", request.getId());
        params.put("login_name", request.getLoginName());
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult result = newBeeMallUserService.getNewBeeMallUsersPage(pageUtil);
        response.setData(result);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @PostMapping("/users/lock")
    public AdminCustomerLockedResponse delete(@RequestBody AdminCustomerLockedRequest request) {
        AdminCustomerLockedResponse response = new AdminCustomerLockedResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Integer[] ids = new Integer[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Integer.valueOf(idsList[i]);
        }
        if (newBeeMallUserService.lockUsers(ids, request.getLockStatus())) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("删除用户失败");
        }
        return response;
    }
}