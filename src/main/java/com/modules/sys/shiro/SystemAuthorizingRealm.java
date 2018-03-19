package com.modules.sys.shiro;

import com.common.config.Global;
import com.common.util.Encodes;
import com.google.common.collect.Lists;
import com.modules.sys.entity.User;
import com.modules.sys.service.SystemService;
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
    private SystemService systemService;


    /**
     * 认证回调函数，登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        System.out.println("登录认证，登录信息和用户验证信息验证 --> doGetAuthenticationInfo");

        // /获取用户名和密码的令牌
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authToken;


        User user = systemService.getByLoginName(usernamePasswordToken.getUsername());
        if (user != null) {
            if (Global.NO.equals(user.getLoginFlag())) {
                throw new AuthenticationException("msg:该已帐号禁止登录.");
            }

            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
            return new SimpleAuthenticationInfo(user, // 用户名
                                                user.getPassword().substring(16), // 密码
                                                ByteSource.Util.bytes(salt), // salt = 加密之后的密码
                                                getName() // realm name
            );
        } else {
            return null;
        }
    }


    /**
     * 授权查询回调函数,进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Object principal = getAvailablePrincipal(principals);
        System.out.println("登录之后访问权限控制的菜单或内容时进行授权--> doGetAuthorizationInfo " + principal);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		// 添加基于Permission的权限信息
//		info.addStringPermission("add");
//		info.addStringPermission("edit");
//		info.addStringPermission("delete");
//		// 添加用户角色信息
//		info.addRole("admin");
//		info.addRole("role");


        List<String> roles = Lists.newArrayList();
        List<String> permissions = Lists.newArrayList();
        roles.add("admin");
        roles.add("guest");
        permissions.add("add");
        permissions.add("edit");
        info.addRoles(roles);
        info.addStringPermissions(permissions);
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