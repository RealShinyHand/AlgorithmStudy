package p20220527;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SWEA1928 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int TC = Integer.parseInt(br.readLine());
		char curChar;

		StringBuilder sb = new StringBuilder();

		for (int t = 1; t <= TC; t++) {
			String inputString = br.readLine();
			for (int i = 0; i < inputString.length(); i ++) {

				curChar = inputString.charAt(i);
				int value = charToBase64Vaule(curChar);
				String binaryString = String.format("%s", Integer.toBinaryString(value));
				while(binaryString.length() < 6) {
					binaryString = "0".concat(binaryString);
				}
				sb.append(binaryString);
			}
			
			String totalBinaryString = sb.toString();
			sb.delete(0, sb.length());
			
			System.out.printf("#%d ",t);
			for(int i = 0 ; i < totalBinaryString.length();i+= 8) {
				int temp = 0;
				for(int j = 0; j < 8 ; j++) {
					temp += (totalBinaryString.charAt(i+j) - '0') << (7-j);
				}
				System.out.print((char)temp);
			}
			System.out.println();
		}

	}

	static int charToBase64Vaule(char curChar) {
		int temp = 0;
		if (curChar >= 'A' && curChar <= 'Z') {
			temp = curChar - 'A';
		} else if (curChar >= 'a' && curChar <= 'z') {
			temp = curChar - 'a' + 26;
		} else if (curChar >= '0' && curChar <= '9') {
			temp = curChar - '0' + 52;
		} else {
			if (curChar == '+') {
				temp = 62;
			} else if (curChar == '/') {
				temp = 63;
			} else {
				System.out.println("에러");
			}
		}

		return  temp;
	}
}
