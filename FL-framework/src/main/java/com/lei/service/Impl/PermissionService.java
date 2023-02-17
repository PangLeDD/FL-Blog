package com.lei.service.Impl;

import com.lei.mapper.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/2 19:07
 * @Version : 1.0.0
 */

@Service("ps")
public class PermissionService {

    public Boolean hasPermission(String perm){
        if (SecurityUtils.getUserId().equals(1L)){
            return true;
        }
        if (perm == null){
            return false;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(perm);
    }
}
