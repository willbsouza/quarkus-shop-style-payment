package br.com.compass.mspayment.resources;

import br.com.compass.mspayment.domain.model.Installment;
import br.com.compass.mspayment.domain.model.Payment;
import br.com.compass.mspayment.repository.InstallmentRepository;
import br.com.compass.mspayment.repository.PaymentRepository;
import br.com.compass.mspayment.resources.dto.InstallmentDto;
import br.com.compass.mspayment.resources.dto.InstallmentFormDto;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/installments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class InstallmentResource {

    private InstallmentRepository installmentRepository;
    private PaymentRepository paymentRepository;

    @Inject
    public InstallmentResource(InstallmentRepository installmentRepository, PaymentRepository paymentRepository){
        this.installmentRepository = installmentRepository;
        this.paymentRepository = paymentRepository;
    }

    @POST
    public Response create(@Valid InstallmentFormDto installmentFormDto){
        Payment payment = paymentRepository.findById(installmentFormDto.getPaymentId());
        if (payment == null){
            return Response.status(404).entity("Payment id: " + installmentFormDto.getPaymentId() + " not found.").build();
        }
        if (!installmentValidation(installmentFormDto, payment)){
            return Response.status(404).entity("Invalid payment form!").build();
        }

        Installment installment = new Installment(installmentFormDto, payment);
        installmentRepository.persist(installment);
        return Response.status(201).entity(new InstallmentDto(installment)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@PathParam("id") Long id, @Valid InstallmentFormDto installmentFormDto){
        Installment installment = installmentRepository.findById(id);
        if (installment == null){
            return Response.status(404).entity("Installment id: " + id + " not found.").build();
        }
        Payment payment = paymentRepository.findById(installmentFormDto.getPaymentId());
        if (payment == null){
            return Response.status(404).entity("Payment id: " + installmentFormDto.getPaymentId() + " not found.").build();
        }
        if (!installmentValidation(installmentFormDto, payment)){
            return Response.status(404).entity("Invalid payment form!").build();
        }
        installment.setAmount(installmentFormDto.getAmount());
        installment.setBrand(installmentFormDto.getBrand());
        installment.setPayment(payment);
        return Response.ok(new InstallmentDto(installment)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id){
        Installment installment = installmentRepository.findById(id);
        if (installment == null){
            return Response.status(404).entity("Installment id: " + id + " not found.").build();
        }
        installmentRepository.deleteById(id);
        return Response.noContent().build();
    }

    private Boolean installmentValidation(InstallmentFormDto installmentFormDto, Payment payment) {
        if(!payment.getActive()) {
            return false;
        }
        if(!payment.getInstallments() && installmentFormDto.getAmount() > 0) {
            return false;
        }
        if(installmentFormDto.getAmount() < 0) {
            return false;
        }
        return true;
    }
}
