package com.zxlvoid.controller.backend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.Product;
import com.zxlvoid.service.IProductService;

/**
 * 
 * @author whoiszxl
 *
 */
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

	@Autowired
	private IProductService iProductService;

	@PostMapping("save.do")
	public ServerResponse<String> productSave(HttpSession session, Product product) {
		return iProductService.saveOrUpdateProduct(product);
	}

	@PostMapping("save.do")
	public ServerResponse<String> setSaleStatus(HttpSession session, Integer productId, Integer status) {
		return iProductService.setSaleStatus(productId, status);
	}

	@GetMapping("detail.do")
	public ServerResponse<?> getDetail(HttpSession session, Integer productId){
		return iProductService.manageProductDetail(productId);
	}

	@GetMapping("list.do")
	public ServerResponse<?> getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
		return iProductService.getProductList(pageNum,pageSize);
	}
	
	@GetMapping("search.do")
    public ServerResponse productSearch(HttpSession session,String productName,Integer productId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        return iProductService.searchProduct(productName,productId,pageNum,pageSize);
    }
	
}
