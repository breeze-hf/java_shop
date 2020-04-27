package ltd.newbee.mall.common;

/**
 * 功能描述：
 *
 * @Author: zhangjie
 * @Date: 2020/4/12 15:19
 */
public enum ExceptionStatusCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败");

    private int code;

    private String massege;

    ExceptionStatusCodeEnum(int code, String massege) {
        this.code = code;
        this.massege = massege;
    }

    public int getCode() {
        return code;
    }

    public String getMassege() {
        return massege;
    }

}

