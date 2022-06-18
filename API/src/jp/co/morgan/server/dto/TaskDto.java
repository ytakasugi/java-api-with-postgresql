package jp.co.morgan.server.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class TaskDto {
    private int taskId;
    private int userId;
    private String content;
    private Timestamp created;
    private Timestamp updated;
    private Date deadLine;
    private String status;

    /**
     * コンストラクタ
     */
    public TaskDto() {
    }
    /**
     * タスクID
     * @return int
     */
    public int getTaskId() {
        return this.taskId;
    }
    /**
     * ユーザーID
     * @return int
     */
    public int getUserId() {
        return this.userId;
    }
    /**
     * タスクの内容
     * @return String
     */
    public String getContent() {
        return this.content;
    }
    /**
     * 作成日
     * @return Timestamp
     */
    public Timestamp getCreated() {
        return this.created;
    }
    /**
     * 更新日
     * @return Timestamp
     */
    public Timestamp getUpdated() {
        return this.updated;
    }
    /**
     * 期日
     * @return Date
     */
    public Date getDeadLine() {
        return this.deadLine;
    }
    /**
     * 完了フラグ
     * @return String
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * タスクID
     * @param taskId
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    /**
     * ユーザーID
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * タスクの内容
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 作成日
     * @param created
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }
    /**
     * 更新日
     * @param updated
     */
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
    /**
     * 期日
     * @param deadLine
     */
    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
    /**
     * 完了フラグ
     * @param finishedFlag
     */
    public void setStatus(String status) {
        this.status = status;
    }

}