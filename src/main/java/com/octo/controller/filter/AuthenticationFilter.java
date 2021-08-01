package com.octo.controller.filter;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.octo.model.entity.User;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.service.UserService;
import com.octo.utils.Constants;
import com.octo.utils.http.UserMapper;

/**
 * Authentication filter.
 *
 * @author Vincent Moittié
 *
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    /**
     * HTTP resource.
     */
    @Context
    private ResourceInfo resourceInfo;

    /**
     * User service.
     */
    @Autowired
    private UserService userService;

    @Override
    public final void filter(final ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        User user = new UserMapper().apply(requestContext);

        if (!Constants.DEFAULT_ADMIN_LOGIN.equals(user.getLogin())
                || !userService.isDefaultAdminitratorAllowed(user.getPassword())) {
            throw new GlobalException(ErrorType.AUTHORIZATION_FAILED, "authentication");
        }
    }
}
