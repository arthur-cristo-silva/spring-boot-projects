package com.arthur.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletDataAlredyExistsException extends PicPayException{

    private String detail = "This CPF, CNPJ or E-mail is already being used";

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Wallet data already exists");
        pb.setDetail(detail);
        return pb;
    }
}
