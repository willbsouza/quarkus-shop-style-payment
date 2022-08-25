package br.com.compass.mspayment.repository;

import br.com.compass.mspayment.domain.model.Installment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InstallmentRepository implements PanacheRepository<Installment> {
}
