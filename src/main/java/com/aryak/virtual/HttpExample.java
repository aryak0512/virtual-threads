package com.aryak.virtual;

import static java.lang.Thread.sleep;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.aryak.records.TemplateRequest;
import com.aryak.utils.CommonUtils;
import com.aryak.utils.RestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpExample {

	public static AtomicInteger count = new AtomicInteger(0);
	static final String URL = "https://jsonplaceholder.typicode.com/todos/1";
	public static ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		final int MAX_THREADS = 300;
		IntStream.rangeClosed(1, MAX_THREADS).forEach(i -> {

			Future<TemplateRequest> future = executorService.submit(() -> makeHttpCall());
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
			// count.incrementAndGet();
		});
	}

	/**
	 * making 2 HTTP calls using virtual threads
	 * 
	 * public static void process() { try (var scope = new
	 * StructuredTaskScope.ShutdownOnFailure()) {
	 * 
	 * // fork the tasks StructuredTaskScope.Subtask<TemplateRequest> task1 =
	 * scope.fork(Main::makeHttpCall); StructuredTaskScope.Subtask<TemplateRequest>
	 * task2 = scope.fork(Main::makeHttpCall);
	 * 
	 * // join the tasks scope.join().throwIfFailed(); // this is non-blocking
	 * 
	 * // retrieve results var result1 = task1.get(); var result2 = task2.get();
	 * System.out.println("Result 1: " + result1 + " | Result 2 : " + result2);
	 * 
	 * } catch (Exception ignored) {
	 * 
	 * } }
	 **/

	public static TemplateRequest makeHttpCall() throws IOException, InterruptedException {
		var httpClient = RestUtils.getClient();
		var httpRequest = RestUtils.getHttpRequest(URL);
		HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		System.out.println("Response code : " + response.statusCode() + " | Body : " + response.body());
		return new ObjectMapper().readValue(response.body(), TemplateRequest.class);
	}

	static void doSomething() {
		
		CommonUtils.log.accept("started task");
		try {
			sleep(5000);
		} catch (Exception ignored) {
		}
		CommonUtils.log.accept("Finished task");
	}

	
}