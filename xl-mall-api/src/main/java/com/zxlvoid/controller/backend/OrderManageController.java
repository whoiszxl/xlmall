package com.zxlvoid.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zxlvoid.common.Const;
import com.zxlvoid.common.ResponseCode;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.User;
import com.zxlvoid.service.IOrderService;
import com.zxlvoid.service.IUserService;
import com.zxlvoid.vo.OrderVo;

import io.swagger.annotations.Api;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value = "后台订单模块", description = "后台订单模块")
@RestController
@RequestMapping("/manage/order")
public class OrderManageController {
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IOrderService iOrderService;

	@GetMapping("list")
	public ServerResponse<PageInfo> orderList(
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return iOrderService.manageList(pageNum, pageSize);
	}

	@GetMapping("detail")
	public ServerResponse<OrderVo> orderDetail(Long orderNo) {
		return iOrderService.manageDetail(orderNo);

	}

	@GetMapping("search")
	public ServerResponse<PageInfo> orderSearch(Long orderNo,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return iOrderService.manageSearch(orderNo, pageNum, pageSize);
	}

	@PostMapping("send_goods")
	public ServerResponse<String> orderSendGoods(Long orderNo) {
		return iOrderService.manageSendGoods(orderNo);
	}
}
