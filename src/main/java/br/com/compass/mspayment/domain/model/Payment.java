package br.com.compass.mspayment.domain.model;

import br.com.compass.mspayment.resources.dto.PaymentFormDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String type;

    @NotNull
    private Boolean active;

    @NotNull
    private Boolean installments;

    public Payment(PaymentFormDto paymentFormDto) {
        this.type = paymentFormDto.getType();
        this.active = paymentFormDto.getActive();
        this.installments = paymentFormDto.getInstallments();
    }
}
