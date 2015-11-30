package shake_n_bacon;

import providedCode.DataCount;
import providedCode.SimpleIterator;

/**
 * Created by benawad on 11/29/15.
 */
public class MyIterator implements SimpleIterator {

    DataCount[] data;
    int curr = 0;

    public MyIterator(DataCount[] data) {
        this.data = data;
    }

    @Override
    public DataCount next() {
        return data[curr++];
    }

    @Override
    public boolean hasNext() {
        return (curr < data.length);
    }
}
