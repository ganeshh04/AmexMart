package com.amexmart.controller;

import com.amexmart.dto.AddressDto;
import com.amexmart.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> addAddress(@RequestBody AddressDto dto) {
        return ResponseEntity.ok(addressService.addAddress(dto));
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getMyAddresses() {
        return ResponseEntity.ok(addressService.getMyAddresses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id, @RequestBody AddressDto dto) {
        return ResponseEntity.ok(addressService.updateAddress(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/default")
    public ResponseEntity<AddressDto> setDefault(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.setDefaultAddress(id));
    }
}
