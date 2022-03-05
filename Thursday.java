import java.util.Scanner;
import java.util.Arrays;

public class Thursday{
	public static void main(String[] args){

		Scanner input = new Scanner(System.in);
		System.out.println("Enter an arithmetic expression");
		String expr = input.nextLine();
	
		String[] array = expr.split("(?=\\d | ?<=\\D)(?=\\D | (?<=\\d)");
		System.out.println(Arrays.toString(array));
   }

}