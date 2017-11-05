package com.zxlvoid.controller.backend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.zxlvoid.common.Const;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.Product;
import com.zxlvoid.pojo.User;
import com.zxlvoid.service.IFileService;
import com.zxlvoid.service.IProductService;
import com.zxlvoid.service.IUserService;
import com.zxlvoid.utils.PropertiesUtil;

import io.swagger.annotations.Api;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value="后台商品管理模块",description="后台商品管理模块")
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

	@Autowired
	private IProductService iProductService;

	@Autowired
	private IFileService iFileService;

	@Autowired
	private IUserService iUserService;

	@PostMapping("save.do")
	public ServerResponse<String> productSave(HttpSession session, Product product) {
		return iProductService.saveOrUpdateProduct(product);
	}

	@PostMapping("set_sale_status.do")
	public ServerResponse<String> setSaleStatus(HttpSession session, Integer productId, Integer status) {
		return iProductService.setSaleStatus(productId, status);
	}

	@GetMapping("detail.do")
	public ServerResponse<?> getDetail(HttpSession session, Integer productId) {
		return iProductService.manageProductDetail(productId);
	}

	@GetMapping("list.do")
	public ServerResponse<?> getList(HttpSession session,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return iProductService.getProductList(pageNum, pageSize);
	}

	@GetMapping("search.do")
	public ServerResponse productSearch(HttpSession session, String productName, Integer productId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return iProductService.searchProduct(productName, productId, pageNum, pageSize);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("upload.do")
	public ServerResponse upload(HttpSession session,
			@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = iFileService.upload(file, path);
		String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

		Map fileMap = Maps.newHashMap();
		fileMap.put("uri", targetFileName);
		fileMap.put("url", url);
		return ServerResponse.createBySuccess(fileMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("richtext_img_upload.do")
	public Map richtextImgUpload(HttpSession session,
			@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Map resultMap = Maps.newHashMap();
		// {
		// "success": true/false,
		// "msg": "error message", # optional
		// "file_path": "[real file path]"
		// }
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = iFileService.upload(file, path);
		if (StringUtils.isBlank(targetFileName)) {
			resultMap.put("success", false);
			resultMap.put("msg", "上传失败");
			return resultMap;
		}
		String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
		resultMap.put("success", true);
		resultMap.put("msg", "上传成功");
		resultMap.put("file_path", url);
		response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
		return resultMap;

	}
}
