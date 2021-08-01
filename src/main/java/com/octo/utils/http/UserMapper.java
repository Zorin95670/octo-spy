package com.octo.utils.http;

import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.octo.model.entity.User;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.Constants;

/**
 * Retrieve user from request context.
 *
 * @author Vincent Moittie
 *
 */
public class UserMapper implements Function<ContainerRequestContext, User> {

    @Override
    public final User apply(final ContainerRequestContext requestContext) {
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        final List<String> authorization = headers.get(Constants.AUTHORIZATION_PROPERTY);

        if (authorization == null || authorization.isEmpty()) {
            throw new GlobalException(ErrorType.AUTHORIZATION_ERROR, Constants.AUTHORIZATION_PROPERTY);
        }
        String encodedAuthentication = authorization.get(0);

        if (!StringUtils.startsWith(encodedAuthentication, Constants.AUTHENTICATION_SCHEME)) {
            throw new GlobalException(ErrorType.AUTHORIZATION_ERROR, "authentication scheme");
        }

        encodedAuthentication = encodedAuthentication.replaceFirst(Constants.AUTHENTICATION_SCHEME + " ", "");
        String authentication = new String(Base64.getDecoder().decode(encodedAuthentication.getBytes()));

        final StringTokenizer tokenizer = new StringTokenizer(authentication, ":");

        User user = new User();
        user.setLogin(tokenizer.nextToken());
        user.setPassword(tokenizer.nextToken());

        return user;
    }

}
