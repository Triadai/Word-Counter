package shake_n_bacon;

import providedCode.Hasher;

public class StringHasher implements Hasher {

    @Override
    public int hash(String str) {
        int prime = 7;
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = prime * hash + str.charAt(i);
        }
        if (hash < 0) {
            hash = -hash;
        }
        return hash;
    }
}
