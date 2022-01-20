package com.octo.security;

import com.octo.model.common.Constants;
import com.octo.model.common.UserRoleType;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.User;
import com.octo.service.UserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

/**
 * Authentication filter.
 *
 * @author Vincent Moittié
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    /**
     * Default authentication error.
     */
    private final GlobalException authenticationError =
            new GlobalException(ErrorType.AUTHORIZATION_FAILED, "authentication");
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
    public final void filter(final ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (!method.isAnnotationPresent(RolesAllowed.class)) {
            throw new GlobalException(ErrorType.INTERNAL_ERROR, "Missing RolesAllowed annotation.");
        }

        User user = new UserMapper().apply(requestContext);

        RolesAllowed annotation = method.getAnnotation(RolesAllowed.class);
        String[] roles = annotation.value();

        if (Constants.AUTHENTICATION_BASIC_SCHEME.equals(user.getAuthenticationType())) {
            this.validateBasicAuthentication(roles, user);
        } else {
            this.validateTokenAuthentication(roles, user);
        }
    }

    /**
     * Validate basic authentication.
     *
     * @param roles Valid roles.
     * @param user  User to validate.
     */
    public void validateBasicAuthentication(final String[] roles, final User user) {
        if (!Constants.DEFAULT_ADMIN_LOGIN.equals(user.getLogin())) {
            throw authenticationError;
        }
        if (!userService.isDefaultAdministratorAllowed(user.getPassword())) {
            throw authenticationError;
        }
    }

    /**
     * Validate token authentication.
     *
     * @param roles     Valid roles.
     * @param userToken User with token.
     */
    public void validateTokenAuthentication(final String[] roles, final User userToken) {
        if (!ArrayUtils.contains(roles, UserRoleType.TOKEN)) {
            throw new GlobalException(ErrorType.AUTHORIZATION_ERROR, "Token are not allowed.");
        }

        User user = userService.getUserFromToken(userToken.getPassword());
        if (user == null || !Constants.DEFAULT_ADMIN_LOGIN.equals(user.getLogin())) {
            throw authenticationError;
        }
    }
}
