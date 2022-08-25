package br.com.compass.mspayment.resources.dto;

import br.com.compass.mspayment.domain.model.Installment;
import br.com.compass.mspayment.domain.model.Payment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InstallmentDto {
    private Long id;
    private Integer amount;
    private String brand;
    private Payment payment;

    public InstallmentDto(Installment installment) {
        this.id = installment.getId();
        this.amount = installment.getAmount();
        this.brand = installment.getBrand();
        this.payment = installment.getPayment();
    }
}
