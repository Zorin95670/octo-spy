package com.octo.models.error;

import javax.ws.rs.core.Response.Status;

/**
 * List of controller exception error.
 */
public enum ErrorType {
    /**
     * Error to call when try to add a lock on a path and an exclusive lock is already present.
     */
    LOCK_EXCLUSIF_PRESENT("Exclusive lock is present.", Status.CONFLICT),
    /**
     * Error to call when try to add an exclusive lock on a path and a shared lock is already present.
     */
    LOCK_SHARED_PRESENT("Shared lock is present.", Status.CONFLICT),
    /**
     * Error to call when trying to update concurrently a resource and sql lock failure count reaches limit of retries.
     */
    SQL_LOCK_LIMIT("SQL lock limit reached.", Status.CONFLICT),
    /**
     * Error to call when Activiti service returns a 5XX status.
     */
    ACTIVITI_UNAVAILABLE("Unable to contact Activiti.", Status.SERVICE_UNAVAILABLE),
    /**
     * Error to call when Activiti service returns a 4XX status.
     */
    ACTIVITI_ERROR("Unable to execute request on Activiti.", Status.BAD_REQUEST),
    /**
     * Error to call when a field value is empty.
     */
    EMPTY_VALUE("Field value is empty.", Status.BAD_REQUEST),
    /**
     * Error to call when field value contains a wrong filter value.
     */
    WRONG_FILTER_VALUE("Field value is wrong.", Status.BAD_REQUEST),
    /**
     * Error to call when operator of wrong filter is wrong.
     */
    WRONG_FILTER_OPERATOR("Field contains a wrong operator.", Status.BAD_REQUEST),
    /**
     * Error to call when someone call setStatus endpoint with warning status.
     */
    WRONG_METHOD_FOR_WARNING(
            "Status warning cannot be set with this endpoint, create error with warning severity instead.",
            Status.BAD_REQUEST),
    /**
     * Error to call when someone try to create a warning error without entry id.
     */
    WARNING_ERROR_WITHOUT_ENTRY("Warning error cannot be created without entry id.", Status.BAD_REQUEST),
    /**
     * Error to call when someone try to create a cleared warning error.
     */
    WARNING_ERROR_CANNOT_BE_CLEARED("Warning error cannot be cleared.", Status.BAD_REQUEST),
    /**
     * Error to call when request order field is wrong.
     */
    WRONG_ORDER("Unknow order field.", Status.BAD_REQUEST),
    /**
     * Error to call when request sort value is wrong.
     */
    WRONG_SORT("Unknow sort value.", Status.BAD_REQUEST),
    /**
     * Error to call when entity is not found in database.
     */
    ENTITY_NOT_FOUND("Entity not found.", Status.NOT_FOUND),
    /**
     * Error to call when there are problems with field.
     */
    WRONG_FIELD("Somethings wrong with this field.", Status.BAD_REQUEST),
    /**
     * Error to call when an internal error occurred.
     */
    INTERNAL_ERROR("Internal error occurred, please contact your administrator.", Status.INTERNAL_SERVER_ERROR),
    /**
     * Error to call when delete is called without a where clause.
     */
    DELETE_WITHOUT_WHERE("Trying to delete without where clause.", Status.INTERNAL_SERVER_ERROR),
    /**
     * Error to call when field is not an attribute of model.
     */
    UNKNOW_FIELD("Field is not an attribute of model.", Status.BAD_REQUEST),
    /**
     * Error to call when field value is wrong.
     */
    WRONG_VALUE("Wrong field value.", Status.BAD_REQUEST),
    /**
     * Error to call when number is wrong.
     */
    WRONG_NUMBER("Wrong number.", Status.BAD_REQUEST),
    /**
     * Error to call when wrong http method is used for http client.
     */
    WRONG_HTTP_METHOD("Internal error occurred, please contact your administrator.", Status.INTERNAL_SERVER_ERROR),
    /**
     * Error to call when the status can not be switched to "Running" due to remaining uncleared errors.
     */
    UNABLE_TO_SET_RUNNNING_STATUS("Unable to set the status to running due to remaining uncleared errors.",
            Status.BAD_REQUEST),
    /**
     * Error to call when you have same value in list.
     */
    DUPLICATE_DATA("You duplicate field value in your data.", Status.BAD_REQUEST),
    /**
     * Error to call when you try to insert multiple values but there is a unique constraints on the field.
     */
    DUPLICATE_DATA_INTEGRITY("Unable to duplicate data, please make an update instead.", Status.BAD_REQUEST),
    /**
     * Error to call when trying to replace a file that is linked to an entry.
     */
    WRONG_STATUS_FOR_FILE("Status replaced can be set only on orphan file.", Status.BAD_REQUEST),
    /**
     * Error to call when trying to delete a file that is linked to an entry.
     */
    UNABLE_TO_DELETE_FILE("Unable to delete non orphan file.", Status.BAD_REQUEST);

    /**
     * Exception message.
     */
    private String message;

    /**
     * Exception status.
     */
    private Status status;

    /**
     * Error type with message.
     *
     * @param message
     *            Error's message.
     * @param status
     *            Error's status.
     */
    ErrorType(final String message, final Status status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Get message error.
     *
     * @return Message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Get status error.
     *
     * @return Status.
     */
    public Status getStatus() {
        return this.status;
    }
}
