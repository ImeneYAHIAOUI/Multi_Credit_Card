package fr.univcotedazur.multiCredit.entities;

public enum AccountStatus {
    EXPIRED("EXPIRED"),
    REGULAR("REGULAR"),
    VFP("VFP");
    private final String accountStatusName;

    AccountStatus(String accountStatusName) {
        this.accountStatusName = accountStatusName;
    }

    public String getAccountStatusName() {
        return accountStatusName;
    }
}
