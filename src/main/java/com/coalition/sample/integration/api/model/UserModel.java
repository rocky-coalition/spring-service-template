package com.coalition.sample.integration.api.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String email;
    private String picture;
    private String firstName;
    private String lastName;
    private String name;
}
