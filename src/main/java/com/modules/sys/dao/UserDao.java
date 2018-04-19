package com.modules.sys.dao;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.modules.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 
 * @ClassName: UserDao
 * @author zhoubin
 * @createDate 2017年6月30日 下午3:45:33
 */
//@Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，所以统一配置@MapperScan在扫描路径在application类中
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 通过登录名获取用户信息
	 * @Title getUserByLoginName
	 * @require 
	 * @param loginName 登录名
	 * @return 
	 * @throws 
	 * @author zhoubin
	 * @date 2017年6月30日 下午4:12:33
	 * @history
	 */
	User getUserByLoginName(@Param("loginName") String loginName);

	/**
	 * 查询所有用户
	 * @Title findAllUser
	 * @require
	 * @return
	 * @throws
	 * @author zhoubin
	 * @date 2017年9月18日 下午5:24:03
	 * @history
	 */
	List<User> findAllUser();
	
}