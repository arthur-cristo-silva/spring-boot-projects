package com.arthur.adi.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@AllArgsConstructor
public class UserNotFoundException extends AdiException {

    private String id;

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setTitle("User not found.");
        pb.setDetail("The user of id: " + id + " was not found.");
        return pb;
    }
}
