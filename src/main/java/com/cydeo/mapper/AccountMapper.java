package com.cydeo.mapper;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /*we need two things:
    if there is a DTO, convert to entity
    if there is an entity, convert to DTO
     */

    public AccountDTO convertToDTO(Account entity){
        //this method will accept Account entity and will convert it to DTO
        return modelMapper.map(entity,AccountDTO.class);
    }

    public Account convertToEntity(AccountDTO dto){
        return modelMapper.map(dto, Account.class);
    }
}
