package br.com.compass.mspayment.rabbitmq;

import br.com.compass.mspayment.domain.model.Installment;
import br.com.compass.mspayment.domain.model.Payment;
import br.com.compass.mspayment.rabbitmq.model.PaymentOrder;
import br.com.compass.mspayment.rabbitmq.model.PaymentOrderStatus;
import br.com.compass.mspayment.rabbitmq.model.Status;
import br.com.compass.mspayment.repository.InstallmentRepository;
import br.com.compass.mspayment.repository.PaymentRepository;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class RabbitMQConsumer {

    @Inject
    private RabbitMQProducer rabbitMQProducer;

    private PaymentRepository paymentRepository;
    private InstallmentRepository installmentRepository;

    @Inject
    public RabbitMQConsumer(PaymentRepository paymentRepository, InstallmentRepository installmentRepository){
        this.paymentRepository = paymentRepository;
        this.installmentRepository = installmentRepository;
    }

    @Incoming("order-payment")
    @Transactional
    public void processMessage(JsonObject msg){
        PaymentOrder paymentOrder = msg.mapTo(PaymentOrder.class);
        Status status = statusCheck(paymentOrder);
        rabbitMQProducer.publishMessage(new PaymentOrderStatus(paymentOrder.getOrderId(), status));
    }

    private Status statusCheck(PaymentOrder paymentOrder) {

        Status status = Status.PROCESSING_PAYMENT;
        Payment payment = paymentRepository.findById(paymentOrder.getPayment().getId());
        Optional<Installment> optInstallment = installmentRepository.findByPayment(payment);
        if(payment != null && optInstallment.isPresent()){
            if(payment != null) {
                if(payment.getActive()) {
                    if (paymentOrder.getPayment().getInstallments() > 0) {
                        if(!payment.getInstallments()) {
                            status = Status.PAYMENT_NOT_INSTALLMENT;
                        }
                        else if (paymentOrder.getPayment().getInstallments() <= optInstallment.get().getAmount()){
                            status = Status.PAYMENT_SUCCESSFUL;
                        }
                        else {
                            status = Status.PAYMENT_AMOUNT_NOT_AVAILABLE;
                        }
                    } else {
                        status = Status.PAYMENT_SUCCESSFUL;
                    }
                } else {
                    status = Status.PAYMENT_INACTIVE;
                }
            } else {
                status = Status.PAYMENT_NOT_FOUND;
            }
        }
        return status;
    }
}

