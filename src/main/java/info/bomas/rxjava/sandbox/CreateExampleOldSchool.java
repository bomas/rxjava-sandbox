package info.bomas.rxjava.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscriber;

public class CreateExampleOldSchool {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateExampleOldSchool.class);
	
	
	public static void main(String[] args){
		
		Observable.create(new Observable.OnSubscribe<Integer>() {
		    @Override
		    public void call(Subscriber<? super Integer> observer) {
		        try {
		            if (!observer.isUnsubscribed()) {
		                for (int i = 1; i < 5; i++) {
		                    observer.onNext(i);
		                }
		                observer.onCompleted();
		            }
		        } catch (Exception e) {
		            observer.onError(e);
		        }
		    }
		 } ).subscribe(new Subscriber<Integer>() {
		        @Override
		        public void onNext(Integer item) {
		        	LOGGER.info("Next: {}", item);
		        }

		        @Override
		        public void onError(Throwable error) {
		        	LOGGER.info("Error: {}", error.getMessage());
		        }

		        @Override
		        public void onCompleted() {
		        	LOGGER.info("Sequence complete.");
		        }
		    });
	}

}
