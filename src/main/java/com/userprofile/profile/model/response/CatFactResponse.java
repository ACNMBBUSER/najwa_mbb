package com.userprofile.profile.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatFactResponse {

    @JsonProperty("fact")
    private String fact;

    @JsonProperty("length")
    private int length;

}
