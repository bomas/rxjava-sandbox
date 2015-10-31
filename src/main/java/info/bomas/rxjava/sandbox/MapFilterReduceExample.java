package info.bomas.rxjava.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

public class MapFilterReduceExample {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapFilterReduceExample.class);

	public static void main(String[] args) {
		Observable.range(1, 100)
		.map(i -> i*i)
		.filter(i -> i % 2 ==0)
		.reduce((x,y)->x+y)
		.forEach(i -> LOGGER.info("result: {}", i));
		
		Observable.range(1, 10)
		.flatMap(i -> Observable.just(i).repeat(i))
		.forEach(i -> LOGGER.info("result: {}", i));
		

	}

}
