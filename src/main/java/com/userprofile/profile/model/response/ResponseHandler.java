package com.userprofile.profile.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.userprofile.profile.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Component
public class ResponseHandler<T> {

    private static final Logger log = LoggerFactory.getLogger(ResponseHandler.class);
    @JsonProperty("Status")
    public Status Status;
    @JsonProperty("Response")
    public T Response;

    public ResponseHandler(Status status) {
        this.Status = status;
    }

    public ResponseHandler generateFailResponse(String error) {
        Status status = new Status("FAILED", HttpStatus.INTERNAL_SERVER_ERROR.value(), Instant.now().toEpochMilli(), error);
        return new ResponseHandler(status, (Object)null);
    }

    public ResponseHandler generateFailResponse(int statusCode, String error) {
        Status status = new Status("FAILED", statusCode, Instant.now().toEpochMilli(), error);
        return new ResponseHandler(status, (Object)null);
    }

    public ResponseHandler generateSuccessResponse(Object data) {
        Status status = new Status("OK", HttpStatus.OK.value(), Instant.now().toEpochMilli(), (List)null);
        return new ResponseHandler(status, data);
    }

    public ResponseHandler(Status Status, T Response) {
        this.Status = Status;
        this.Response = Response;
    }

    public ResponseHandler() {
    }
}
