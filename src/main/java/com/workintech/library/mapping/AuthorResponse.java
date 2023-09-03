package com.workintech.library.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private int authorId;
    private String firstName;
    private String lastName;
    private List<BookResponse> bookResponses;

}
