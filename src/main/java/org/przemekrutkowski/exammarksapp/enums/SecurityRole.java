package org.przemekrutkowski.exammarksapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum SecurityRole {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_GUEST("GUEST");

    private final String shortName;

    public String getFullName() {
        return "ROLE_%s".formatted(shortName);
    }

    public static List<String> roles() {
        return Stream.of(SecurityRole.values())
                .map(SecurityRole::getShortName)
                .collect(Collectors.toList());
    }
}
