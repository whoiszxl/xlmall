package com.zxlvoid.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.zxlvoid.common.Const;
import com.zxlvoid.common.ResponseCode;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.dao.CartMapper;
import com.zxlvoid.dao.ProductMapper;
import com.zxlvoid.pojo.Cart;
import com.zxlvoid.pojo.Product;
import com.zxlvoid.service.ICartService;
import com.zxlvoid.utils.BigDecimalUtil;
import com.zxlvoid.utils.PropertiesUtil;
import com.zxlvoid.vo.CartProductVo;
import com.zxlvoid.vo.CartVo;

/**
 * 
 * @author whoiszxl
 *
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private ProductMapper productMapper;
	
	
	private Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
		if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
		
		Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
		if (cart == null) {
			// 这个产品不在当前用户的购物车内
			Cart cartItem = new Cart();
			cartItem.setQuantity(count);
			cartItem.setChecked(Const.Cart.CHECKED);
			cartItem.setProductId(productId);
			cartItem.setUserId(userId);

			cartMapper.insert(cartItem);
		} else {
			// 产品已经在购物车里
			cart.setQuantity(cart.getQuantity() + count);
			cartMapper.updateByPrimaryKeySelective(cart);
		}
		
		return this.list(userId);
	}

	
	public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
		if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
		
		Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
		if(cart != null) {
			cart.setQuantity(count);
		}
		cartMapper.updateByPrimaryKeySelective(cart);
		return this.list(userId);
	}
	
	public ServerResponse<CartVo> deleteProduct(Integer userId,String productIds){
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId,productList);
        return this.list(userId);
    }
	
	
	public ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked){
        cartMapper.checkedOrUncheckedProduct(userId,productId,checked);
        return this.list(userId);
    }

    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }
	
	
	
	public ServerResponse<CartVo> list (Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }
	
	private CartVo getCartVoLimit(Integer userId) {
		CartVo cartVo = new CartVo();
		List<Cart> cartList = cartMapper.selectCartByUserId(userId);
		List<CartProductVo> cartProductVos = Lists.newArrayList();

		BigDecimal cartTotalPrice = new BigDecimal("0");

		if (CollectionUtils.isNotEmpty(cartList)) {
			for (Cart cartItem : cartList) {
				CartProductVo cartProductVo = new CartProductVo();
				cartProductVo.setId(cartItem.getId());
				cartProductVo.setUserId(userId);
				cartProductVo.setProductId(cartItem.getProductId());

				Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());

				if (product != null) {
					cartProductVo.setProductMainImage(product.getMainImage());
					cartProductVo.setProductName(product.getName());
					cartProductVo.setProductSubtitle(product.getSubtitle());
					cartProductVo.setProductStatus(product.getStatus());
					cartProductVo.setProductPrice(product.getPrice());
					cartProductVo.setProductStock(product.getStock());

					// 判断库存
					int buyLimitCount = 0;
					if (product.getStock() >= cartItem.getQuantity()) {
						//库存足够的时候使用购物车里的商品数量
						buyLimitCount = cartItem.getQuantity();
						cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
					} else {
						//库存不足的时候使用商品的库存量
						buyLimitCount = product.getStock();
						cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
						// 更新有效库存
						Cart cartForQuantity = new Cart();
						cartForQuantity.setId(cartItem.getId());
						cartForQuantity.setQuantity(buyLimitCount);
						cartMapper.updateByPrimaryKeySelective(cartForQuantity);
					}
					cartProductVo.setQuantity(buyLimitCount);
					// 计算总价
					cartProductVo.setProductTotalPrice(
							BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
					cartProductVo.setProductChecked(cartItem.getChecked());
					logger.info("商品价格为：" + product.getPrice());
					logger.info("商品数量为：" + cartProductVo.getQuantity());
					logger.info("商品总价为：" + BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
				}
				if (cartItem.getChecked() == Const.Cart.CHECKED) {
					// 如果已经勾选,增加到整个的购物车总价中
					cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),
							cartProductVo.getProductTotalPrice().doubleValue());
				}
				cartProductVos.add(cartProductVo);
			}
		}
		
		cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVos);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
	}
	
	private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;

    }
}
