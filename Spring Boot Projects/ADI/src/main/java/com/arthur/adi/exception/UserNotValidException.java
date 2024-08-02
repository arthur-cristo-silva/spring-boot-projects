package com.arthur.adi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserNotValidException extends AdiException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("User not valid.");
        pb.setDetail("Please give valid attributes to create user.");
        return pb;
    }
}
