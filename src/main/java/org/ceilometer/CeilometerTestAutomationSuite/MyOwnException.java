package org.ceilometer.CeilometerTestAutomationSuite;
public class MyOwnException {
    
}
class MyAppException extends Exception {
	
	
	 
    private String message = "Bollocks to you";
 
    public MyAppException() {
        super();
    }
 
    public MyAppException(String message) {
        super(message);
        this.message = message;
    }
 
    public MyAppException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}