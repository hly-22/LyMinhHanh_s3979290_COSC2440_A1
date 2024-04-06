/**
 * @author <Ly Minh Hanh - s3979290>
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Claim {
    private String fID;
    private LocalDate claimDate;
    private String insuredPerson;
    private InsuranceCard cardNumber;
    private LocalDate examDate;
    private List<String> documentList;
    private BigDecimal claimAmount;
    private ClaimStatus status;
    private String receiverBankingInfo;

    public Claim(String fID, InsuranceCard cardNumber, LocalDate examDate, BigDecimal claimAmount) {
        this.fID = fID;
        this.claimDate = LocalDate.now();
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documentList = new ArrayList<>();
        this.claimAmount = claimAmount;
        this.status = ClaimStatus.NEW;
        this.receiverBankingInfo = null;
        setInsuredPerson(cardNumber.getCardHolder());
    }

    public Claim(String fID, LocalDate claimDate, String insuredPerson, LocalDate examDate, List<String> documentList, BigDecimal claimAmount, ClaimStatus status, String receiverBankingInfo) {
        this.fID = fID;
        this.claimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.examDate = examDate;
        this.documentList = documentList;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingInfo = receiverBankingInfo;
        this.cardNumber = null;
    }

    public boolean addDocument(String documentName) {
        String claimID = getFID();
        String cardNumber = getCardNumber().getCardNumber();
        String fullDocument = claimID + "_" + cardNumber + "_" + documentName + ".pdf";

        return documentList.add(fullDocument);
    }

    public void setCardNumber(InsuranceCard cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setReceiverBankingInfo(String bank, String name, String number) {
        this.receiverBankingInfo = bank + " - " + name + " - " + number;;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
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

    public String getInsuredPerson() {
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Claim other = (Claim) o;
        return Objects.equals(this.getFID(), other.getFID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFID());
    }
}

enum ClaimStatus {
    NEW,
    PROCESSING,
    DONE
}