package Test;

import java.util.Scanner;

public class scanner {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("num1: ");
        String x = input.nextLine();
        System.out.print("num2: ");
        String y = input.nextLine();
        System.out.println("Sum: " + (x + y));
    }
}
