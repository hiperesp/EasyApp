package org.hiperesp.easyapp.core.js_bridge;

public class PromiseUtils {
    public String string(String literalData) {
        StringBuilder data = new StringBuilder();
        for(int i=0; i<literalData.length(); i++) {
            char currentChar = literalData.charAt(i);
            switch(currentChar){
                case '\"':
                    data.append("\\\"");
                    break;
                case '\n':
                    data.append("\\n");
                    break;
                default:
                    data.append(currentChar);
                    break;
            }
        }
        return "\""+data+"\"";
    }
    public String number(String literalData) {
        String data = "";
        for(int i=0; i<literalData.length(); i++) {
            char currentChar = literalData.charAt(i);
            if((currentChar>47&&currentChar<58)||currentChar==46) data+= currentChar;
        }
        return data;
    }
    public String number(long number){
        return ""+number;
    }
    public String number(double number){
        return ""+number;
    }
    public String bool(String literalData) {
        return literalData.equalsIgnoreCase("true")?"true":"false";
    }
}
