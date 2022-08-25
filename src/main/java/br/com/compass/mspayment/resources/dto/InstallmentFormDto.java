package br.com.compass.mspayment.resources.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class InstallmentFormDto {

    @NotNull
    private Integer amount;

    private String brand;

    @NotNull
    private Long paymentId;
}
