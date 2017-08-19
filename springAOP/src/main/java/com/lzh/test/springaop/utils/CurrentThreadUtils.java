package com.lzh.test.springaop.utils;

import com.lzh.test.springaop.interfaces.IParallelThread;

import java.util.concurrent.*;

public class CurrentThreadUtils {
    /**
     * 多线程并发处理
     *
     * @param command
     * @param bq
     */
    public static void parallelJob(final IParallelThread command,
                                   final BlockingQueue<Object> bq,
                                   int threadNums) {
        ExecutorService es = Executors.newFixedThreadPool(
                threadNums > 0 ? threadNums : Runtime.getRuntime().availableProcessors() * 2);
        CompletionService<Boolean> cs = new ExecutorCompletionService<Boolean>(
                es);
        int len = bq.size();
        while (!bq.isEmpty()) {
            cs.submit(new Callable<Boolean>() {
                Object obj = bq.poll();

                public Boolean call() throws Exception {
                    return command.doMyJob(obj);
                }
            });
        }
        for (int i = 0; i < len; i++) {
            try {
                Future<Boolean> res = cs.take();
                if (res.get().equals(Boolean.FALSE)) {
                    throw new RuntimeException("多线程执行出错");
                }
            } catch (Exception e) {
                throw new RuntimeException("多线程执行出错", e);
            }
        }
        es.shutdown();
    }

    /**
     * 单线程线程处理
     *
     * @param command
     * @param bq
     */
    public static void parallelJobSin(final IParallelThread command,
                                   final BlockingQueue<Object> bq,
                                      CompletionService<Boolean> cs) {
        int len = bq.size();
        while (!bq.isEmpty()) {
            cs.submit(new Callable<Boolean>() {
                Object obj = bq.poll();

                public Boolean call() throws Exception {
                    return command.doMyJob(obj);
                }
            });
        }
        for (int i = 0; i < len; i++) {
            try {
                Future<Boolean> res = cs.take();
                if (res.get().equals(Boolean.FALSE)) {
                    throw new RuntimeException("多线程执行出错");
                }
            } catch (Exception e) {
                throw new RuntimeException("多线程执行出错", e);
            }
        }
    }
}

