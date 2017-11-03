package com.zxlvoid.service;

import com.github.pagehelper.PageInfo;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.Product;
import com.zxlvoid.vo.ProductDetailVo;

/**
 * 
 * @author whoiszxl
 *
 */
public interface IProductService {
	ServerResponse<String> saveOrUpdateProduct(Product product);

	ServerResponse<String> setSaleStatus(Integer productId, Integer status);

	ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

	ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

	ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

	ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

	ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize,
			String orderBy);
}
