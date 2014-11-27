package study.org.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONStudy {
    public JSONStudy(){
//        StringToJSONObject();
//        StringToJSON();
        ListToJSON();
    }
    private void StringToJSONObject() {
        String jsonMessage = "{'语文':'88','数学':'78','计算机':'99'}";
        JSONObject myJsonObject = null;
        String value = null;
        try {
            // 将字符串转换成jsonObject对象
            myJsonObject = new JSONObject(jsonMessage);
            // 获取对应的值
            value = myJsonObject.getString("数学");
        } catch (JSONException e) {
        }
        System.out.print("myJsonObject=");
        System.out.println(myJsonObject);
        System.out.println("value1=" + value);
    }
    private void ListToJSON(){
        List list = new ArrayList();  
        list.add( "JSON" );   
        list.add( "1" );   
        list.add( "2.0" );   
        list.add( "true" );   
        System.out.println(new JSONArray(list));
        String[] array = {"asd","dfgd","asd","234"};
        System.out.println(new JSONArray(array));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", 1001);
        map.put("userName", "张三");
        map.put("userSex", "男");
        System.out.println(new JSONObject(map));
    }
    private void printJSON(String jsonobject){
        System.out.println(jsonobject);
    }

    private void StringToJSON() {
        String jsonMessage = "[{'num':'成绩', '外语':88, '历史':65, '地理':99, 'object':{'aaa':'1111','bbb':'2222','cccc':'3333'}},"
                + "{'num':'兴趣', '外语':28, '历史':45, '地理':19, 'object':{'aaa':'11a11','bbb':'2222','cccc':'3333'}},"
                + "{'num':'爱好', '外语':48, '历史':62, '地理':39, 'object':{'aaa':'11c11','bbb':'2222','cccc':'3333'}}]";
        JSONArray myJsonArray;
        try {
            myJsonArray = new JSONArray(jsonMessage);
            for (int i = 0; i < myJsonArray.length(); i++) {
                // 获取每一个JsonObject对象
                JSONObject myjObject = myJsonArray.getJSONObject(i);
                // 获取每一个对象中的值
                String numString = myjObject.getString("num");
                int englishScore = myjObject.getInt("外语");
                int historyScore = myjObject.getInt("历史");
                int geographyScore = myjObject.getInt("地理");
                // 获取数组中对象的对象
                JSONObject myjObject2 = myjObject.getJSONObject("object");
                String aaaString = myjObject2.getString("aaa");
                System.out.println("aaaString=" + aaaString);
                System.out.println("numString=" + numString);
                System.out.println("englishScore=" + englishScore);
                System.out.println("historyScore=" + historyScore);
                System.out.println("geographyScore=" + geographyScore);
                System.out.println("myjObject2=" + myjObject2);
            }
        } catch (JSONException e) {
        }
    }
    public static void main(String[] args){
        JSONStudy jsonStudy = new JSONStudy();
    }
}
