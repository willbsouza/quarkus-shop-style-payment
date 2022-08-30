package br.com.compass.mspayment.resources;

import br.com.compass.mspayment.domain.model.Payment;
import br.com.compass.mspayment.repository.PaymentRepository;
import br.com.compass.mspayment.resources.dto.PaymentDto;
import br.com.compass.mspayment.resources.dto.PaymentFormDto;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/v1/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class PaymentResource {

    private PaymentRepository paymentRepository;

    @Inject
    public PaymentResource(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @GET
    public Response findAll(){
        return Response.ok(paymentRepository.findAll().list().stream().map(PaymentDto::new).collect(Collectors.toList())).build();
    }

    @POST
    public Response create(@Valid PaymentFormDto paymentFormDto){
        Payment payment = new Payment(paymentFormDto);
        paymentRepository.persist(payment);
        return Response.status(201).entity(new PaymentDto(payment)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@PathParam("id") Long id, @Valid PaymentFormDto paymentFormDto){
        Payment payment = paymentRepository.findById(id);
        if (payment == null){
            return Response.status(404).entity("Payment id: " + id + " not found.").build();
        }
        payment.setActive(paymentFormDto.getActive());
        payment.setType(paymentFormDto.getType());
        payment.setInstallments(paymentFormDto.getInstallments());

        return Response.ok(new PaymentDto(payment)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id){
        if (paymentRepository.findById(id) == null){
            return Response.status(404).entity("Payment id: " + id + " not found.").build();
        }
        paymentRepository.deleteById(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/order/{paymentId}")
    public Payment getPayment(@PathParam("paymentId") Long paymentId){
        return paymentRepository.findById(paymentId);
    }
}
