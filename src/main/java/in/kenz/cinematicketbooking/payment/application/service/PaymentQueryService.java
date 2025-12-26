package in.kenz.cinematicketbooking.payment.application.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentQueryService {

    boolean isPaymentAllowed(UUID bookingId);

    BigDecimal getAmountDue(UUID bookingId);
}