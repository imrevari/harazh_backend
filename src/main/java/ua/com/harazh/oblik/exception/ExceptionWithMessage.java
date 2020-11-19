package ua.com.harazh.oblik.exception;

import org.springframework.core.NestedRuntimeException;

@SuppressWarnings("serial")
public class ExceptionWithMessage extends NestedRuntimeException{
	
	private String msg;
	private String field;
	
	

	public ExceptionWithMessage(String msg, String field) {
		super(msg);
		this.msg = msg;
		this.field = field;		
	}



	public String getMsg() {
		return msg;
	}



	public String getField() {
		return field;
	}
	
	

}
