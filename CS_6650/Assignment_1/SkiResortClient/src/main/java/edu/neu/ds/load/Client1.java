package edu.neu.ds.load;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client1 {

    public Client1Data generateLoad(int maxThreads, int skiers, int skiLifts, int numRuns) throws InterruptedException, IOException {
        // Phase 1
        System.out.println("Starting phase1");
        System.out.println(System.currentTimeMillis());

        int totalThreadsForPhase1 = maxThreads/4;
        int skierStart1 = 1;
        int incrementalValueForPhase1 = skiers/totalThreadsForPhase1;
        int skierEnd1 = skierStart1 + incrementalValueForPhase1 - 1;
        CountDownLatch task1Latch = new CountDownLatch((int)Math.ceil((totalThreadsForPhase1) * 0.1));
        CountDownLatch task1LatchFull = new CountDownLatch((int)Math.ceil((totalThreadsForPhase1)));
        ExecutorService executorService1 = Executors.newFixedThreadPool(totalThreadsForPhase1);
        long initialTime = System.currentTimeMillis();
        for (int i = 0; i < totalThreadsForPhase1; i++) {
            executorService1.execute(new Task1(skierStart1, skierEnd1, 1, 90, skiLifts,
                    task1Latch, task1LatchFull, (int) (numRuns * 0.1)));
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

        ExecutorService executorService2 = Executors.newFixedThreadPool(totalThreadsForPhase2);
        for (int i = 0; i < totalThreadsForPhase2; i++) {
            executorService2.execute(new Task1(skierStart2, skierEnd2, 91, 360, skiLifts,
                    task2Latch, task2LatchFull, (int) (numRuns * 0.8)));
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
        int skierEnd3 = skierStart3 + incrementalValueForPhase3 - 1;;

        CountDownLatch task3Latch = new CountDownLatch(totalThreadsForPhase3);
        CountDownLatch task3LatchFull = new CountDownLatch(totalThreadsForPhase3);
        for (int i = 0; i < totalThreadsForPhase3; i++) {
            executorService3.execute(new Task1(skierStart3, skierEnd3, 361, 420, skiLifts,
                    task3Latch, task3LatchFull, (int) (numRuns * 0.1)));
            skierStart3 += incrementalValueForPhase3;
            skierEnd3 += incrementalValueForPhase3;
        }

        System.out.println("Waiting for all phases to complete");
        task3LatchFull.await();
        task1LatchFull.await();
        task2LatchFull.await();

        long endTime = System.currentTimeMillis();
        long wallTime = endTime - initialTime;

        executorService1.shutdown();
        executorService2.shutdown();
        executorService3.shutdown();


        System.out.println("Start time = " + initialTime);
        System.out.println("End time = " + endTime);
        System.out.println("Wall time = " + wallTime);
        System.out.println("Threads = " + maxThreads);

        Client1Data client1Data = new Client1Data(maxThreads, wallTime);
        return client1Data;
    }



    public static void main(String[] args) throws InterruptedException, IOException {
        Client1 client1 = new Client1();
        List<String> list = new ArrayList();
        List<Client1Data> result = new ArrayList();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("client1_input");
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

            Client1Data client1Data = client1.generateLoad(map.get("maxThreads"), map.get("skiers"),
                    map.get("skiLifts"), map.get("numRuns"));
            result.add(client1Data);
        }


        FileWriter csvWriter = new FileWriter("chart1.csv");
        csvWriter.append("Threads");
        csvWriter.append(",");
        csvWriter.append("Wall Time (ms)");
        csvWriter.append("\n");

        for (Client1Data data : result) {
            csvWriter.append(String.valueOf(data.getThreads()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(data.getWallTime()));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }
}
