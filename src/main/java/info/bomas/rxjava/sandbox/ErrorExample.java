package info.bomas.rxjava.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

public class ErrorExample {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorExample.class);

	public static void main(String[] args) {
		
		Observable.create(subscriber -> {
			subscriber.onError(new Exception("Oops"));
		}).subscribe(object -> {}, exception -> LOGGER.error("Oh no!",exception));
		
		

	}

}
