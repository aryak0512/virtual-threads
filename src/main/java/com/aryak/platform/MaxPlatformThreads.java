package com.aryak.platform;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.aryak.utils.CommonUtils;
import com.aryak.virtual.MyRunnableTask;
/**
 * 
 * @author aryak
 * @apiNote Demonstrates creation of n no. of native OS threads
 **/
public class MaxPlatformThreads {
	
	static AtomicInteger i = new AtomicInteger(0);

	public static void main(String[] args) {

		final int MAX_THREADS = 20000;

		IntStream.rangeClosed(1, MAX_THREADS).forEach(t -> {
				
			Thread.ofPlatform().name("t" + t).start(new MyRunnableTask());
			i.getAndIncrement();
			CommonUtils.log.accept("No of platform threads created :" + i.get());

		});

		CommonUtils.log.accept("Main done.");
	}
}
