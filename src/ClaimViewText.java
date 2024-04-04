import java.util.List;

/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class ClaimViewText {
    public void view(Claim claim) {
        System.out.println();
        System.out.println("------------------------");
        System.out.println("Claim Information");
        System.out.println();
        System.out.println("fID: " + claim.getFID());
        System.out.println("claim date: " + claim.getClaimDate());
        System.out.println("insured person: " + claim.getInsuredPerson());
        System.out.println("insurance card number: " + claim.getCardNumber());
        System.out.println("exam date: " + claim.getExamDate());
        System.out.println("document list: " + claim.getDocumentList());
        System.out.println("claim amount: " + claim.getClaimAmount());
        System.out.println("status: " + claim.getStatus());
        System.out.println("receiver banking information: " + claim.getReceiverBankingInfo());
        System.out.println("------------------------");
        System.out.println();
    }
}
