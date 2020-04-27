package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ExceptionStatusCodeEnum;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.req.category.*;
import ltd.newbee.mall.res.category.*;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@ResponseBody
@RequestMapping("/admin")
public class NewBeeMallGoodsCategoryController {

    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

    @GetMapping("/categories")
    public Map<String, Object> categoriesPage(HttpServletRequest request, @RequestParam("categoryLevel") Byte categoryLevel, @RequestParam("parentId") Long parentId, @RequestParam("backParentId") Long backParentId) {
        if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
            //return "error/error_5xx";
            throw new NewBeeMallException("异常");
        }
        request.setAttribute("path", "newbee_mall_category");
        request.setAttribute("parentId", parentId);
        request.setAttribute("backParentId", backParentId);
        request.setAttribute("categoryLevel", categoryLevel);
        Map<String, Object> map = new HashMap<>(3);
        map.put("path", "newbee_mall_category");
        map.put("parentId", parentId);
        map.put("backParentId", backParentId);
        map.put("categoryLevel", categoryLevel);
        //return "admin/newbee_mall_category";
        return map;
    }

    /**
     * 列表
     */
    @PostMapping("/categories/list")
    public CategoryListResponse list(@RequestBody CategoryListRequest request) {
        CategoryListResponse response = new CategoryListResponse();
        Map<String, Object> params = new HashMap<>();
        params.put("page", request.getPage());
        params.put("limit", request.getLimit());
        params.put("categoryLevel", request.getCategoryLevel());
        params.put("parentId", request.getParentId());
        params.put("categoryName", request.getCategoryName());
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        try {
            PageResult result = newBeeMallCategoryService.getCategorisPage(pageUtil);
            response.setData(result);
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } catch (Exception e) {
            throw  new NewBeeMallException("获取列表失败");
        }
        return response;
    }

    /**
     * 列表
     */
    @PostMapping("/categories/listForSelect")
    public CategorySelectListResponse listForSelect(@RequestBody CategorySelectListRequest request) {
        CategorySelectListResponse response = new CategorySelectListResponse();
        Long categoryId = request.getCategoryId();
        GoodsCategory category = newBeeMallCategoryService.getGoodsCategoryById(categoryId);
        //既不是一级分类也不是二级分类则为不返回数据
        if (category == null || category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            response.setResultCode(ExceptionStatusCodeEnum.FAIL.getCode());
            response.setMessage("当前类目不是一级或者二级类目");
        }
        Map categoryResult = new HashMap(2);
        if (category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel()) {
            //如果是一级分类则返回当前一级分类下的所有二级分类，以及二级分类列表中第一条数据下的所有三级分类列表
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel()) {
            //如果是二级分类则返回当前分类下的所有三级分类列表
            List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        response.setData(categoryResult);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;
    }

    /**
     * 添加
     */
    @PostMapping("/categories/save")
    public CategoryCreateResponse save(@RequestBody CategoryCreateRequest request) {
        CategoryCreateResponse response = new CategoryCreateResponse();
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryName(request.getCategoryName());
        goodsCategory.setCategoryRank(request.getCategoryRank());
        goodsCategory.setParentId(request.getParentId());
        goodsCategory.setCategoryLevel(request.getCategoryLevel());
        goodsCategory.setCreateTime(new Date());
        goodsCategory.setUpdateTime(new Date());
        String result = newBeeMallCategoryService.saveCategory(goodsCategory);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("保存失败");
        }
        return response;
    }


    /**
     * 修改
     */
    @PostMapping("/categories/update")
    public CategoryUpdateResponse update(@RequestBody CategoryUpdateRequest request) {
        CategoryUpdateResponse response = new CategoryUpdateResponse();
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryId(request.getCategoryId());
        goodsCategory.setCategoryLevel(request.getCategoryLevel());
        goodsCategory.setParentId(request.getParentId());
        goodsCategory.setCategoryName(request.getCategoryName());
        goodsCategory.setCategoryRank(request.getCategoryRank());
        String result = newBeeMallCategoryService.updateGoodsCategory(goodsCategory);
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
    @PostMapping("/categories/info")
    public CategoryDetaileResponse info(@RequestBody CategoryDetaileRequest request) {
        CategoryDetaileResponse response = new CategoryDetaileResponse();
        Long id = request.getId();
        GoodsCategory goodsCategory = newBeeMallCategoryService.getGoodsCategoryById(id);
        if (goodsCategory != null) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
            response.setData(goodsCategory);
        } else {
            throw  new NewBeeMallException("当前详情不存在");
        }
        return response;
    }

    /**
     * 分类删除
     */
    @PostMapping("/categories/delete")
    public CategoryDeletedResponse delete(@RequestBody CategoryDeletedRequest request) {
        CategoryDeletedResponse response = new CategoryDeletedResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Integer[] ids = new Integer[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Integer.valueOf(idsList[i]);
        }
        if (newBeeMallCategoryService.checkCategories(ids)) {
            throw  new NewBeeMallException("当前类目已经被引用，不能被删除");
        }
        if (newBeeMallCategoryService.deleteBatch(ids)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("删除失败");
        }
        return response;
    }


}