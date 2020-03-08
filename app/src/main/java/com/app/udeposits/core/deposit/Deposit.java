package com.app.udeposits.core.deposit;

public class Deposit {
    private String bankName;
    private String depositName;
    private String currency;
    private float percent;
    private String duration;
    private String payout;

    public Deposit(String bankName, String depositName, String currency, float percent, String duration, String payout) {
        this.bankName = bankName;
        this.depositName = depositName;
        this.currency = currency;
        this.percent = percent;
        this.duration = duration;
        this.payout = payout;
    }

    public String getBankName() {
        return bankName;
    }

    public String getDepositName() {
        return depositName;
    }

    public String getCurrency() {
        return currency;
    }

    public float getPercent() {
        return percent;
    }

    public String getDuration(){
        return duration;
    }

    public int getDurationInt() {
        return Integer.parseInt(duration.replace(" дней", ""));
    }

    public String getPayout() {
        return payout;
    }
}
