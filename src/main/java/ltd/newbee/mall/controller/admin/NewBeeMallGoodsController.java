package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.*;
import ltd.newbee.mall.controller.vo.NewBeeMallIndexCategoryVO;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.req.goods.*;
import ltd.newbee.mall.res.goods.*;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.*;


@RestController
@RequestMapping("/admin")
public class NewBeeMallGoodsController {

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

  /*  @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_goods");
        return "admin/newbee_mall_goods";
    }*/

    @PostMapping("/goods/edit")
    public GoodsEidtResponse edit(@RequestBody GoodsEidtRequest request) {
        GoodsEidtResponse response = new GoodsEidtResponse();
        Map<String, Object> map = new HashMap<>(3);
        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (CollectionUtils.isEmpty(firstLevelCategories)) {
            throw new NewBeeMallException("categories为空异常");
        }
        //查询一级分类列表中第一个实体的所有二级分类
        List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());

        List<NewBeeMallIndexCategoryVO> categories =
                newBeeMallCategoryService.getCategoriesForIndex();
        map.put("allCategoryies",categories);
//        if (!CollectionUtils.isEmpty(secondLevelCategories)) {
//            //查询二级分类列表中第一个实体的所有三级分类
//            List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
//            map.put("firstLevelCategories", firstLevelCategories);
//            map.put("secondLevelCategories", secondLevelCategories);
//            map.put("thirdLevelCategories", thirdLevelCategories);
//
//            map.put("path", "goods-edit");
//            response.setData(map);
//
//        }
        response.setData(map);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;

    }

    @PostMapping("/goods/detail")
    public GoodsEidtDetailResponse edit(@RequestBody GoodsEidtDetailRequest request) {
        GoodsEidtDetailResponse response = new GoodsEidtDetailResponse();
        Long goodsId = request.getGoodsId();
        Map<String, Object> map = new HashMap<>(6);
        NewBeeMallGoods newBeeMallGoods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        if (newBeeMallGoods == null) {
            throw new NewBeeMallException("newBeeMallGoods == null异常");
        }
        if (newBeeMallGoods.getGoodsCategoryId() > 0) {
            if (newBeeMallGoods.getGoodsCategoryId() != null || newBeeMallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = newBeeMallCategoryService.getGoodsCategoryById(newBeeMallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = newBeeMallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firestCategory = newBeeMallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            map.put("firstLevelCategories", firstLevelCategories);
                            map.put("secondLevelCategories", secondLevelCategories);
                            map.put("thirdLevelCategories", thirdLevelCategories);
                            map.put("firstLevelCategoryId", firestCategory.getCategoryId());
                            map.put("secondLevelCategoryId", secondCategory.getCategoryId());
                            map.put("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (newBeeMallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    map.put("firstLevelCategories", firstLevelCategories);
                    map.put("secondLevelCategories", secondLevelCategories);
                    map.put("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }

        map.put("goods", newBeeMallGoods);
        map.put("path", "goods-edit");
        response.setData(map);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;
    }

    /**
     * 列表
     */
    @PostMapping("/goods/list")
    public GoodsListResponse list(@RequestBody GoodsListRequest request) {
        GoodsListResponse response = new GoodsListResponse();
        Map<String, Object> params = new HashMap<>();

        Integer page = 1;
        if (!StringUtils.isEmpty(request.getPage())){
            page=Integer.valueOf(request.getPage());
        }
        Integer limit = 10;
        if (!StringUtils.isEmpty(request.getLimit())){
            limit=Integer.valueOf(request.getLimit());
        }

        params.put("page", page);
        params.put("limit", limit);
        params.put("goodsId", request.getGoodsId());
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult result = newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil);
        response.setData(result);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;
    }

    /**
     * 添加
     */
    @PostMapping("/goods/save")
    public GoodsSaveResponse save(@RequestBody GoodsSaveRequest request) {
        GoodsSaveResponse response = new GoodsSaveResponse();
        NewBeeMallGoods newBeeMallGoods = new NewBeeMallGoods();
        BeanUtil.copyProperties(request, newBeeMallGoods);
        newBeeMallGoods.setCreateTime(new Date());
        newBeeMallGoods.setUpdateTime(new Date());
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(newBeeMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw new NewBeeMallException("保存失败");
        }
        return response;
    }


    /**
     * 修改
     */
    @PostMapping("/goods/update")
    public GoodsUpdateResponse update(@RequestBody GoodsUpdateRequest request) {
        GoodsUpdateResponse response = new GoodsUpdateResponse();
        NewBeeMallGoods newBeeMallGoods = new NewBeeMallGoods();
        BeanUtil.copyProperties(request, newBeeMallGoods);
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(newBeeMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw new NewBeeMallException("更新失败");
        }
        return response;
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @PostMapping("/goods/status")
    public GoodsUpdateStatusResponse delete(@RequestBody GoodsUpdateStatusRequest request) {
        GoodsUpdateStatusResponse response = new GoodsUpdateStatusResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Long[] ids = new Long[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Long.valueOf(idsList[i]);
        }
        int sellStatus = request.getSellStatus();
        if (newBeeMallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw new NewBeeMallException("修改失败");
        }
        return response;
    }

    /**
     * 分类删除
     */
    @PostMapping("/goods/delete")
    public GoodsDeleteResponse delete(@RequestBody GoodsDeleteRequest request) {
        GoodsDeleteResponse response = new GoodsDeleteResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Integer[] ids = new Integer[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Integer.valueOf(idsList[i]);
        }

        if (newBeeMallGoodsService.checkGoods(ids)) {
            throw new NewBeeMallException("该商品处于销售中，不允许删除");
        }

        if (newBeeMallGoodsService.deleteBatch(ids)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw new NewBeeMallException("删除失败");
        }
        return response;
    }

}