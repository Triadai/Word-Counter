package shake_n_bacon;

import providedCode.*;

/**
 * @author <name>
 * @UWNetID <uw net id>
 * @studentID <id number>
 * @email <email address>
 * <p/>
 * <p/>
 * 1. You may implement HashTable with open addressing discussed in
 * class; You can choose one of those three: linear probing, quadratic
 * probing or double hashing. The only restriction is that it should not
 * restrict the size of the input domain (i.e., it must accept any key)
 * or the number of inputs (i.e., it must grow as necessary).
 * <p/>
 * 2. Your HashTable should rehash as appropriate (use load factor as
 * shown in the class).
 * <p/>
 * 3. To use your HashTable for WordCount, you will need to be able to
 * hash strings. Implement your own hashing strategy using charAt and
 * length. Do NOT use Java's hashCode method.
 * <p/>
 * 4. HashTable should be able to grow at least up to 200,000. We are not
 * going to test input size over 200,000 so you can stop resizing there
 * (of course, you can make it grow even larger but it is not necessary).
 * <p/>
 * 5. We suggest you to hard code the prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt NOTE: Make sure you
 * only hard code the prime numbers that are going to be used. Do NOT
 * copy the whole list!
 * <p/>
 */
public class HashTable_OA extends DataCounter {

    int size = 0;
    DataCount[] table;
    double loadFactor = .70;
    Hasher h;
    int[] primes = {31, 101, 227, 499, 1049, 2207, 4639, 6043, 12149, 26083, 56501, 108827, 206197, 413201, 855851, 1299827};
    int primeCount = 0;
    Comparator<String> c;
    int probe = 1;

    public HashTable_OA(Comparator<String> c, Hasher h) {
        table = new DataCount[nextPrime()];
        this.h = h;
        this.c = c;
    }

    private int nextPrime() {
        return primes[primeCount++];
    }

    private void doubleTable() {
        DataCount[] newTable = new DataCount[nextPrime()];
        SimpleIterator si = getIterator();
        table = newTable;
        while (si.hasNext()) {
            insert(si.next());
        }
    }

    private void insert(DataCount data) {
        int index = hash(data.data);
        int nextIndex = index;
        int count = 0;
        while (count < table.length) {
            if (table[nextIndex] == null) {
                table[nextIndex] = data;
                return;
            }
            nextIndex = (nextIndex + probe) % table.length;
            count++;
        }
    }

    @Override
    public void incCount(String data) {
        int index = hash(data);
        int nextIndex = index;
        int count = 0;
        while (count < table.length) {
            if (table[nextIndex] == null) {
                table[nextIndex] = new DataCount(data, 1);
                size++;
                break;
            } else if (c.compare(table[nextIndex].data, data) == 0) {
                table[nextIndex].count++;
                return;
            }
            nextIndex = (nextIndex + probe) % table.length;
            count++;
        }

        if (size / (double) table.length >= loadFactor) {
            doubleTable();
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getCount(String data) {
        int index = hash(data);
        if (table[index] == null) {
            return 0;
        }
        int nextIndex = index;
        int count = 0;
        while (count < table.length) {
            if (c.compare(table[nextIndex].data, data) == 0) {
                return table[nextIndex].count;
            }
            nextIndex = (index + probe) % table.length;
            count++;
        }

        return 0;
    }

    private int hash(String data) {
        return h.hash(data) % table.length;
    }

    @Override
    public SimpleIterator getIterator() {
        DataCount[] data = new DataCount[size];
        int count = 0;
        int i = 0;
        while (true) {
            if (count < data.length) {
                if (table[i] != null) {
                    data[count] = table[i];
                    count++;
                }
            } else {
                break;
            }
            i++;
        }
        return new MyIterator(data);
    }

}