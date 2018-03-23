package com.modules.sys.shiro;

import com.common.config.Global;
import com.common.util.Encodes;
import com.modules.sys.entity.Menu;
import com.modules.sys.entity.Role;
import com.modules.sys.entity.User;
import com.modules.sys.service.MenuService;
import com.modules.sys.service.RoleService;
import com.modules.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;


    /**
     * 认证回调函数，登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        System.out.println("登录认证，登录信息和用户验证信息验证 --> doGetAuthenticationInfo");

        // /获取用户名和密码的令牌
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authToken;

        User user = userService.getByLoginName(usernamePasswordToken.getUsername());
        if (user == null) {
            throw new AccountException("帐号或密码不正确！");
        } else if (Global.NO.equals(user.getLoginFlag())) {
            throw new DisabledAccountException("帐号已经禁止登录！");
        } else {
            // FIXME 更新登录时间 last login time

        }

        // 密码加密
        byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
        // 登录认证
        return new SimpleAuthenticationInfo(user, // 用户名
                                            user.getPassword().substring(16), // 密码
                                            ByteSource.Util.bytes(salt), // salt = 加密之后的密码
                                            getName() // realm name
        );
    }


    /**
     * 授权查询回调函数
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取当前已经登录的用户
        User user = (User) getAvailablePrincipal(principals);
        System.out.println("登录之后访问权限控制的菜单或内容时进行授权--> doGetAuthorizationInfo " + user.getLoginName());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 根据 userId 查询对应的角色和权限
        List<Role> roles = roleService.findRoleByUserId(user.getId());

        List<Menu> menus = menuService.findMenuByUserId(user.getId());


        // 查询该登录用户对应的菜单


//		// 添加基于Permission的权限信息
//		info.addStringPermission("add");
//		info.addStringPermission("edit");
//		info.addStringPermission("delete");
//		// 添加用户角色信息
//		info.addRole("admin");
//		info.addRole("role");


        // 角色集合
//        List<String> roles = Lists.newArrayList();
//        roles.add("admin");
//        roles.add("guest");
//        info.addRoles(roles);
//
//        // 权限集合
//        List<String> permissions = Lists.newArrayList();
//        permissions.add("add");
//        permissions.add("edit");
//        info.addStringPermissions(permissions);

        return info;
    }


    /**
     * @PostConstruct web 容器启动的时候在 servlet 加载之后在 init 方法之前执行
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
//		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
//		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }


//	public SystemService getSystemService() {
//		if(systemService == null) {
//			systemService = SpringContextHolder.getBean(SystemService.class);
//		}
//		return systemService;
//	}


}