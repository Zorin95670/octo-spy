package com.octo.security;

import com.octo.model.common.Constants;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.User;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;

/**
 * Retrieve user from request context.
 *
 * @author Vincent Moittie
 */
public class UserMapper implements Function<ContainerRequestContext, User> {

    @Override
    public final User apply(final ContainerRequestContext requestContext) {
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        final List<String> authorization = headers.get(Constants.AUTHORIZATION_PROPERTY);

        if (authorization == null || authorization.isEmpty()) {
            return null;
        }
        String encodedAuthentication = authorization.get(0);

        String authenticationScheme;
        if (StringUtils.startsWith(encodedAuthentication, Constants.AUTHENTICATION_BASIC_SCHEME)) {
            authenticationScheme = Constants.AUTHENTICATION_BASIC_SCHEME;
        } else if (StringUtils.startsWith(encodedAuthentication, Constants.AUTHENTICATION_TOKEN_SCHEME)) {
            authenticationScheme = Constants.AUTHENTICATION_TOKEN_SCHEME;
        } else {
            throw new GlobalException(ErrorType.AUTHORIZATION_ERROR, "authentication scheme");
        }

        encodedAuthentication = encodedAuthentication.replaceFirst(authenticationScheme + " ", "");
        String authentication = new String(Base64.getUrlDecoder().decode(encodedAuthentication.getBytes()));

        User user = new User();
        if (Constants.AUTHENTICATION_BASIC_SCHEME.equals(authenticationScheme)) {
            final StringTokenizer tokenizer = new StringTokenizer(authentication, ":");
            user.setLogin(tokenizer.nextToken());
            user.setPassword(tokenizer.nextToken());
        } else {
            user.setPassword(authentication);
        }
        user.setAuthenticationType(authenticationScheme);

        return user;
    }

}
