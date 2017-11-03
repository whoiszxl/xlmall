package com.zxlvoid.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zxlvoid.common.Const;
import com.zxlvoid.common.ResponseCode;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.User;
import com.zxlvoid.service.ICategoryService;
import com.zxlvoid.service.IUserService;

/**
 * 
 * @author whoiszxl
 *
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {
	
	@Autowired
    private ICategoryService iCategoryService;
	
	@GetMapping("/add_category.do")
	public ServerResponse<String> addCategory(HttpSession session, String categoryName, @RequestParam(value="parentId",defaultValue="0") int parentId) {
		return iCategoryService.addCategory(categoryName, parentId);
	}
	
	@GetMapping("set_category_name.do")
    public ServerResponse<String> setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        //更新categoryName
        return iCategoryService.updateCategoryName(categoryId,categoryName); 
    }

    @GetMapping("get_category.do")
    public ServerResponse<?> getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
       //查询子节点的category信息,并且不递归,保持平级
       return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @GetMapping("get_deep_category.do")
    public ServerResponse<?> getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        return iCategoryService.selectCategoryAndChildrenById(categoryId);

    }
	
}
