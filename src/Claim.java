/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private static int nextFID = 1;

    public Claim(InsuranceCard cardNumber, LocalDate examDate, BigDecimal claimAmount) {
        this.fID = generateFID();
        this.claimDate = LocalDate.now();
        setInsuredPerson();
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documentList = new ArrayList<>();
        this.claimAmount = claimAmount;
        this.status = ClaimStatus.NEW;
        this.receiverBankingInfo = null;
    }

    protected synchronized String generateFID() {
        String FID = "f-" + String.format("%010d", nextFID);
        nextFID++;
        return FID;
    }
    public boolean addDocument(String documentName) {
        String claimID = getFID();
        String cardNumber = getCardNumber().getCardNumber();
        String fullDocument = claimID + "_" + cardNumber + "_" + documentName + ".pdf";

        return documentList.add(fullDocument);
    }

    public void setReceiverBankingInfo(String bank, String name, String number) {
        this.receiverBankingInfo = bank + " - " + name + " - " + number;;
    }

    public void setInsuredPerson() {
        this.insuredPerson = cardNumber.getCardHolder();
    }

    public void setCardNumber(InsuranceCard cardNumber) {
        this.cardNumber = cardNumber;
        setInsuredPerson();
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }


    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public String getFID() {
        return fID;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public Customer getInsuredPerson() {
        return insuredPerson;
    }

    public InsuranceCard getCardNumber() {
        return cardNumber;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public List<String> getDocumentList() {
        return documentList;
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public String getReceiverBankingInfo() {
        return receiverBankingInfo;
    }
}

enum ClaimStatus {
    NEW,
    PROCESSING,
    DONE
}