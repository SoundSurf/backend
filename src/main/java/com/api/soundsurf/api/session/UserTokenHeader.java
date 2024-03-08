package com.api.soundsurf.api.session;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@EqualsAndHashCode(of = "token")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenHeader {
    public static final String HEADER_KEY = "token";

    private String token;
}
