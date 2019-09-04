package cn.lijiahao.Json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 将向前端返回的数据进行封装
 * @author franky
 */
public class JsonResult implements Serializable{
	private static final long serialVersionUID = -5799876186305241574L;
	
	private Map<String, Object> datas = new HashMap<String, Object>();
	private String message;
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void addDatas(String key,Object value) {
		if(!key.isEmpty()) {
			datas.put(key, value);
		}
	}
	
}
