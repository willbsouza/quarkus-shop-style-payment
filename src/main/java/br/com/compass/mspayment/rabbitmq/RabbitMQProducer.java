package br.com.compass.mspayment.rabbitmq;

import br.com.compass.mspayment.rabbitmq.model.PaymentOrderStatus;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RabbitMQProducer {

    @Channel("payment-order")
    Emitter<JsonObject> emitterPaymentOrder;

    public void publishMessage(PaymentOrderStatus message){
        emitterPaymentOrder.send(JsonObject.mapFrom(message));
    }
}
