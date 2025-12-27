package in.kenz.cinematicketbooking.payment.application.port.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderPaymentResult {

    /**
     * Provider identifier
     */
    private String provider;

    /**
     * Provider payment ID
     */
    private String gatewayPaymentId;

    /**
     * Provider order / intent ID
     */
    private String gatewayOrderId;

    /**
     * Raw provider status (e.g. captured, failed, authorized)
     */
    private String providerStatus;

    /**
     * Raw provider response (JSON string)
     */
    private String rawResponse;
}