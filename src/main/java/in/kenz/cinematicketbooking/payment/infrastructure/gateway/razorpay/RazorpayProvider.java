package in.kenz.cinematicketbooking.payment.infrastructure.gateway.razorpay;

import in.kenz.cinematicketbooking.payment.application.port.PaymentProvider;
import in.kenz.cinematicketbooking.payment.application.port.dto.PaymentOrderResponse;
import in.kenz.cinematicketbooking.payment.application.port.dto.ProviderPaymentResult;
import in.kenz.cinematicketbooking.payment.application.port.dto.WebhookPayload;
import in.kenz.cinematicketbooking.payment.domain.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("RAZORPAY")
@RequiredArgsConstructor
public class RazorpayProvider implements PaymentProvider {

    /* ========================
       INFRA DEPENDENCIES
       ======================== */

    private final RazorpayClient razorpayClient;
    private final RazorpayConfig razorpayConfig;
    private final RazorpayWebhookVerifier webhookVerifier;

    /* ========================
       PROVIDER IDENTITY
       ======================== */

    @Override
    public String getProviderName() {
        return "RAZORPAY";
    }

    /* ========================
       CREATE PAYMENT ATTEMPT
       ======================== */

    @Override
    public PaymentOrderResponse createOrder(Payment payment) {

        /*
         * Razorpay expects amount in minor units (paise)
         * Conversion responsibility stays here (infra)
         */
        long amountInPaise = payment.getAmount().longValue() * 100;

        String orderId = razorpayClient.createOrder(
                amountInPaise,
                payment.getCurrency(),
                payment.getId().toString() // receipt / reference
        );

        return PaymentOrderResponse.builder()
                .provider(getProviderName())
                .gatewayOrderId(orderId)
                .amount(amountInPaise)
                .currency(payment.getCurrency())
                .metadata(Map.of(
                        "key", razorpayConfig.getKeyId(),
                        "order_id", orderId
                ))
                .build();
    }

    /* ========================
       WEBHOOK VERIFICATION
       ======================== */

    @Override
    public void verifyWebhook(WebhookPayload payload) {

        String signature = payload.getHeaders()
                .getOrDefault("X-Razorpay-Signature", null);

        webhookVerifier.verify(
                payload.getPayload(),
                signature,
                razorpayConfig.getWebhookSecret()
        );
    }

    /* ========================
       FETCH PAYMENT STATUS
       ======================== */

    @Override
    public ProviderPaymentResult fetchPaymentStatus(String gatewayPaymentId) {

        String rawResponse = razorpayClient.fetchPayment(gatewayPaymentId);

        /*
         * No interpretation here.
         * Raw provider data only.
         */
        return ProviderPaymentResult.builder()
                .provider(getProviderName())
                .gatewayPaymentId(gatewayPaymentId)
                .providerStatus("UNKNOWN") // parsed later in app layer
                .rawResponse(rawResponse)
                .build();
    }
}