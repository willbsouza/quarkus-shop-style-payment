package br.com.compass.mspayment.domain.model;

import br.com.compass.mspayment.resources.dto.InstallmentFormDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer amount;

    private String brand;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;

    public Installment(InstallmentFormDto installmentFormDto, Payment payment) {
        this.amount = installmentFormDto.getAmount();
        this.brand = installmentFormDto.getBrand();
        this.payment = payment;
    }
}
