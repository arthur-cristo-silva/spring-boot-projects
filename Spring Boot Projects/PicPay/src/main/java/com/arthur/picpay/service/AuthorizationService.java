package com.arthur.picpay.service;

import com.arthur.picpay.client.AuthorizationClient;
import com.arthur.picpay.controller.dto.TransferDto;
import com.arthur.picpay.exception.PicPayException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public boolean isAuthorized(TransferDto transfer) {

        var resp = authorizationClient.isAuthorized();

        if (resp.getStatusCode().isError()) {
            throw new PicPayException();
        }
        return resp.getBody().authorized();
    }

}
