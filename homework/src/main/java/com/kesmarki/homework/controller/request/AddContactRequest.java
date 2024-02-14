package com.kesmarki.homework.controller.request;

import com.kesmarki.homework.model.ContactType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddContactRequest {
    private String Address;
    private ContactType type;
    private String contactInfo;
}
