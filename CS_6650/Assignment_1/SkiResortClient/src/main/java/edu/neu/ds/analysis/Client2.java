package edu.neu.ds.analysis;

import edu.neu.ds.load.Client1;
import edu.neu.ds.load.Client1Data;
import edu.neu.ds.model.Data;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Client2 {

    public Client2Data generateLoadAndAnalyze(int maxThreads, int skiers, int skiLifts, int numRuns) throws IOException, InterruptedException {

        List<Data> result = new ArrayList<>();
        // Phase 1
        System.out.println("Starting phase1");
        System.out.println(System.currentTimeMillis());
        AtomicInteger requestsCompleted1 = new AtomicInteger();
        AtomicInteger requestsErrored1 = new AtomicInteger();
        int totalThreadsForPhase1 = maxThreads/4;
        int skierStart1 = 1;
        int incrementalValueForPhase1 = skiers/totalThreadsForPhase1;
        int skierEnd1 = skierStart1 + incrementalValueForPhase1 - 1;
        CountDownLatch task1Latch = new CountDownLatch((int)Math.ceil((totalThreadsForPhase1) * 0.1));
        CountDownLatch task1LatchFull = new CountDownLatch((int)Math.ceil((totalThreadsForPhase1)));
        ExecutorService executorService1 = Executors.newFixedThreadPool(totalThreadsForPhase1);
        long initialTime = System.currentTimeMillis();
        for (int i = 0; i < totalThreadsForPhase1; i++) {
            executorService1.execute(new Task2(skierStart1, skierEnd1, 1, 90, skiLifts,
                    requestsCompleted1, requestsErrored1, task1Latch, task1LatchFull, (int) (numRuns * 0.1), result, "Task1", "Thread-" + i));
            skierStart1 += incrementalValueForPhase1;
            skierEnd1 += incrementalValueForPhase1;
        }
        System.out.println("Waiting for phase1 to complete 10%");
        task1Latch.await();
        System.out.println(System.currentTimeMillis());

        // Phase 2

        System.out.println("Starting phase2");
        int totalThreadsForPhase2 = maxThreads;
        int incrementalValueForPhase2 = skiers/totalThreadsForPhase2;
        int skierStart2 = 1;
        int skierEnd2 = skierStart2 + incrementalValueForPhase2 - 1;
        CountDownLatch task2Latch = new CountDownLatch((int)Math.ceil((totalThreadsForPhase2) * 0.1));
        CountDownLatch task2LatchFull = new CountDownLatch((int)Math.ceil((totalThreadsForPhase2)));
        AtomicInteger requestsCompleted2 = new AtomicInteger();
        AtomicInteger requestsErrored2 = new AtomicInteger();
        ExecutorService executorService2 = Executors.newFixedThreadPool(totalThreadsForPhase2);
        for (int i = 0; i < totalThreadsForPhase2; i++) {
            executorService2.execute(new Task2(skierStart2, skierEnd2, 91, 360, skiLifts,
                    requestsCompleted2, requestsErrored2, task2Latch, task2LatchFull, (int) (numRuns * 0.8), result, "Task2", "Thread-" + i));
            skierStart2 += incrementalValueForPhase2;
            skierEnd2 += incrementalValueForPhase2;
        }

        System.out.println("Waiting for phase2 to complete 10%");
        task2Latch.await();
        System.out.println(System.currentTimeMillis());

        // Phase 3

        System.out.println("Starting phase3");
        int totalThreadsForPhase3 = maxThreads/4;
        ExecutorService executorService3 = Executors.newFixedThreadPool(totalThreadsForPhase3);
        int incrementalValueForPhase3 = skiers/totalThreadsForPhase3;
        int skierStart3 = 1;
        int skierEnd3 = skierStart3 + incrementalValueForPhase3 - 1;
        AtomicInteger requestsCompleted3 = new AtomicInteger();
        AtomicInteger requestsErrored3 = new AtomicInteger();
        CountDownLatch task3Latch = new CountDownLatch(totalThreadsForPhase3);
        CountDownLatch task3LatchFull = new CountDownLatch(totalThreadsForPhase3);
        for (int i = 0; i < totalThreadsForPhase3; i++) {
            executorService3.execute(new Task2(skierStart3, skierEnd3, 361, 420, skiLifts,
                    requestsCompleted3, requestsErrored3, task3Latch, task3LatchFull, (int) (numRuns * 0.1), result, "Task3", "Thread-" + i));
            skierStart3 += incrementalValueForPhase3;
            skierEnd3 += incrementalValueForPhase3;
        }

        System.out.println("Waiting for all phases to complete");
        task3LatchFull.await();
        task1LatchFull.await();
        task2LatchFull.await();

        long endTime = System.currentTimeMillis();

        executorService1.shutdown();
        executorService2.shutdown();
        executorService3.shutdown();

        System.out.println("SuccessfulRequests1 = " + requestsCompleted1);
        System.out.println("SuccessfulRequests2 = " + requestsCompleted2);
        System.out.println("SuccessfulRequests3 = " + requestsCompleted3);
        System.out.println("ErroredRequests1 = " + requestsErrored1);
        System.out.println("ErroredRequests2 = " + requestsErrored2);
        System.out.println("ErroredRequests3 = " + requestsErrored3);

        int successfulRequests = requestsCompleted1.get() + requestsCompleted2.get() + requestsCompleted3.get();
        int erroredRequests = requestsErrored1.get() + requestsErrored2.get() + requestsErrored3.get();

        System.out.println("Successful Requests = " + successfulRequests);
        System.out.println("Errored Requests = " + erroredRequests);
        int totalRequests = successfulRequests + erroredRequests;
        long wallTime = endTime - initialTime;
        long throughput = totalRequests/wallTime;
        System.out.println("Total threads = " + maxThreads);
        System.out.println("Total requests = " + totalRequests);
        System.out.println("Wall time = " + wallTime);
        System.out.println("Throughput = " + throughput);

        List<Long> latencies = result.stream().map(data -> data.getLatency()).collect(Collectors.toList());

        String fileName = "records_" + maxThreads + ".csv";
        FileWriter csvWriter = new FileWriter(fileName);
        csvWriter.append("StartTime");
        csvWriter.append(",");
        csvWriter.append("EndTime");
        csvWriter.append(",");
        csvWriter.append("Latency");
        csvWriter.append(",");
        csvWriter.append("Request");
        csvWriter.append(",");
        csvWriter.append("edu.neu.ds.analysis.Task2");
        csvWriter.append(",");
        csvWriter.append("Thread");
        csvWriter.append(",");
        csvWriter.append("NumRun");
        csvWriter.append("\n");

        for (edu.neu.ds.model.Data rowData : result) {
            csvWriter.append(String.valueOf(rowData.getStartTime()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(rowData.getEndTime()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(rowData.getLatency()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(rowData.getRequestType()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(rowData.getTask()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(rowData.getThread()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(rowData.getNumRun()));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

        double[] latencyArray = latencies.stream().mapToDouble(val -> val).toArray();
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double item : latencyArray) {
            stats.addValue(item);
        }

        System.out.println("Max Response time = "+stats.getMax());

        System.out.println("Min Response time = "+stats.getMin());

        System.out.println("Mean Response time = "+stats.getMean());

        System.out.println("P99 "+stats.getPercentile(99));

        System.out.println("OTHER STATS INCLUDING MEDIAN RESPONSE TIME - ");
        System.out.println(stats);

        Client2Data client2Data = new Client2Data(maxThreads, throughput, stats.getMax());

        return client2Data;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        Client2 client2 = new Client2();
        List<String> list = new ArrayList();
        List<Client2Data> result = new ArrayList();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("client2_input");
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Cannot find input file");
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read input file");
        } finally {
            if (br != null) try { br.close(); } catch (IOException e) { }
            if (fr != null) try { fr.close(); } catch (IOException e) { }
        }

        for (int i = 0; i < list.size(); i++) {
            String current = list.get(i);
            String[] currParts = current.split(",");
            Map<String, Integer> map = new HashMap();
            map.put("maxThreads", -1);
            map.put("skiers", -1);
            map.put("skiLifts", 40);
            map.put("numRuns", 10);

            if (currParts != null && currParts.length >= 2 && currParts.length <= 4) {
                for (String arg : currParts) {
                    String[] parts = arg.split("=");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Enter the input in the following format." +
                                "Example: maxThreads=32 skiers=20000 skiLifts=40 numRuns=10");
                    } else {
                        if (map.containsKey(parts[0].trim())) {
                            map.put(parts[0].trim(), Integer.valueOf(parts[1].trim()));
                        } else {
                            throw new IllegalArgumentException("Enter the input in the following format." +
                                    "Example: maxThreads=32 skiers=20000 skiLifts=40 numRuns=10");
                        }
                    }
                }

            } else {
                throw new IllegalArgumentException("Enter the input in the following format." +
                        "Example: maxThreads=32 skiers=20000 skiLifts=40 numRuns=10");
            }


            if (map.get("maxThreads") < 4 || map.get("maxThreads") > 256) {
                throw new IllegalArgumentException("Max threads can be between 4 and 256");
            }
            if (map.get("skiers") < 1 || map.get("skiers") > 50000) {
                throw new IllegalArgumentException("Max skiers can be between 1 and 50000");
            }
            if (map.get("skiLifts") < 5 || map.get("skiLifts") > 60) {
                throw new IllegalArgumentException("Skilifts can be between 5 and 60");
            }
            if (map.get("numRuns") < 10 || map.get("numRuns") > 20) {
                throw new IllegalArgumentException("NumRuns can be between 10 and 20");
            }

            Client2Data client2Data = client2.generateLoadAndAnalyze(map.get("maxThreads"), map.get("skiers"),
                    map.get("skiLifts"), map.get("numRuns"));
            result.add(client2Data);
        }


        FileWriter csvWriter = new FileWriter("chart2.csv");
        csvWriter.append("Threads");
        csvWriter.append(",");
        csvWriter.append("Throughput");
        csvWriter.append(",");
        csvWriter.append("Max Response time");
        csvWriter.append("\n");

        for (Client2Data data : result) {
            csvWriter.append(String.valueOf(data.getThreads()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(data.getThroughput()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(data.getMaxResponseTime()));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }
}
