package com.kesmarki.homework.controller.response;

import com.kesmarki.homework.model.Address;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponse {

    private Long id;

    private String name;

    private List<Address> addresses = new ArrayList<>();
}
