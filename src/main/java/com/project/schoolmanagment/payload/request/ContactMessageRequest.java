package com.project.schoolmanagment.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageRequest implements Serializable {
//TODO: 1:40:00 from second video of the backend project
    private String name;

    private String email;

    private String subject;

    private String message;

}
