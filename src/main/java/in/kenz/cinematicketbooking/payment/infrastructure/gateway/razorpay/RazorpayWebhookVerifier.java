package in.kenz.cinematicketbooking.payment.infrastructure.gateway.razorpay;

import org.springframework.stereotype.Component;

@Component
public class RazorpayWebhookVerifier {

    public void verify(String payload, String signature, String secret) {
        if (signature == null) {
            throw new IllegalArgumentException("Invalid Razorpay signature");
        }
    }
}