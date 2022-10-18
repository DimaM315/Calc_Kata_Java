import java.util.Scanner;
import java.util.Arrays;

public class Main{
	
	public static void main (String [] arg){
		Scanner scan = new Scanner(System.in);

		System.out.println("Input the expretion: ");
		try{
			String result = calc(scan.nextLine());
			System.out.println("Answer: "+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static String calc(String input) throws Exception {
		Calculator calculator = new Calculator();

		String result = calculator.calcExpretion(input);

		return result;
	}
}


class Calculator{
	String[] allowedOperator = {"+", "-", "*", "/"};
	String[] digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	String[] romeSimbols = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

	boolean modeRome = false;

	public String calcExpretion(String expretion) throws Exception {
		String operator; 
		int result;
		String [] operands;

		expretion =  expretion.replaceAll(" ", "");	

		if (checkCorrectExpretion(expretion)){
			expretion = this.replaceRomanicToArabic(expretion);

			operator = this.extractOperation(expretion);
			operands = expretion.split("[+-/*]");

			result = this.carryOutOperation(operands, operator);;
			if (this.modeRome){
				if (result < 1){
					throw new Exception("Rome alifmetic system have only positive number.");
				}
				return this.convertArabicToRome(result);
			}
			return Integer.toString(result);	
		}
		throw new Exception("The program received an incorrect expretion.");
	}

	private int carryOutOperation(String[] operands, String operator){
		int result = 0;
		int operand1 = Integer.parseInt(operands[0]);
		int operand2 = Integer.parseInt(operands[1]);

		switch (operator) {
			case "+" : result = operand1 + operand2;break;
			case "*" : result = operand1 * operand2;break;
			case "-" : result = operand1 - operand2;break;
			case "/" : result = operand1 / operand2;break;
		}
		return result;
	}

	private boolean checkCorrectExpretion(String expretion){
		return this.hasOneOperator(expretion) && this.hasOnlyTwoOperands(expretion);
	}

	private boolean hasOnlyTwoOperands(String expretion){
		int romeCounter = 0;
		int arabicCounter = 0;
		
		// Here we already know that there is 1 operator in the expretion.
		String[] blocks = expretion.split("[+-/*]");
	
		for (String rome : this.romeSimbols){
			if (blocks[0].equals(rome)){
				romeCounter++;
			}
			if(blocks[1].equals(rome)){
				romeCounter++;
			}
		}

		for (String arabic : this.digits){
			if (blocks[0].equals(arabic)){
				arabicCounter++;
			}
			if(blocks[1].equals(arabic)){
				arabicCounter++;
			}
		}

		if (romeCounter == 2 || arabicCounter == 2){
			if (romeCounter == 2){
				this.modeRome = true;
			}
			return true;
		}
		return false;
	}

	private boolean hasOneOperator(String expretion){
		int operatorCounter = 0;
		for (int i=0; i<expretion.length();i++){

			String tmpSimbol = Character.toString(expretion.charAt(i));

			for (int j=0; j<this.allowedOperator.length;j++){
				if (tmpSimbol.equals(this.allowedOperator[j])){
					operatorCounter += 1;
				}
			}
				
		}
		return (operatorCounter == 1);
	}

	private String replaceRomanicToArabic(String expretion){
		// There we know that expretion have two same type operands.
		expretion = expretion.replaceAll(this.romeSimbols[8], this.digits[8]);
		expretion = expretion.replaceAll(this.romeSimbols[7], this.digits[7]);
		expretion = expretion.replaceAll(this.romeSimbols[6], this.digits[6]);
		expretion = expretion.replaceAll(this.romeSimbols[3], this.digits[3]);
		expretion = expretion.replaceAll(this.romeSimbols[2], this.digits[2]);
		for (int i=this.digits.length-1;i>=0;i--){
			if (expretion.indexOf(this.romeSimbols[i]) != -1){
				expretion = expretion.replaceAll(this.romeSimbols[i], this.digits[i]);
			}
		}
		return expretion;
	}

	private String extractOperation(String expretion){
		// There we know that expretion have only one allowed operation.
		for (String operator : this.allowedOperator){
			if (expretion.indexOf(operator) != -1){
				return operator;
			}
		}
		return "Operation is none";
	}

	private String convertArabicToRome(int arabicInt){
		System.out.println(arabicInt);
		String result = "";
		while(arabicInt != 0){
			if (arabicInt >= 50){
				result += "L";
				arabicInt -= 50;
			}else if(arabicInt >= 10){
				result += "X";
				arabicInt -= 10;
			}else if(arabicInt >= 5){
				result += "V";
				arabicInt -= 5;
			}else if(arabicInt >= 1){
				result += "I";
				arabicInt -= 1;
			}
		}
		return result;
	}
}
