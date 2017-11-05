package com.zxlvoid.controller.portal;

import static org.mockito.Matchers.contains;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zxlvoid.common.Const;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.Shipping;
import com.zxlvoid.pojo.User;
import com.zxlvoid.service.IShippingService;

import io.swagger.annotations.Api;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value="前台收货地址管理模块",description="前台收货地址管理模块")
@RestController
@RequestMapping("/shipping/")
public class ShippingController {

	@Autowired
	private IShippingService iShippingService;
	
	@PostMapping("add")
	public ServerResponse add(HttpSession session,Shipping shipping) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		return iShippingService.add(user.getId() , shipping);
	}
	
	@PostMapping("delete")
	public ServerResponse delete(HttpSession session,Integer shippingId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		return iShippingService.del(user.getId(),shippingId);
	}
	
	@PostMapping("update")
    public ServerResponse update(HttpSession session,Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return iShippingService.update(user.getId(),shipping);
    }


    @GetMapping("selects")
    public ServerResponse<Shipping> select(HttpSession session,Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return iShippingService.select(user.getId(),shippingId);
    }


    @PostMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return iShippingService.list(user.getId(),pageNum,pageSize);
    }
	
}
