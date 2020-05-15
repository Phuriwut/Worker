package Test;

public class checkValid {
     static boolean isValid(String email) {
            String regex = "john123@gmail.com"; //if(john123@gmail.com) = true ,else false
            return email.matches(regex);
     }

     public static void main(String[] args) {
         String email = "john123@gmail.com";
         System.out.println("The E-mail ID is: " + email);
         System.out.println("Is the above E-mail ID valid? " + isValid(email));
     }
}
