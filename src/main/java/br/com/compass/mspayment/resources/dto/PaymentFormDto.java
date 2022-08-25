package br.com.compass.mspayment.resources.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PaymentFormDto {

    @NotNull
    @NotEmpty
    private String type;

    @NotNull
    private Boolean active;

    @NotNull
    private Boolean installments;
}
