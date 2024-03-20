/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Claim {
    private String fID;
    private LocalDate claimDate;
    private Customer insuredPerson;
    private InsuranceCard cardNumber;
    private LocalDate examDate;
    private List<String> documentList;
    private BigDecimal claimAmount;
    private ClaimStatus status;
    private String receiverBankingInfo;

}

enum ClaimStatus {
    NEW,
    PROCESSING,
    DONE
}