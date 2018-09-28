package com.java.lambda;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {
	private static final long serialVersionUID = 1L;
	private final long[] numbers;
	private final int start;
	private final int end;
	
	private static final long THRESHOLD = 100000;
	

	public ForkJoinSumCalculator(long[] numbers) {
		this(numbers,0, numbers.length);
	}

	private ForkJoinSumCalculator(long[] numbers, int start, int end) {
		super();
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		int length = end - start;
		if (length <= THRESHOLD) {
			return computeSequentially();
		}
		ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
		leftTask.fork();
		ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
		Long rightResult = rightTask.compute();
		Long leftResult = leftTask.join();
		return leftResult + rightResult;
	}

	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end ; i++) {
			sum += sum;
		}
		return sum;
	}
	
	private static long forkJoinSum (long n) {
		long[] numbers = LongStream.rangeClosed(1,  n).toArray();
		ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
		return new ForkJoinPool().invoke(task);
	}
	
	public static long measureSumPerf (Function<Long, Long> adder, long n) {
		long fastest = Long.MAX_VALUE;
		for (int i =0 ;i <10 ;i ++) {
			long start = System.nanoTime();
			adder.apply(n);
			long duration = (System.nanoTime() - start) / 1000000;
			if (duration < fastest) {
				fastest = duration;
			}
		}
		return fastest;
	}
	
	public static void main(String[] args) {
		System.out.println("Fork join process took " + measureSumPerf(ForkJoinSumCalculator::forkJoinSum, 10000000) + "ms");
	}
}
