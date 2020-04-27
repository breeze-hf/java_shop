package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.mall.response.CartListPageResult;
import ltd.newbee.mall.controller.mall.response.SettlePageResult;
import ltd.newbee.mall.controller.vo.NewBeeMallShoppingCartItemVO;
import ltd.newbee.mall.entity.NewBeeMallShoppingCartItem;
import ltd.newbee.mall.service.NewBeeMallShoppingCartService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import ltd.newbee.mall.util.TokenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class ShoppingCartController {

    @Resource
    private NewBeeMallShoppingCartService newBeeMallShoppingCartService;

    @PostMapping("/shop-cart/{userId}")
    public Result cartListPage(@PathVariable("userId") Long userId) {
        int itemsTotal = 0;
        int priceTotal = 0;
        if (StringUtils.isEmpty(userId)){
            throw  new NewBeeMallException("userId不能为空");
        }
        List<NewBeeMallShoppingCartItemVO> myShoppingCartItems = newBeeMallShoppingCartService.getMyShoppingCartItems(userId);
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(NewBeeMallShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                throw  new NewBeeMallException("itemsTotal < 1异常");
            }
            //总价
            for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                throw  new NewBeeMallException("priceTotal < 1异常");
            }
        }

        CartListPageResult pageResult=new CartListPageResult();
        pageResult.setItemsTotal(itemsTotal);
        pageResult.setMyShoppingCartItems(myShoppingCartItems);
        pageResult.setPriceTotal(priceTotal);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/shop-cart/save/{userId}")
    public Result saveNewBeeMallShoppingCartItem(@RequestBody NewBeeMallShoppingCartItem newBeeMallShoppingCartItem,
                                                 @PathVariable("userId") Long userId) {
        newBeeMallShoppingCartItem.setUserId(userId);
//        Integer loginUserId = TokenUtils.parseToken(newBeeMallShoppingCartItem.getToken());
//        if (userId.toString()!=loginUserId.toString()){
//            throw  new NewBeeMallException("当前用户不存在");
//        }
        //todo 判断数量
        String saveResult = newBeeMallShoppingCartService.saveNewBeeMallCartItem(newBeeMallShoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PostMapping("/shop-cart/update/{userId}")
    public Result updateNewBeeMallShoppingCartItem(@RequestBody NewBeeMallShoppingCartItem newBeeMallShoppingCartItem,
                                                   @PathVariable("userId") Long userId) {
        newBeeMallShoppingCartItem.setUserId(userId);
//        Integer loginUserId = TokenUtils.parseToken(newBeeMallShoppingCartItem.getToken());
//        if (userId.toString()!=loginUserId.toString()){
//            throw  new NewBeeMallException("当前用户不存在");
//        }
        //todo 判断数量
        String updateResult = newBeeMallShoppingCartService.updateNewBeeMallCartItem(newBeeMallShoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @PostMapping("/shop-cart/delete/{newBeeMallShoppingCartItemId}")
    public Result updateNewBeeMallShoppingCartItem(@PathVariable("newBeeMallShoppingCartItemId") Long newBeeMallShoppingCartItemId) {
        Boolean deleteResult = newBeeMallShoppingCartService.deleteById(newBeeMallShoppingCartItemId);

        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @PostMapping("/shop-cart/settle/{userId}")
    public Result settlePage(@PathVariable("userId") Long userId) {
        SettlePageResult pageResult=new SettlePageResult();

        int priceTotal = 0;
        List<NewBeeMallShoppingCartItemVO> myShoppingCartItems = newBeeMallShoppingCartService.getMyShoppingCartItems(userId);
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return ResultGenerator.genSuccessResult(pageResult);
        } else {
            //总价
            for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                //return "error/error_5xx";
                throw  new NewBeeMallException("priceTotal < 1异常");
            }
        }

        pageResult.setPriceTotal(priceTotal);
        pageResult.setMyShoppingCartItems(myShoppingCartItems);
        return ResultGenerator.genSuccessResult(pageResult);
    }
}
