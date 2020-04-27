package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ExceptionStatusCodeEnum;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.req.mall.AdminCustomerQueryRequest;
import ltd.newbee.mall.req.mall.PersonLoginRequest;
import ltd.newbee.mall.req.mall.PersonLogoutRequest;
import ltd.newbee.mall.req.mall.PersonRegisterRequest;
import ltd.newbee.mall.res.mall.AdminCustomerQueryResponse;
import ltd.newbee.mall.res.mall.PersonLoginResponse;
import ltd.newbee.mall.res.mall.PersonLogoutResponse;
import ltd.newbee.mall.res.mall.PersonRegisterResponse;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.util.MD5Util;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseBody
public class PersonalController {

    @Resource
    private NewBeeMallUserService newBeeMallUserService;

//     @GetMapping("/personal")
//     public Map<String, String> personalPage(HttpServletRequest request,
//                                             HttpSession httpSession) {
//         request.setAttribute("path", "personal");
//         Map<String, String> map = new HashMap<>(2);
//         map.put("path", "personal");
//         //return "mall/personal";
//         return map;
//     }

    @PostMapping("/logout")
    public PersonLogoutResponse logout(@RequestBody PersonLogoutRequest request, HttpSession httpSession) {
        PersonLogoutResponse response = new PersonLogoutResponse();

        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        return response;
    }

   /* @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "mall/login";
    }*/

   /* @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }*/

 /*   @GetMapping("/personal/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }*/

    @PostMapping("/login")
    public PersonLoginResponse login(@RequestBody PersonLoginRequest request, HttpSession httpSession) {
        PersonLoginResponse response = new PersonLoginResponse();
        String loginName = request.getLoginName();
        String password = request.getPassword();

        String token = newBeeMallUserService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"), httpSession);
        //登录成功
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        response.setToken(token);
        return response;
    }

    @PostMapping("/register")
    public PersonRegisterResponse register(@RequestBody PersonRegisterRequest request, HttpSession httpSession) {
        PersonRegisterResponse response = new PersonRegisterResponse();
        String loginName = request.getLoginName();
        String password = request.getPassword();
        String registerResult = newBeeMallUserService.register(loginName, password);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw new NewBeeMallException("注册失败");
        }
        return response;
    }

    @PostMapping("/user/Info")
    public AdminCustomerQueryResponse info(@RequestBody AdminCustomerQueryRequest request) {
        AdminCustomerQueryResponse response = new AdminCustomerQueryResponse();
        Long id = request.getId();
        MallUser mallUser = newBeeMallUserService.info(id);
        //注册成功
        response.setData(mallUser);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;
    }


   @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        NewBeeMallUserVO mallUserTemp = newBeeMallUserService.updateUserInfo(mallUser, httpSession);
        if (mallUserTemp == null) {
            Result result = ResultGenerator.genFailResult("修改失败");
            return result;
        } else {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        }
    }
}
