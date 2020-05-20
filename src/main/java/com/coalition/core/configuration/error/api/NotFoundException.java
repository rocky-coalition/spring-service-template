package com.coalition.core.configuration.error.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested resource was not found.")
public class NotFoundException extends RuntimeException {}
