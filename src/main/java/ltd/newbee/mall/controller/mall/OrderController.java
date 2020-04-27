package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.mall.request.NewBeeMallOrderDetailRequest;
import ltd.newbee.mall.controller.mall.request.OrderListPageRequest;
import ltd.newbee.mall.controller.mall.request.PaySuccessRequest;
import ltd.newbee.mall.controller.vo.NewBeeMallOrderDetailVO;
import ltd.newbee.mall.controller.vo.NewBeeMallShoppingCartItemVO;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.service.NewBeeMallShoppingCartService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Resource
    private NewBeeMallShoppingCartService newBeeMallShoppingCartService;
    @Resource
    private NewBeeMallOrderService newBeeMallOrderService;

    @PostMapping("/orders/orderDetailPage")
    @ResponseBody
    public Result orderDetailPage( @RequestBody NewBeeMallOrderDetailRequest request) {
        NewBeeMallOrderDetailVO orderDetailVO = newBeeMallOrderService.getOrderDetailByOrderNo(request.getOrderNo(), request.getUserId());
        if (orderDetailVO == null) {
            throw  new NewBeeMallException("orderDetailVO == null异常");
        }
        return ResultGenerator.genSuccessResult(orderDetailVO);
    }

    @PostMapping("/orders/orderListPage")
    @ResponseBody
    public Result orderListPage(@RequestBody OrderListPageRequest request) {
        Map<String, Object> params=new HashMap<>();
        String page="1";
        if (!StringUtils.isEmpty(request.getPage())) {
            params.put("page", 1);
            page=request.getPage();
        }

        params.put("userId",request.getUserId());
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        params.put("page",page);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);

        PageResult pageResult = newBeeMallOrderService.getMyOrders(pageUtil);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/orders/saveOrder")
    @ResponseBody
    public Result saveOrder(@RequestBody NewBeeMallUserVO user) {
        List<NewBeeMallShoppingCartItemVO> myShoppingCartItems =
                newBeeMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (StringUtils.isEmpty(user.getAddress().trim())) {
            // 无收货地址
            NewBeeMallException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            // 购物车中无数据则跳转至错误页
            NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        // 保存订单并返回订单号
        String saveOrderResult = newBeeMallOrderService.saveOrder(user, myShoppingCartItems);
        // 跳转到订单详情页
        // return "redirect:/orders/" + saveOrderResult;
        return ResultGenerator.genSuccessResult(saveOrderResult);
    }

    @PostMapping("/orders/cancel/{orderNo}")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo,@RequestBody NewBeeMallUserVO user) {
        String cancelOrderResult = newBeeMallOrderService.cancelOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PostMapping("/orders/finish/{orderNo}")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo,@RequestBody NewBeeMallUserVO user) {
        String finishOrderResult = newBeeMallOrderService.finishOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @PostMapping("/orders/selectPayType/{orderNo}")
    @ResponseBody
    public Result selectPayType( @PathVariable("orderNo") String orderNo) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderService.getNewBeeMallOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        return ResultGenerator.genSuccessResult(newBeeMallOrder);
    }

    @PostMapping("/orders/payPage/{orderNo}")
    @ResponseBody
    public Result payOrder(@PathVariable("orderNo") String orderNo) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderService.getNewBeeMallOrderByOrderNo(orderNo);
        return ResultGenerator.genSuccessResult(newBeeMallOrder);
    }

    @PostMapping("/orders/paySuccess/{orderNo}")
    @ResponseBody
    public Result paySuccess(@PathVariable("orderNo") String orderNo,@RequestBody PaySuccessRequest request) {
        String payResult = newBeeMallOrderService.paySuccess(orderNo, request.getPayType());
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

}
