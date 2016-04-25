/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import entity.User;
import java.util.List;

/**
 *
 * @author Alex
 */
public class JsonConverter {
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public static JsonObject getAllUsers(User u){
        JsonObject users = new JsonObject();
        JsonArray roles = new JsonArray();
        
        if(u.getUserName() != null){
            users.addProperty("username", u.getUserName());
        }
        else{
            users.addProperty("username", "");
        }
        if(u.getRoles() != null || u.getRoles().isEmpty()){
            for (int i = 0; i < u.getRoles().size(); i++) {
                if(u.getRoles().get(i) != null){
                    JsonPrimitive role = new JsonPrimitive(u.getRoles().get(i).getRoleName());
                    roles.add(role);
                }
                
            }
        }
        else{
            roles.add(new JsonObject());
        }
        
        users.add("roles", roles);
        return users;
    }
    
    public static JsonObject getAllUsers(List<User> us){
        JsonArray users = new JsonArray();
        for (User user : us) {
            users.add(getAllUsers(user));
        }
        JsonObject test = new JsonObject();
        test.add("users", users);
        return test;
    }
    
    public static String getJSON(JsonElement voorhees) {

        return gson.toJson(voorhees);
    }
    
}
