package org.example;

import org.json.JSONObject;
import java.io.IOException;
public class JsonFormat {
    JSONObject jsonObject;
    public JsonFormat() throws IOException {
        this.jsonObject = new JSONObject();
    }

    public void print(String serach) throws IOException {

        if(serach.equals("Accept")){
            System.out.println("> Accept: " + jsonObject.get(serach));
        }else{
            System.out.println("> " + jsonObject.get(serach));
        }


    }

    public void PUT(String methodname, String str){

        jsonObject.put(methodname, str);

    }

    public String serach(String key){
        return (String) jsonObject.get(key);
    }


}


