package in.kenz.cinematicketbooking.payment.application.port;


import in.kenz.cinematicketbooking.payment.application.port.dto.PaymentOrderResponse;
import in.kenz.cinematicketbooking.payment.application.port.dto.ProviderPaymentResult;
import in.kenz.cinematicketbooking.payment.application.port.dto.WebhookPayload;
import in.kenz.cinematicketbooking.payment.domain.entity.Payment;

public interface PaymentProvider {

    /* ========================
       PROVIDER IDENTITY
       ======================== */

    /**
     * Canonical provider name.
     * Example: RAZORPAY, STRIPE, CASHFREE, BANK, CASH
     */
    String getProviderName();

    /* ========================
       PAYMENT CREATION
       ======================== */

    /**
     * Creates a payment attempt at the provider.
     * Maps to:
     * - Razorpay Order
     * - Stripe PaymentIntent
     * - Bank transfer reference
     */
    PaymentOrderResponse createOrder(Payment payment);

    /* ========================
       WEBHOOK VERIFICATION
       ======================== */

    /**
     * Verifies webhook authenticity.
     * Must throw exception if verification fails.
     */
    void verifyWebhook(WebhookPayload payload);

    /* ========================
       STATUS FETCH / RECONCILIATION
       ======================== */

    /**
     * Fetches latest payment status from provider.
     * Used for polling, reconciliation, or manual checks.
     */
    ProviderPaymentResult fetchPaymentStatus(String gatewayPaymentId);
}