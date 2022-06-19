package jp.co.morgan.server.constants;

public enum StatusCode {
    // 未処理
    Code0("0"),
    // 処理中
    Code1("1"),
    // 完了
    Code2("2"),
    // フィードバック
    Code9("9");

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