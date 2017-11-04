package com.zxlvoid.controller.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.service.IProductService;
import com.zxlvoid.vo.ProductDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl
 * 前台商品模块
 */
@Api(value = "product",description = "前台商品模块")
@RestController
@RequestMapping("/product/")
public class ProductController {
	
	@Autowired
	private IProductService iProductService;
	
	@ApiOperation(value = "前台：通过商品id获取到商品详情",notes = "前台：通过商品id获取到商品详情n")
	@GetMapping("detail")
	public ServerResponse<ProductDetailVo> detail(Integer productId){
		return iProductService.getProductDetail(productId);
	}
	
	
	@GetMapping("list")
	public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
										 @RequestParam(value = "categoryId",required = false)Integer categoryId,
										 @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
										 @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
										 @RequestParam(value = "orderBy",defaultValue = "")String orderBy){
		return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
	}
}	
