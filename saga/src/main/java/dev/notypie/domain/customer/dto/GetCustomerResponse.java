package dev.notypie.domain.customer.dto;

import dev.notypie.messaging.common.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerResponse {
    private Long customerId;
    private String name;
    private Money creditLimit;
}
