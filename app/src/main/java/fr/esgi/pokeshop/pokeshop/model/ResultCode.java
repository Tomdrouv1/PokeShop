package fr.esgi.pokeshop.pokeshop.model;


public enum ResultCode {

    OK(0, "OK"),
    MISSING_PARAMS(1, "Missing required parameter(s)"),
    EMAIL_EXISTS(2, "Email already registered"),
    LOGIN_FAILED(3, "Login failed check your credentials"),
    INVALID_TOKEN(4, "Invalid token"),
    INTERNAL_ERROR(5, "Internal server error"),
    UNAUTHORIZED(6, "Unauthorized action"),
    NOT_UPDATE(7, "Nothing to update");

    ResultCode(Integer code, String libelle) {};
}
