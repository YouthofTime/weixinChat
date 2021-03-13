package cn.itcast.utils;

import java.util.HashMap;
import java.util.Map;

public class StateJudge {
    private boolean judge;
    private String msg;
    private Map<String,Object> hashmap=new HashMap<>();
    public static StateJudge success(){
        StateJudge state=new StateJudge();
        state.judge=true;
        return state;
    }
    public static StateJudge error(){
        StateJudge state=new StateJudge();
        state.judge=false;
        return state;
    }
    public StateJudge setHashMap(String key,Object value){
        this.hashmap.put(key,value);
        return this;
    }

    public Map<String, Object> getHashmap() {
        return hashmap;
    }

    public void setHashmap(Map<String, Object> hashmap) {
        this.hashmap = hashmap;
    }

    public boolean isJudge() {
        return judge;
    }

    public void setJudge(boolean judge) {
        this.judge = judge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
