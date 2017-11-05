package com.zxlvoid.service;

import com.github.pagehelper.PageInfo;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.Shipping;

/**
 * 
 * @author whoiszxl
 *
 */
public interface IShippingService {

	ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse<String> del(Integer userId,Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
