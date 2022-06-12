package org.przemekrutkowski.exammarksapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class UserDto {

    private String userName;
    private String firstName;
    private String lastName;
    private String role;

    public static UserDto of(final String userName) {
        return UserDto.of(userName, null, null, null);
    }
}
