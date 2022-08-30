package br.com.compass.mspayment.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentOrderStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private Status status;
}
