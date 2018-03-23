package com.modules.sys.dao;

import com.common.persistence.CrudDao;
import com.modules.sys.entity.Menu;
import com.modules.sys.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author zhoubin
 * @ClassName: RoleDao
 * @createDate 2017年6月30日 下午3:45:33
 */
//@Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，所以统一配置@MapperScan在扫描路径在application类中
public interface RoleDao extends CrudDao<Role> {

    /**
     * 根据 userId 查询对应的角色
     * @param userId
     * @return
     */
    List<Role> findRoleByUserId(@Param("userId") String userId);

}