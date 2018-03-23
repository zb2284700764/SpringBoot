package com.modules.sys.service;

import com.common.service.CrudService;
import com.modules.sys.dao.MenuDao;
import com.modules.sys.dao.RoleDao;
import com.modules.sys.entity.Menu;
import com.modules.sys.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends CrudService<RoleDao, Role> {


    /**
     * 根据 userId 查询对应的角色
     * @param userId
     * @return
     */
    public List<Role> findRoleByUserId(String userId){

        return dao.findRoleByUserId(userId);
    }

}
