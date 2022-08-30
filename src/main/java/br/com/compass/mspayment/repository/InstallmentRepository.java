package br.com.compass.mspayment.repository;

import br.com.compass.mspayment.domain.model.Installment;
import br.com.compass.mspayment.domain.model.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class InstallmentRepository implements PanacheRepository<Installment> {

    public Optional<Installment> findByPayment(Payment payment) {
        Map<String, Object> params = Parameters.with("payment", payment).map();
        return find("payment = :payment", params).firstResultOptional();
    }
}
