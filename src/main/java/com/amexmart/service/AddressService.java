package com.amexmart.service;

import com.amexmart.dto.AddressDto;

import java.util.List;

public interface AddressService {

    AddressDto addAddress(AddressDto dto);

    List<AddressDto> getMyAddresses();

    AddressDto updateAddress(Long id, AddressDto dto);

    void deleteAddress(Long id);

    AddressDto setDefaultAddress(Long id);
}
