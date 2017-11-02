package com.zxlvoid.service;

import java.util.List;

import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.Category;

/**
 * 
 * @author whoiszxl
 *
 */
public interface ICategoryService {
	ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);	
}	
