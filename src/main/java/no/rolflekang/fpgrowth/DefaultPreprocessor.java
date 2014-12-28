package no.rolflekang.fpgrowth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultPreprocessor implements Preprocessor {
    private ArrayList<int[]> transactions;

    @Override
    public void loadDataFile(FileReader fileReader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            transactions.add(parseTransactionString(line));
        }
        bufferedReader.close();
    }

    @Override
    public ArrayList<int[]> getTransactions() {
        return transactions;
    }

    @Override
    public List<List<String>> getFrequentItemsetWithLabels(List<List<Integer>> frequentItemsets) {
        List<List<String>> output = new ArrayList<List<String>>();
        for (List<Integer> itemset : frequentItemsets) {
            List<String> newItemset = new ArrayList<String>();
            for (Integer item : itemset) {
                newItemset.add(item.toString());
            }
            output.add(newItemset);
        }
        return output;
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
