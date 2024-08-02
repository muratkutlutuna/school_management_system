package com.project.schoolmanagment.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Please enter name")
    @Size(min = 4,max = 16,message = "Your name should be at least 4 characters")
    @Pattern(regexp = "\\A(?!\\s*\\z).+",message = "Your message must consist of the character .")
    private String name;

    @Email(message = "Please enter valid email")
    @Size(min = 5,max = 20, message = "Your email should be at least 5 characters")
    @NotNull(message = "Please enter your email")
    //column annotation should be in entity class for DB validation
    @Column(nullable = false,unique = true,length = 20)
    private String email;

    @Email(message = "Please enter valid subject")
    @Size(min = 4,max = 50, message = "Your email should be at least 4 characters")
    @Pattern(regexp = "\\A(?!\\s*\\z).+",message = "Your message must consist of the character .")
    private String subject;

    @Email(message = "Please enter valid message")
    @Size(min = 16,max = 200, message = "Your email should be at least 16 characters")
    @Pattern(regexp = "\\A(?!\\s*\\z).+",message = "Your message must consist of the character .")
    private String message;

}
