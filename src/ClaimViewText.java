import java.util.List;

/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class ClaimViewText {
    public void view(Claim claim) {
        System.out.println();
        System.out.println("============================");
        System.out.println("FID: " + claim.getFID());
        System.out.println("Claim date: " + claim.getClaimDate());
        System.out.println("Insured person: " + claim.getInsuredPerson());
        System.out.println("Insurance card: " + claim.getCardNumber());
        System.out.println("Exam date: " + claim.getExamDate());
        System.out.println("Document list: " + claim.getDocumentList());
        System.out.println("Claim amount: " + claim.getClaimAmount());
        System.out.println("Status: " + claim.getStatus());
        System.out.println("Receiver banking information: " + claim.getReceiverBankingInfo());
        System.out.println("============================");
        System.out.println();
    }
}
