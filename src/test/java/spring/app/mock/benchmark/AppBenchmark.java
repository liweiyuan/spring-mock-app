package spring.app.mock.benchmark;

import org.openjdk.jmh.annotations.*;
import spring.app.mock.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime) // 可以是 Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime // 可以是 Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime
// 可以是 Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime
@OutputTimeUnit(TimeUnit.NANOSECONDS) // 输出时间单位 // 输出时间单位
// 输出时间单位
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) // 预热迭代 // 预热迭代
// 预热迭代
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS) // 实际测量迭代 // 实际测量迭代
// 实际测量迭代
@Fork(1) // Fork 一个 JVM 进程进行测试 // Fork 一个 JVM 进程进行测试
public class AppBenchmark {

    private List<Product> products;

    @Setup(Level.Trial) // 每次完整的基准测试运行前执行一次
    public void trialSetup() {
        products = new ArrayList<>();
    }

    @TearDown(Level.Trial) // 每次完整的基准测试运行后执行一次
    public void trialTearDown() {
        products.clear();
        products = null;
    }

    @Benchmark
    public void benchmark() {
        //add
        //query
        //update
        //delete
        for (int i = 0; i < 100000; i++) {
            products.add(new Product());
        }

    }
}
