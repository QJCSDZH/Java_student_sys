package org.example.studentsystem.common;

import lombok.Data;

@Data
public class PHResult<T> {
    private Integer code;
    private String message;
    private T content;

    /*
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getContent() {
        return content;
    }
    public void setContent(T content) {
        this.content = content;
    }
    */

    public static <T> PHResult<T> success(T content) {
        PHResult<T> result = new PHResult<T>();
        result.setCode(200);
        result.setMessage("success");
        result.setContent(content);
        return result;
    }

    public static <T> PHResult<T> fail(String message) {
        PHResult<T> result = new PHResult<T>();
        result.setCode(500);
        result.setMessage(message);
        result.setContent(null);
        return result;
    }

}
