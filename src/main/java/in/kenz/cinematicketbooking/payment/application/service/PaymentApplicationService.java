package in.kenz.cinematicketbooking.payment.application.service;

import in.kenz.cinematicketbooking.payment.application.port.PaymentProvider;
import in.kenz.cinematicketbooking.payment.application.port.dto.*;

import in.kenz.cinematicketbooking.payment.domain.entity.Payment;

import in.kenz.cinematicketbooking.payment.infrastructure.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    private final PaymentRepository paymentRepository;
    private final PaymentProviderRegistry providerRegistry;

    @Transactional
    public PaymentOrderResponse createPayment(Payment payment) {

        Payment saved = paymentRepository.save(payment);
        PaymentProvider provider = providerRegistry.get(saved.getProvider());

        return provider.createOrder(saved);
    }

    @Transactional
    public void handleWebhook(WebhookPayload payload) {

        PaymentProvider provider = providerRegistry.get(payload.getProvider());
        provider.verifyWebhook(payload);

        // parse payload later
        // update Payment + CollectionRequest here
    }
}