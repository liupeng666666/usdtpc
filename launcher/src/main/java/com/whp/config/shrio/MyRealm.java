package com.whp.config.shrio;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SubUserInterface;
import com.whp.usdt.user.utils.JWTUtil;
import com.whp.config.jwt.JWTToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.tags.SecureTag;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author : 张吉伟
 * @data : 2018/5/15 15:12
 * @descrpition : 前台用户授权信息，暂未提供权限。
 */
@Repository
public class MyRealm extends AuthorizingRealm {
    @Resource
    private SubUserInterface subUserInterface;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = JWTUtil.getUsername(principalCollection.toString(), "username");
        if (username == null) {
            throw new UnauthorizedException("token 不存在，请重新登陆");

        }
        Map<String, Object> json = subUserInterface.getSubUserByUserName(username);
        if (json == null) {
            throw new UnknownAccountException("用户不存在");
        }
        ArrayList<String> pers = new ArrayList<>();
        pers.add(json.get("rolename").toString());
        Set<String> permission = new HashSet<>(pers);
        simpleAuthorizationInfo.addStringPermissions(permission);

//        JSONObject jsons=sysModuleInterface.getModuleByUser(json.get("userid").toString());
//        if(jsons.getInteger("code")==100){
//            JSONArray array=jsons.getJSONArray("module");
//            ArrayList<String> pers = new ArrayList<>();
//            for(int i=0;i<array.size();i++){
//                JSONObject json_z=JSONObject.parseObject(array.get(i).toString());
//                pers.add(json_z.getString("shiro"));
//            }
//            Set<String> permission = new HashSet<>(pers);
//            simpleAuthorizationInfo.addStringPermissions(permission);
//        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String username = JWTUtil.getUsername(token, "username");
        if (username == null) {
            throw new UnauthorizedException("token 不存在，请重新登陆");

        }
        Map<String, Object> subUser = subUserInterface.getSubUserByUserName(username);
        if (subUser == null) {
            throw new UnknownAccountException("用户不存在");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(token, token, getName());
        return authenticationInfo;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
}
