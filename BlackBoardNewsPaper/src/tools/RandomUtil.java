package tools;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
	public static String random(int length){
		StringBuilder builder = new StringBuilder(length);
		for(int i = 0;i<length;i++){
			builder.append((char)(ThreadLocalRandom.current().nextInt(33,128)));
			
		}
		return builder.toString();
	}
	public static void main(String[] args) {
		System.out.println(random(22));
	}
}
