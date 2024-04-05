/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class InsuranceCardViewText {
    public void view(InsuranceCard insuranceCard) {
        System.out.println();
        System.out.println("============================");
        System.out.println("Card holder: " + insuranceCard.getCardHolder());
        System.out.println("Card number: " + insuranceCard.getCardNumber());
        System.out.println("Policy owner: " + insuranceCard.getPolicyOwner());
        System.out.println("Expiration date: " + insuranceCard.getExpirationDate());
        System.out.println("============================");
        System.out.println();
    }
}
