package com.kesmarki.homework.controller.request;

import com.kesmarki.homework.model.AddressType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {

    private AddressType type;

    private String address;
}
