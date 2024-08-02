package com.arthur.adi.service;

import com.arthur.adi.repository.BillingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingAddressService {

    private final BillingAddressRepository billingAddressRepository;

}
