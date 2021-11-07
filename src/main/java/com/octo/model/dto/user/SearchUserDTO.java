package com.octo.model.dto.user;

import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.predicate.filter.QueryFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO to search user.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public @Data class SearchUserDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @FilterType(type = Type.NUMBER)
    private String id;
    /**
     * Type of authentication.
     */
    @FilterType(type = Type.TEXT)
    private String authenticationType;
    /**
     * Login.
     */
    @FilterType(type = Type.TEXT)
    private String login;
    /**
     * First name.
     */
    @FilterType(type = Type.TEXT)
    private String firstname;
    /**
     * Last name.
     */
    @FilterType(type = Type.TEXT)
    private String lastname;
    /**
     * Email.
     */
    @FilterType(type = Type.TEXT)
    private String email;
    /**
     * Active.
     */
    @FilterType(type = Type.BOOLEAN)
    private String active;
}
