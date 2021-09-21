package com.example.demo.service;


import com.example.demo.dto.AddressDto;
import com.example.demo.dto.UpdateUserDtoIn;
import com.example.demo.dto.UserDtoIn;
import com.example.demo.dto.UserDtoOut;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.error.NotFoundException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDtoOut getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return toDto(user);
    }

    @Override
    public UserDtoOut addUser(UserDtoIn userDtoIn) {
        User user = new User();
        user.setFirstName(userDtoIn.getFirstName());
        user.setLastName(userDtoIn.getLastName());
        user.setAddress(buildAddress(userDtoIn.getAddress()));
        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public UserDtoOut updateUser(Long userId, UpdateUserDtoIn updateUserDtoIn) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Address newAddress = buildAddress(updateUserDtoIn.getAddress());
        user.setAddress(mergeAddresses(user.getAddress(), newAddress));
        user.setFirstName(updateUserDtoIn.getFirstName());
        user.setLastName(updateUserDtoIn.getLastName());
        userRepository.save(user);
        return toDto(user);
    }

    private Address mergeAddresses(Address address, Address newAddress) {
        if (newAddress != null) {
            if (address != null) {
                newAddress.setId(address.getId());
            }
            return newAddress;
        }
        return address;
    }

    private Address buildAddress(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        return address;
    }


    private UserDtoOut toDto(User user) {
        Address address = user.getAddress();
        AddressDto addressDto = null;
        if (address != null) {
            addressDto = new AddressDto(address.getCity(), address.getCountry());
        }
        return new UserDtoOut(user.getId(), user.getFirstName(), user.getLastName(), addressDto);
    }
}
