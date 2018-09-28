# ParallelStreams
Benchmarking parallel streams performance

These programs benchmarks the performance of a summation process using traditional for loops versus using streams along with parallel streams. It also demonstrates the use of Forj/Join pool to see how parallel streams leverages it.
Here are the results:

Sequential stream sum took 107 ms - largely due to the overhead of boxing/unboxing of obejcts
Iterative sum took 4 ms - largely due to the overhead of boxing/unboxing of obejcts
Parallel stream sum took 186 ms
Sequential stream without overhead sum took 5 ms
Parallel stream without overhead sum took 2 ms - Shines here using Primitive streams as it avoids the boxing/unboxing activites
Fork join process took 47 ms