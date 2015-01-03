package no.rolflekang.fpgrowth;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Preprocessor {
    public void loadDataFile(FileReader fileReader) throws IOException;
    public ArrayList<int[]> getTransactions();
    public List<List<String>> getFrequentItemsetWithLabels(List<List<Integer>> frequentItemsets);
}
