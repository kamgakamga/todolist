package com.if5.todolist.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class ApiResponse {
    boolean success;
    String message;
    Date timestamp;
    Object data;

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;

        this.data = data;
        this.timestamp = new Date();
    }

    public ApiResponse(boolean success, String message, Date timestamp, Object data) {
        this.success = success;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
    }

    public static final class ApiResponseBuilder {
        boolean success;
        String message;
        Object data;

        private ApiResponseBuilder() {
        }

        public static ApiResponseBuilder anApiResponse() {
            return new ApiResponseBuilder();
        }

        public ApiResponseBuilder withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder withData(Object data) {
            this.data = data;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(success, message, data);
        }
    }
}
