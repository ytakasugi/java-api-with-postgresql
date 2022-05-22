package jp.co.morgan.server.dto;

public class UserDto {
    private String userId;
    private String userName;
    private String eMail;
    private String updated;

    // コンストラクタ
    public UserDto() {
        userId = null;
        userName = null;
        eMail = null;
        updated = null;
    }

    /**
     * ゲッター
     * @return
     */
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEMail() {
        return eMail;
    }

    public String getUpdated() {
        return updated;
    }

    /**
     * セッター
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}