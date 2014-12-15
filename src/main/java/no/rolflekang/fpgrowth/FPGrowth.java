package no.rolflekang.fpgrowth;

import com.google.common.primitives.Ints;

import java.io.*;
import java.util.*;

public class FPGrowth {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("FPGrowth take exactly two arguments");
            System.exit(1);
        }
        ArrayList<int[]> transactions = new ArrayList<int[]>();
        System.out.println("Running FPGrowth on file: " + args[0]);
        double minSupport = Double.parseDouble(args[1]);
        File file = new File(args[0]);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                transactions.add(parseTransactionString(line));
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Could not read the file");
            System.exit(1);
        }

        Map<Integer, Integer> oneItemsetCounts = getOneItemsetCounts(transactions);
        System.out.println(findFrequentItemsetWithSuffix(
            buildFPTree(transactions, oneItemsetCounts, true, minSupport),
            new ArrayList<Integer>(),
            ((int) minSupport * transactions.size())
        ));
    }

    public static Map<Integer, Integer> getOneItemsetCounts(List<int[]> transactions) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        return addToOneItemsetCounts(counts, transactions);
    }

    public static Map<Integer, Integer> addToOneItemsetCounts(Map<Integer, Integer> counts, List<int[]> transactions) {
        for (int[] itemset : transactions) {
            for (int item : itemset) {
                Integer count = counts.get(item);
                if (count == null) count = 0;
                count++;
                counts.put(item, count);
            }
        }
        return counts;
    }

    public static FPTree buildFPTree(List<int[]> transactions, Map<Integer, Integer> oneItemsetCounts, boolean pruneBeforeInsert, double minSupport) {
        FPTree tree = new FPTree();
        return addToFPTree(tree, transactions, oneItemsetCounts, pruneBeforeInsert, minSupport);
    }

    public static FPTree addToFPTree(FPTree tree, List<int[]> transactions, Map<Integer, Integer> oneItemsetCounts, boolean pruneBeforeInsert, double minSupport) {
        for (int[] transaction : transactions) {
            List<Integer> frequent = new ArrayList<Integer>();
            transaction = sortTransaction(transaction, oneItemsetCounts);
            for (int item : transaction) {
                if (!pruneBeforeInsert || oneItemsetCounts.get(item) / (double) transactions.size() >= minSupport) {
                    frequent.add(item);
                } else {
                    break;
                }
            }
            tree.addTransaction(frequent);
        }
        return tree;
    }


    public static int[] sortTransaction(int[] transaction, Map<Integer, Integer> oneItemsetCounts) {
        List<Integer> list = Ints.asList(transaction);
        Collections.sort(list, new ItemsetComparator(oneItemsetCounts));
        return Ints.toArray(list);
    }


    public static List<List<Integer>> findFrequentItemsetWithSuffix(FPTree tree, List<Integer> suffix, int minSupportCount) {
        List<List<Integer>> frequentItemset = new ArrayList<List<Integer>>();
        for (Integer item : tree.getItems().keySet()) {
            int support = tree.getSupportForItem(item);
            if (support >= minSupportCount && !suffix.contains(item)) {
                List<Integer> found = new ArrayList<Integer>();
                found.addAll(suffix);
                found.add(item);
                frequentItemset.add(found);

                FPTree conditionalTree = FPTree.conditionalTree(tree.getPrefixPaths(item), minSupportCount);
                frequentItemset.addAll(findFrequentItemsetWithSuffix(conditionalTree, found, minSupportCount));
            }
        }
        return frequentItemset;
    }

    public static int[] parseTransactionString(String str) {
        String[] strArray = str.split(" ");
        int[] transaction = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            transaction[i] = Integer.parseInt(strArray[i]);
        }
        return transaction;
    }


}