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
	ServerResponse<String> addCategory(String categoryName, Integer parentId);
    ServerResponse<String> updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);	
}	
