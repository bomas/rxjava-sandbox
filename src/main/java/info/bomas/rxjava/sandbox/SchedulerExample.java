package info.bomas.rxjava.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.schedulers.Schedulers;

public class SchedulerExample {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerExample.class);
	
	
	public static void main(String[] args){
		
		Observable<Integer> observable = Observable.range(1, 1000);
		observable.subscribeOn(Schedulers.computation()).forEach(item -> LOGGER.info("One: " + item));
		observable.subscribeOn(Schedulers.computation()).toBlocking().forEach(item -> LOGGER.info("Two: " + item));
		
	}

}
