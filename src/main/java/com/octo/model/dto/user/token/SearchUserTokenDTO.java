package com.octo.model.dto.user.token;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.utils.predicate.filter.FilterType.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO to search user token.
 */
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class SearchUserTokenDTO extends QueryFilter {
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
}
