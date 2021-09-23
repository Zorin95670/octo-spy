package com.octo.model.dto.user.token;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.utils.predicate.filter.FilterType.Type;

/**
 * DTO to search user token.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchUserTokenDTO extends QueryFilter {
    /**
     * Token.
     */
    @QueryParam("token")
    @FilterType(type = Type.TOKEN)
    private String token;
    /**
     * User id.
     */
    @QueryParam("user")
    @FilterType(type = Type.NUMBER)
    private String user;
    /**
     * Token name.
     */
    @QueryParam("name")
    @FilterType(type = Type.TEXT)
    private String name;

    /**
     * Default constructor.
     *
     * @param token
     *            User token.
     */
    public SearchUserTokenDTO(final String token) {
        this.setToken(token);
    }

    /**
     * Default constructor.
     */
    public SearchUserTokenDTO() {
        this(null);
    }

    /**
     * Get token.
     *
     * @return Token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Set token.
     *
     * @param token
     *            Token.
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * Get user.
     *
     * @return User.
     */
    public String getUser() {
        return user;
    }

    /**
     * Set user.
     *
     * @param user
     *            User.
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     * Get name.
     *
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     *
     * @param name
     *            Name.
     */
    public void setName(final String name) {
        this.name = name;
    }
}
