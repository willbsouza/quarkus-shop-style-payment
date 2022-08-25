package br.com.compass.mspayment.resources.dto;

import br.com.compass.mspayment.domain.model.Payment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentDto {
    private Long id;
    private String type;
    private Boolean active;
    private Boolean installments;

    public PaymentDto(Payment payment) {
        this.id = payment.getId();
        this.type = payment.getType();
        this.active = payment.getActive();
        this.installments = payment.getInstallments();
    }
}
