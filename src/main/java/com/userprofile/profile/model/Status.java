package com.userprofile.profile.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Status {

    @JsonProperty("Type")
    public String Type;
    @JsonProperty("Code")
    public int Code;
    @JsonProperty("Message")
    public String Message;
    @JsonProperty("Timestamp")
    public long Timestamp;
    @JsonProperty("Errors")
    public List<String> Errors;

    public Status(String type, int code, long timestamp, String error) {
        this.Type = type;
        this.Code = code;
        this.Timestamp = timestamp;
        this.Errors = new ArrayList();
        this.Errors.add(error);
    }

    public Status(String type, int code, long timestamp, List<String> errors) {
        this.Type = type;
        this.Code = code;
        this.Timestamp = timestamp;
        this.Errors = errors;
    }

    public Status(String Type, int Code, String Message, long Timestamp, List<String> Errors) {
        this.Type = Type;
        this.Code = Code;
        this.Message = Message;
        this.Timestamp = Timestamp;
        this.Errors = Errors;
    }

    public Status() {
    }
}
