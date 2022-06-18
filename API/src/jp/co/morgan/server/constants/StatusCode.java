package jp.co.morgan.server.constants;

public enum StatusCode {
    // 未処理
    StatusCode0("0"),
    // 処理中
    StatusCode1("1"),
    // 完了
    StatusCode2("2"),
    // フィードバック
    StatusCode9("9");

    private final String status;
    /**
     * コンストラクタ
     * @param status
     */
    private StatusCode(String status) {
        this.status = status;
    }

    public String getCodeValue() {
        return status;
    }
}