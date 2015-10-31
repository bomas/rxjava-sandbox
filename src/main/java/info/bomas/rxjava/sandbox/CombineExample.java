package info.bomas.rxjava.sandbox;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

public class CombineExample {

	private static final Logger LOGGER = LoggerFactory.getLogger(CombineExample.class);

	public static void main(String[] args) {
		
		Observable<String> lowerCase = Observable.from(Arrays.asList("a","b","c","d"));
		Observable<String> upperCase = Observable.from(Arrays.asList("A","B","C","D"));
		
		Observable.zip(lowerCase,upperCase, (a, b)-> a +"_" + b )
		.forEach(ab -> LOGGER.info("zip {}",ab));
		
		
		Observable.merge(lowerCase, upperCase)
		.forEach(ab -> LOGGER.info("merge {}",ab));
		
		

	}

}
