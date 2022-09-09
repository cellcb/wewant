package me.cell.wewant.core.mail;

public class EmailAuthInfo {
    private String userCount;//用户账户
    private String authCode;//授权码
    private String smtpAdr;//smtp地址
    private String sendTo;//收件人

    EmailAuthInfo() {
        this.userCount = "chengbo851027@163.com";
        this.smtpAdr = "smtp.163.com";
        this.authCode = "WNXQYEWWIQLYBVHD";
        this.sendTo = "chengbo.tr@outlook.com";
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getSmtpAdr() {
        return smtpAdr;
    }

    public void setSmtpAdr(String smtpAdr) {
        this.smtpAdr = smtpAdr;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}

