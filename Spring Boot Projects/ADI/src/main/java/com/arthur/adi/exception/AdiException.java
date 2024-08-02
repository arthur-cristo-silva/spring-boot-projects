package com.arthur.adi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AdiException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("ADI internal server error.");
        return pb;
    }
}
