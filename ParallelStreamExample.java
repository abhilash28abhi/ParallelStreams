package com.java.lambda;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreamExample {

	public static long sequentialSum (long n) {
		return Stream.iterate(1l, i -> i+1)
				.limit(n)
				.reduce(0l, Long::sum);
	}
	
	public static long sequentialWithoutBoxingOverheadSum (long n) {
		return LongStream.rangeClosed(1, n)
				.reduce(0l, Long::sum);
	}
	
	public static long parallelSum (long n) {
		return Stream.iterate(1L, i -> i +1)
				.limit(n)
				.parallel()
				.reduce(0l, Long::sum);
	}
	
	public static long parallelWithoutBoxingOverheadSum (long n) {
		return LongStream.rangeClosed(1, n)
				.parallel()
				.reduce(0l, Long::sum);
	}
	
	public static long iterativeSum(long n) {
		long result = 0;
		for (long i = 1l; i <=n ; i++) {
			result = result + i;
		}
		return result;
	}
	
	public static long measureSumPerf (Function<Long, Long> adder, long n) {
		long fastest = Long.MAX_VALUE;
		for (int i =0 ;i <10 ;i ++) {
			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime() - start) / 1000000;
			//System.out.println("Result " + sum);
			if (duration < fastest) {
				fastest = duration;
			}
		}
		return fastest;
	}
	
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
		System.out.println("Sequential stream sum took " + measureSumPerf(ParallelStreamExample::sequentialSum, 10000000) + " ms");
		System.out.println("Iterative sum took " + measureSumPerf(ParallelStreamExample::iterativeSum, 10000000) + " ms");
		System.out.println("Parallel stream sum took " + measureSumPerf(ParallelStreamExample::parallelSum, 10000000) + " ms");
		System.out.println("Sequential stream without overhead sum took " + measureSumPerf(ParallelStreamExample::sequentialWithoutBoxingOverheadSum, 10000000) + " ms");
		System.out.println("Parallel stream without overhead sum took " + measureSumPerf(ParallelStreamExample::parallelWithoutBoxingOverheadSum, 10000000) + " ms");
	}
}
