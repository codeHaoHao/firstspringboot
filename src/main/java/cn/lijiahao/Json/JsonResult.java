package cn.lijiahao.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 将向前端返回的数据进行封装
 *
 * @author franky
 */
public class JsonResult implements Serializable {
    public enum JsonResultEmum {
        SUCCESS(1,"success"),ERROR(0,"error");

        int id;
        String status;

       JsonResultEmum(int id, String status){
            this.id = id;
            this.status = status;
       }

       public String status(){
           return this.status;
       }
    }
    private static final long serialVersionUID = -5799876186305241574L;

    private Map<String, Object> datas = new HashMap<String, Object>(); // response datas
    private String message; // error message
    private JsonResultEmum status = JsonResultEmum.SUCCESS;
    private boolean success = true;
    private String code;

    public JsonResult() {

    }

    public JsonResult(Map<String, Object> map, String message, boolean success, String code) {
        super();
        this.datas = map;
        this.message = message;
        this.success = success;
        this.code = code;
    }

    public Map<String, Object> getMap() {
        return datas;
    }

    public void setMap(Map<String, Object> map) {
        this.datas = map;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(JsonResultEmum status){
        this.status = status;
    }

    public String getStatus(){
        return status.status();
    }

    public String getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void addDatas(String key, Object value) {
        if (!key.isEmpty()) {
            datas.put(key, value);
        }
    }

}
