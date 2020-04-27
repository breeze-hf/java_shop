package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ExceptionStatusCodeEnum;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.entity.IndexConfig;
import ltd.newbee.mall.req.carousel.*;
import ltd.newbee.mall.res.carousel.*;
import ltd.newbee.mall.service.NewBeeMallCarouselService;
import ltd.newbee.mall.service.NewBeeMallIndexConfigService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
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
public class NewBeeMallCarouselController {

    @Resource
    NewBeeMallCarouselService newBeeMallCarouselService;

    /*@GetMapping("/carousels")
    public String carouselPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_carousel");
        return "admin/newbee_mall_carousel";
    }*/

    /**
     * 列表
     */
    @PostMapping("/carousels/list")
    public CarouselListResponse list(@RequestBody CarouselListRequest request) {
        CarouselListResponse response = new CarouselListResponse();
        Map<String, Object> params = new HashMap<>();
        params.put("page", request.getPage());
        params.put("limit", request.getLimit());
        params.put("carouselId", request.getCarouselId());
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        try {
            PageResult pageResult = newBeeMallCarouselService.getCarouselPage(pageUtil);
            response.setData(pageResult);
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } catch (Exception e) {
            throw  new NewBeeMallException("获取列表失败");
        }
        return response;
    }

    /**
     * 添加
     */
    @PostMapping("/carousels/save")
    public CarouselSaveResponse save(@RequestBody CarouselSaveRequest request) {
        CarouselSaveResponse response = new CarouselSaveResponse();
        Carousel carousel = new Carousel();
        carousel.setCarouselUrl(request.getCarouselUrl());
        carousel.setRedirectUrl(request.getRedirectUrl());
        carousel.setCarouselRank(request.getCarouselRank());
        carousel.setCreateTime(new Date());
        carousel.setUpdateTime(new Date());
        String result = newBeeMallCarouselService.saveCarousel(carousel);
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
    @PostMapping("/carousels/update")
    public CarouselUpdateResponse update(@RequestBody CarouselUpdateRequest request) {
        CarouselUpdateResponse response = new CarouselUpdateResponse();
        Carousel carousel = new Carousel();
        carousel.setCarouselId(Integer.valueOf(request.getCarouselId()));
        carousel.setCarouselUrl(request.getCarouselUrl());
        carousel.setCarouselRank(request.getCarouselRank());
        carousel.setRedirectUrl(request.getRedirectUrl());
        String result = newBeeMallCarouselService.updateCarousel(carousel);
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
    @PostMapping("/carousels/info")
    public CarouselDetailResponse info(@RequestBody CarouselDetailRequest request) {
        CarouselDetailResponse response = new CarouselDetailResponse();
        Carousel carousel = newBeeMallCarouselService.getCarouselById(request.getId());
        if (carousel != null) {
            response.setData(carousel);
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("获取详情为空");
        }
        return response;
    }

    /**
     * 删除
     */
    @PostMapping("/carousels/delete")
    public CarouselDeletedResponse delete(@RequestBody CarouselDeletedRequest request) {
        CarouselDeletedResponse response = new CarouselDeletedResponse();
        String idsStr = request.getIds();
        String[] idsList = idsStr.split(",");
        Integer[] ids = new Integer[idsList.length];
        for (int i = 0; i < idsList.length; i++) {
            ids[i] = Integer.valueOf(idsList[i]);
        }
        if (newBeeMallCarouselService.deleteBatch(ids)) {
            response.setResultCode(ExceptionStatusCodeEnum.SUCCESS.getCode());
            response.setMessage(ExceptionStatusCodeEnum.SUCCESS.getMassege());
        } else {
            throw  new NewBeeMallException("删除失败");
        }
        return response;
    }

}