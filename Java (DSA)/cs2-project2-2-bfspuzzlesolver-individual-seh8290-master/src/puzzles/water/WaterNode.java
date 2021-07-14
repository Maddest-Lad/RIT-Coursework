package puzzles.water;

import puzzles.common.solver.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.hash;

public class WaterNode extends Node {

    public static int amount;
    public static int[] capacities;
    public static List<int[]> triedConfigs;
    public int[] buckets;

    // Works as Both The Normal And Copy Constructor
    WaterNode(WaterNode parent, int[] buckets) {
        super(parent);
        this.buckets = buckets;
        update();
        WaterNode.triedConfigs.add(buckets);
    }

    // Go Through Buckets And Fix Any Problems
    public void update() {
        for (int i = 0; i < buckets.length; i++)
        {
            if (buckets[i] > capacities[i])
            {
                buckets[i] = capacities[i];
            }

            if (buckets[i] < 0)
            {
                buckets[i] = 0;
            }
        }
    }

    @Override
    public boolean isGoal() {

        for (int i : buckets)
        {
            if (i == amount)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(buckets);
    }

    public boolean alreadyTried(int[] current) {
        for (int[] arr : WaterNode.triedConfigs)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i] != current[i])
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<Node> generateChildren() {
        List<Node> children = new LinkedList<>();
        List<int[]> bucketCombinations = new LinkedList<>();

        for (int i = 0; i < buckets.length; i++)
        {
            // Configurations Where Only 1 Value Changes (ie Fill / Flush)
            int[] temp = buckets.clone();

            // Fill
            if (buckets[i] < capacities[i])
            {
                temp[i] = capacities[i];

                if (!alreadyTried(temp))
                {
                    bucketCombinations.add(temp);
                }
            }

            // Empty
            if (buckets[i] > 0)
            {
                temp[i] = 0;

                if (!alreadyTried(temp))
                {
                    bucketCombinations.add(temp);
                }
            }

            // Pour From Every Bucket To Every Other Bucket
            for (int j = 0; j < buckets.length; j++)
            {

                int openSpace = Math.abs(capacities[i] - temp[i]);

                if (buckets[j] > openSpace)
                {
                    temp = buckets.clone();
                    temp[i] += openSpace;
                    temp[j] -= openSpace;

                    if (!alreadyTried(temp))
                    {
                        bucketCombinations.add(temp);
                    }
                }

                openSpace = Math.abs(capacities[j] - temp[j]);

                if (buckets[j] > openSpace)
                {
                    temp = buckets.clone();
                    temp[j] += openSpace;
                    temp[i] -= openSpace;

                    if (!alreadyTried(temp))
                    {
                        bucketCombinations.add(temp);
                    }
                }

                temp = buckets.clone();

                temp[i] = temp[j];
                temp[j] = 0;

                if (!alreadyTried(temp))
                {
                    bucketCombinations.add(temp);
                }

                // Can pull off of same array clone
                temp[j] = temp[i];
                temp[i] = 0;

                if (!alreadyTried(temp))
                {
                    bucketCombinations.add(temp);
                }

            }
        }

        for (int[] intArr : bucketCombinations)
        {
            children.add(new WaterNode(this, intArr));
        }

        return children;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WaterNode)
        {
            return Arrays.equals(((WaterNode) obj).buckets, buckets);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return hash(buckets);
    }
}
