package Test;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ToJSON {
    public static void main(String args[]) {
        UserDetails user = new UserDetails("John",
                "john.doe@gmail.com",
                29, 5168161922L,
                "NewYork",
                false);
        Gson gson = new Gson();
        //java to json
        String json = gson.toJson(user);
        System.out.println(json);

        //json to java
        try{
            UserDetails us = gson.fromJson(json + "wer", UserDetails.class);
            System.out.println(us.getAge());
        }catch (JsonSyntaxException e){
            System.out.println("Error");
//            e.printStackTrace();
        }
    }
}

class UserDetails {
    private String name;
    private String email;
    private int age;
    private long phone;
    private String city;
    private boolean hasCreditCard;
    public UserDetails(String name,
                       String email,
                       int age,
                       long phone,
                       String city,
                       boolean hasCreditCard) {
        super();
        this.name = name;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.city = city;
        this.hasCreditCard = hasCreditCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isHasCreditCard() {
        return hasCreditCard;
    }

    public void setHasCreditCard(boolean hasCreditCard) {
        this.hasCreditCard = hasCreditCard;
    }
}
