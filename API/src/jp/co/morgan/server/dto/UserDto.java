package jp.co.morgan.server.dto;

import java.sql.Timestamp;

public class UserDto {
    private String userId;
    private String userName;
    private String eMail;
    private Timestamp created;
    private Timestamp updated;
    private boolean deleteFlag;

    // コンストラクタ
    public UserDto() {
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

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public boolean getDeleteFlag() {
        return deleteFlag;
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

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}