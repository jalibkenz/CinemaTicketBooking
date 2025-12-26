package in.kenz.cinematicketbooking.paymentpolicy.repository;

import in.kenz.cinematicketbooking.paymentpolicy.entity.PaymentPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentPolicyRepository extends JpaRepository<PaymentPolicy, Long> {
}
