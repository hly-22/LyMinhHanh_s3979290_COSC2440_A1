import java.time.LocalDate;

/**
 * @author <Ly Minh Hanh - s3979290>
 */

public class InsuranceCardViewText {
    public void view(InsuranceCard insuranceCard) {
        System.out.println();
        System.out.println("------------------------");
        System.out.println("Customer " + insuranceCard.getCardHolder().getFullName() + " Insurance Card");
        System.out.println();
        System.out.println("card number: " + insuranceCard.getCardNumber());
        System.out.println("policy owner: " + insuranceCard.getPolicyOwner());
        System.out.println("expiration date: " + insuranceCard.getExpirationDate());
        System.out.println("------------------------");
        System.out.println();
    }
}
