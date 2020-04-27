package ltd.newbee.mall.controller.admin;

import com.sun.org.apache.regexp.internal.RE;
import ltd.newbee.mall.common.ExceptionStatusCodeEnum;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.AdminUser;
import ltd.newbee.mall.req.admin.*;
import ltd.newbee.mall.res.admin.*;
import ltd.newbee.mall.service.AdminUserService;

import ltd.newbee.mall.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminController {

    @Resource
    private AdminUserService adminUserService;

   /* @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    @GetMapping({"/test"})
    public String test() {
        return "admin/test";
    }*/


    /*@GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "admin/index";
    }*/

    @PostMapping(value = "/login")
    public AdminLoginResponse login(@RequestBody AdminLoginRequest request, HttpSession session) {
        AdminLoginResponse response = new AdminLoginResponse();
        AdminUser adminUser = adminUserService.login(request.getUserName(), request.getPassword());
        try {
            if (adminUser != null) {
                session.setAttribute("loginUser", adminUser.getNickName());
                session.setAttribute("loginUserId", adminUser.getAdminUserId());
                response.setLoginUser(adminUser.getNickName());
                response.setLoginUserId(adminUser.getAdminUserId().toString());
                String token = Base64Utils.encodeToString(adminUser.getAdminUserId().toString().getBytes("utf-8"));
                response.setToken(token);
                response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
                response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
            } else {
                throw  new NewBeeMallException("用户名或密码错误");
            }
        } catch (UnsupportedEncodingException e) {
            throw  new NewBeeMallException("加密失败");
        }

        return response;
    }

    @PostMapping("/profile")
    public AdminProfileResponse profile(@RequestBody AdminProfileRequst requst) {
        AdminProfileResponse response = new AdminProfileResponse();
        Integer loginUserId = TokenUtils.parseToken(requst.getToken());
        AdminUser adminUser = adminUserService.getUserDetailById(Integer.valueOf(loginUserId));
        if (adminUser == null) {
            throw  new NewBeeMallException("当前用户不存在");
        } else {
            response.setNickName(adminUser.getNickName());
            response.setLoginUserName(adminUser.getLoginUserName());
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
            return response;
        }
    }

    @PostMapping("/profile/password")
    public AdminProfilePasswordResponse passwordUpdate(@RequestBody AdminProfilePasswordRequest request, HttpServletRequest httpServletRequest) {
        AdminProfilePasswordResponse response = new AdminProfilePasswordResponse();
        Integer loginUserId = TokenUtils.parseToken(request.getToken());
        if (adminUserService.updatePassword(loginUserId, request.getOriginalPassword(), request.getNewPassword())) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            httpServletRequest.getSession().removeAttribute("loginUserId");
            httpServletRequest.getSession().removeAttribute("loginUser");
            httpServletRequest.getSession().removeAttribute("errorMsg");
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("修改失败");
        }
        return response;
    }

    @PostMapping("/profile/name")
    public AdminProfileNameResponse nameUpdate(@RequestBody AdminProfileNameRequest request) {
        AdminProfileNameResponse response = new AdminProfileNameResponse();
        Integer loginUserId = TokenUtils.parseToken(request.getToken());
        if (adminUserService.updateName(loginUserId, request.getLoginUserName(), request.getNickName())) {
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        } else {
            throw  new NewBeeMallException("修改失败");
        }
        return response;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
    }

    @GetMapping("/admin")
    public Map<String, Object> usersPage(HttpServletRequest request) {
        request.setAttribute("path", "users");
        Map<String, Object> map = new HashMap<>(3);
        map.put("path", "users");
        //return "admin/newbee_mall_admin";
        return map;
    }

    /**
     * 列表
     */
    @PostMapping("/user/list")
    public AdminUserListResponse list(@RequestBody AdminUserListRequest request) {
        AdminUserListResponse response = new AdminUserListResponse();
        Map<String, Object> params = new HashMap<>();
        params.put("page", request.getPage());
        params.put("limit", request.getLimit());
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        try {
            PageResult data = adminUserService.getNewBeeMallUsersPage(pageUtil);
            response.setData(data);
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } catch (Exception e) {
            throw  new NewBeeMallException("获取列表失败");
        }
        return response;
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @PostMapping("/user/delete")
    public AdminUserLockResponse delete(@RequestBody AdminUserLockRequest request) {
        AdminUserLockResponse response = new AdminUserLockResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Integer[] ids = new Integer[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Integer.valueOf(idsList[i]);
        }
        if (adminUserService.delete(ids)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("删除失败");
        }
        return response;
    }

    /**
     * 新增
     */
    @PostMapping("/user/save")
    public AdminUserCreateResponse delete(@RequestBody AdminUserCreateRequest request) {
        AdminUserCreateResponse response = new AdminUserCreateResponse();
        AdminUser adminUser = new AdminUser();
        adminUser.setLoginUserName(request.getLoginUserName());
        adminUser.setLoginPassword(request.getLoginPassword());
        adminUser.setNickName(request.getNickName());
        adminUser.setLocked(Byte.valueOf(request.getLocked()));
        String result = adminUserService.saveAdminUser(adminUser);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("保存失败");
        }
        return response;
    }

    /**
     * 更新
     */
    @PostMapping("/user/update")
    public AdminUserUpdateResponse update(@RequestBody AdminUserUpdateRequest request) {
        AdminUserUpdateResponse response = new AdminUserUpdateResponse();
        AdminUser adminUser = new AdminUser();
        adminUser.setLocked(Byte.valueOf(request.getLocked()));
        adminUser.setNickName(request.getNickName());
        adminUser.setAdminUserId(Integer.valueOf(request.getAdminUserId()));
        adminUser.setLoginUserName(request.getLoginUserName());
        String result = adminUserService.updateAdminUser(adminUser);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("修改失败");
        }
        return response;
    }


}
