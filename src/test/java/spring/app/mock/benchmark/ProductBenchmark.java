package spring.app.mock.benchmark;


import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class ProductBenchmark {

    @Benchmark
    public void testMethod() {
        // 执行需要基准测试的代码
    }
}

