
public class Palindrome {

    public static boolean isDoublePalindrome (char[] digits)
    {
		if(digits.length % 2 != 0) return false;
		
		for(int i = 0; i< digits.length/2; i++)
		{
			if(digits[i] != digits[digits.length - i]) return false;
		}
		
		for(int j = 0; j< digits.length/4; j++)
		{
			if(digits[j] != digits[digits.length/2 - j]) return false;
			if(digits[j + digits.length/2] != digits[digits.length - j]) return false;
		}
    }

}
