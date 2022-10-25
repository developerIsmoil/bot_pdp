package com.example.appwebhooktelegram.results;

import com.example.appwebhooktelegram.payload.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResultDTO<T> implements Serializable {
    private boolean success = false;
    private List<UserDTO> data;
}
