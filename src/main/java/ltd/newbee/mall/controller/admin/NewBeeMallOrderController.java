package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ExceptionStatusCodeEnum;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallOrderItemVO;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.req.order.*;
import ltd.newbee.mall.res.order.*;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class NewBeeMallOrderController {

    @Resource
    private NewBeeMallOrderService newBeeMallOrderService;

    @GetMapping("/orders")
    public String ordersPage(HttpServletRequest request) {
        request.setAttribute("path", "orders");
        return "admin/newbee_mall_order";
    }

    /**
     * 列表
     */
    @PostMapping("/orders/list")
    public OrderListResponse list(@RequestBody OrderListRequest request) {
        OrderListResponse response = new OrderListResponse();
        Map<String, Object> params = new HashMap<>();
        params.put("page", request.getPage());
        params.put("limit", request.getLimit());
        params.put("orderNo", request.getOrderNo());
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult result = newBeeMallOrderService.getNewBeeMallOrdersPage(pageUtil);
        response.setData(result);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;
    }

    /**
     * 修改
     */
    @PostMapping("/orders/update")
    public OrderUpdateResponse update(@RequestBody OrderUpdateRequest request) {
        OrderUpdateResponse response = new OrderUpdateResponse();
        NewBeeMallOrder newBeeMallOrder = new NewBeeMallOrder();
        newBeeMallOrder.setOrderId(request.getId());
        newBeeMallOrder.setUserAddress(request.getUserAddress());
        String result = newBeeMallOrderService.updateOrderInfo(newBeeMallOrder);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("更新失败");
        }
        return response;
    }

    /**
     * 详情
     */
    @PostMapping("/order/items")
    public OrderDetailResponse info(@RequestBody OrderDetailRequest request) {
        OrderDetailResponse response = new OrderDetailResponse();
        List<NewBeeMallOrderItemVO> orderItems = newBeeMallOrderService.getOrderItems(request.getId());
        if (!CollectionUtils.isEmpty(orderItems)) {
            response.setData(orderItems);
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("当前订单不存在");
        }
        return response;
    }

    /**
     * 配货
     */
    @PostMapping("/orders/checkDone")
    public OrderSendOrderResponse checkDone(@RequestBody OrderSendOrderRequest request) {
        OrderSendOrderResponse response = new OrderSendOrderResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Long[] ids = new Long[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Long.valueOf(idsList[i]);
        }
        String result = newBeeMallOrderService.checkDone(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("配货失败");
        }
        return response;
    }

    /**
     * 出库
     */
    @PostMapping("/orders/checkOut")
    public OrderCheckOutResponse checkOut(@RequestBody OrderCheckOutRequest request) {
        OrderCheckOutResponse response = new OrderCheckOutResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Long[] ids = new Long[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Long.valueOf(idsList[i]);
        }
        String result = newBeeMallOrderService.checkOut(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("出库失败");
        }
        return response;
    }

    /**
     * 关闭订单
     */
    @PostMapping("/orders/close")
    public OrderClosrResponse closeOrder(@RequestBody OrderCloseRequest request) {
        OrderClosrResponse response = new OrderClosrResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Long[] ids = new Long[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Long.valueOf(idsList[i]);
        }
        String result = newBeeMallOrderService.closeOrder(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("关闭订单失败");
        }
        return response;
    }


}