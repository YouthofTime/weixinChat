package cn.itcast.bitcoin;



public class MyMessage {
    private String message;
    private MyMessage(){

    }
    private static MyMessage myMessage=new MyMessage();
    public static MyMessage getInstance(){
        return myMessage;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
