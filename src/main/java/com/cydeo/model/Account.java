package com.cydeo.model;

import com.cydeo.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder    // with @Builder, we don't need @AllArgsConstructor. It gives custom constructor whichever we need
public class Account {
    private UUID id;
    private BigDecimal balance;
    private AccountType accountType;
    private Date creationDate;
    private Long userId;
}
