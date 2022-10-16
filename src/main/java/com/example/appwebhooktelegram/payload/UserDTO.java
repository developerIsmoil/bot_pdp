package com.example.appwebhooktelegram.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String photoId;
    private String patron;
    private List<String> permissions;
    private boolean enabled;
    private Timestamp createdAt;
    private Date birthDate;
    private boolean isAdmin;

    public UserDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
