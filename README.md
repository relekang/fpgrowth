# FPGrowth [![Build status](https://ci.frigg.io/badges/relekang/fpgrowth/)](https://ci.frigg.io/relekang/fpgrowth/last/) [![Coverage status](https://ci.frigg.io/badges/coverage/relekang/fpgrowth/)](https://ci.frigg.io/relekang/fpgrowth/last/)
A java implementation of the FPGrowth algorithm

## Usage
**Create a jar and run it:**
```bash
git clone git@github.com:relekang/fpgrowth.git && cd fpgrowth
mvn assembly:assembly
java -jar target/FPGrowth-jar-with-dependencies.jar <path-to-file> <minimum support>
```

**Or use it in code:**

```java
double minSupport = 0.9;
boolean pruneTreeOnInsert = true;
Map<Integer, Integer> oneItemsetCounts = FPGrowth.getOneItemsetCounts(transactions);
List<List<Integer> frequentItemsets = FPGrowth.findFrequentItemsetWithSuffix(
  FPGrowth.buildFPTree(transactions, oneItemsetCounts, pruneTreeOnInsert, minSupport),
  new ArrayList<Integer>(),
  ((int) minSupport * transactions.size())
);
```

MIT © Rolf Erik Lekang
