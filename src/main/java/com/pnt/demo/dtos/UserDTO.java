package com.pnt.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String password;
}
