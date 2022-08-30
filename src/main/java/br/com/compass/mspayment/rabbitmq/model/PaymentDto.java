package br.com.compass.mspayment.rabbitmq.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaymentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer installments;
}