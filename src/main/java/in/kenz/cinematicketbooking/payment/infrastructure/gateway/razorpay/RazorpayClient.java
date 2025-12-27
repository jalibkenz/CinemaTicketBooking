package in.kenz.cinematicketbooking.payment.infrastructure.gateway.razorpay;

import org.springframework.stereotype.Component;

@Component
public class RazorpayClient {

    public String createOrder(
            Long amountInPaise,
            String currency,
            String receipt
    ) {
        // Call Razorpay Orders API with receipt
        return "rzp_order_dummy";
    }

    public String fetchPayment(String paymentId) {
        // Call Razorpay Payments API
        return "{ razorpay_raw_json }";
    }
}