package com.amexmart.service.impl;

import com.amexmart.dto.AddressDto;
import com.amexmart.model.Address;
import com.amexmart.model.User;
import com.amexmart.repository.AddressRepository;
import com.amexmart.service.AddressService;
import com.amexmart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public AddressDto addAddress(AddressDto dto) {
        User user = userService.getCurrentUser();

        Address address = modelMapper.map(dto, Address.class);
        address.setUser(user);

        // First address should be default
        if (addressRepository.findByUser(user).isEmpty()) {
            address.setDefault(true);
        }

        return modelMapper.map(addressRepository.save(address), AddressDto.class);
    }

    @Override
    public List<AddressDto> getMyAddresses() {
        User user = userService.getCurrentUser();
        return addressRepository.findByUser(user)
                .stream()
                .map(a -> modelMapper.map(a, AddressDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto dto) {
        User user = userService.getCurrentUser();

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setLandmark(dto.getLandmark());

        return modelMapper.map(addressRepository.save(address), AddressDto.class);
    }

    @Override
    public void deleteAddress(Long id) {
        User user = userService.getCurrentUser();

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        addressRepository.delete(address);
    }

    @Override
    public AddressDto setDefaultAddress(Long id) {
        User user = userService.getCurrentUser();

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // Remove old default
        Address oldDefault = addressRepository.findByUserAndIsDefaultTrue(user);
        if (oldDefault != null) {
            oldDefault.setDefault(false);
            addressRepository.save(oldDefault);
        }

        // Set new default
        address.setDefault(true);
        addressRepository.save(address);

        return modelMapper.map(address, AddressDto.class);
    }
}
