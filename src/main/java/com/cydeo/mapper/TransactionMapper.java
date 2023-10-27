package com.cydeo.mapper;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /*we need two things:
    if there is a DTO, convert to entity
    if there is an entity, convert to DTO
     */

    public TransactionDTO convertToDTO(Transaction entity){
        //this method will accept Account entity and will convert it to DTO
        return modelMapper.map(entity,TransactionDTO.class);
    }

    public Transaction convertToEntity(TransactionDTO dto){
        return modelMapper.map(dto, Transaction.class);
    }
}
