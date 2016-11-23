package mr.joins;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.hadoop.util.bloom.BloomFilter;
import org.apache.hadoop.util.bloom.Key;
import org.apache.hadoop.util.hash.Hash;

public class CreateSampleBloomFilter {

    public static void main(String[] args) throws IOException {
        BloomFilter bloom = new BloomFilter(
                100, // vector size
                10, // number of hash function
                Hash.JENKINS_HASH); // hash type
        bloom.add(new Key("user1".getBytes()));
        bloom.add(new Key("user4".getBytes()));
        DataOutputStream out = new DataOutputStream(new FileOutputStream(
                new File("src/main/resources/mr/reviews/joins/userBloomFilter.bloom")));
        bloom.write(out);
        out.close();
    }

}
