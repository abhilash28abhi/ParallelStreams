# ParallelStreams
Benchmarking parallel streams performance

These programs benchmarks the performance of a summation process using traditional for loops versus using streams along with parallel streams. It also demonstrates the use of Forj/Join pool to see how parallel streams leverages it.
Here are the results:

1. Sequential stream sum took 107 ms - largely due to the overhead of boxing/unboxing of obejcts
2. Iterative sum took 4 ms - largely due to the overhead of boxing/unboxing of obejcts
3. Parallel stream sum took 186 ms
4. Sequential stream without overhead sum took 5 ms
5. Parallel stream without overhead sum took 2 ms - Shines here using Primitive streams as it avoids the boxing/unboxing activites
6. Fork join process took 47 ms
