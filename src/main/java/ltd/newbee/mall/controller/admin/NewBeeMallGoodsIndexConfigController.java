package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ExceptionStatusCodeEnum;
import ltd.newbee.mall.common.IndexConfigTypeEnum;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.IndexConfig;
import ltd.newbee.mall.req.config.*;
import ltd.newbee.mall.res.config.*;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallIndexConfigService;
import ltd.newbee.mall.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/admin")
public class NewBeeMallGoodsIndexConfigController {

    @Resource
    private NewBeeMallIndexConfigService newBeeMallIndexConfigService;

    @GetMapping("/indexConfigs")
    public Map<String, Object> indexConfigsPage(HttpServletRequest request, @RequestParam("configType") int configType) {
        Map<String, Object> map = new HashMap<>(3);
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);
        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            //return "error/error_5xx";
            throw new NewBeeMallException("异常");
        }

        request.setAttribute("path", indexConfigTypeEnum.getName());
        request.setAttribute("configType", configType);

        map.put("path", indexConfigTypeEnum.getName());
        map.put("configType", configType);
        //return "admin/newbee_mall_index_config";
        return map;
    }

    /**
     * 列表
     */
    @PostMapping("/indexConfigs/list")
    public ConfigListResponse list(@RequestBody ConfigListRequest request) {
        ConfigListResponse response = new ConfigListResponse();
        Map<String, Object> params = new HashMap<>();
        params.put("page", request.getPage());
        params.put("limit", request.getLimit());
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult result = newBeeMallIndexConfigService.getConfigsPage(pageUtil);
        response.setData(result);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;
    }

    /**
     * 添加
     */
    @PostMapping("/indexConfigs/save")
    public ConfigSaveResponse save(@RequestBody ConfigSaveRequest request) {
        ConfigSaveResponse response = new ConfigSaveResponse();
        IndexConfig indexConfig = new IndexConfig();
        BeanUtil.copyProperties(request, indexConfig);
        indexConfig.setCreateTime(new Date());
        indexConfig.setUpdateTime(new Date());
        String result = newBeeMallIndexConfigService.saveIndexConfig(indexConfig);
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
    @PostMapping("/indexConfigs/update")
    public ConfigUpdateResponse update(@RequestBody ConfigUpdateRequest request) {
        ConfigUpdateResponse response = new ConfigUpdateResponse();
        IndexConfig indexConfig = new IndexConfig();
        BeanUtil.copyProperties(request, indexConfig);
        String result = newBeeMallIndexConfigService.updateIndexConfig(indexConfig);
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
    @PostMapping("/indexConfigs/info")
    public ConfigDetailResponse info(@RequestBody ConfigDetailRequest request) {
        ConfigDetailResponse response = new ConfigDetailResponse();
        Long id = request.getId();
        IndexConfig config = newBeeMallIndexConfigService.getIndexConfigById(id);
        if (config == null) {
            throw new NewBeeMallException("未查询到数据");
        }
        response.setData(config);
        response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
        response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        return response;
    }

    /**
     * 删除
     */
    @PostMapping("/indexConfigs/delete")
    public ConfigDeleteResponse delete(@RequestBody ConfigDeleteRequest request) {
        ConfigDeleteResponse response = new ConfigDeleteResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Long[] ids = new Long[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Long.valueOf(idsList[i]);
        }
        if (newBeeMallIndexConfigService.deleteBatch(ids)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw new NewBeeMallException("删除失败");
        }
        return response;
    }


}