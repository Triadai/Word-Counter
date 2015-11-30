package shake_n_bacon;

import providedCode.*;

/**
 * @author <name>
 * @UWNetID <uw net id>
 * @studentID <id number>
 * @email <email address>
 * <p/>
 * TODO: Replace this comment with your own as appropriate.
 * <p/>
 * 1. You may implement HashTable with separate chaining discussed in
 * class; the only restriction is that it should not restrict the size of
 * the input domain (i.e., it must accept any key) or the number of
 * inputs (i.e., it must grow as necessary).
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
 * TODO: Develop appropriate tests for your HashTable.
 */
public class HashTable_SC extends DataCounter {

    int size = 0;
    Node[] table;
    double loadFactor = .75;
    Hasher h;
    int[] primes = {31, 101, 227, 499, 1049, 2207, 4639, 6043, 12149, 26083, 56501, 108827, 206197, 413201, 855851, 1299827};
    int primeCount = 0;
    Comparator<String> c;

    public HashTable_SC(Comparator<String> c, Hasher h) {
        table = new Node[nextPrime()];
        this.h = h;
        this.c = c;
    }

    private int nextPrime() {
        return primes[primeCount++];
    }

    private void doubleTable() {
        Node[] newTable = new Node[nextPrime()];
        SimpleIterator si = getIterator();
        table = newTable;
        while (si.hasNext()) {
            insert(si.next());
        }
    }

    private void insert(DataCount data) {
        int index = hash(data.data);
        if (table[index] == null) {
            Node n = new Node(data);
            table[index] = n;
        } else {
            Node currNode = table[index];
            while (currNode.next != null) {
                currNode = currNode.next;
            }
            currNode.next = new Node(data);
        }
    }

    @Override
    public void incCount(String data) {
        int index = hash(data);
        if (table[index] == null) {
            Node n = new Node(data);
            table[index] = n;
        } else {
            Node currNode = table[index];
            while (currNode.next != null) {
                if (c.compare(currNode.data.data, data) == 0) {
                    currNode.data.count++;
                    return;
                }
                currNode = currNode.next;
            }
            currNode.next = new Node(data);
        }
        size++;
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
            System.err.println(data + " is not in the table");
            return -1;
        }
        Node currNode = table[index];
        while (currNode != null) {
            if (c.compare(currNode.data.data, data) == 0) {
                return currNode.data.count;
            }
            currNode = currNode.next;
        }
        System.err.println(data + " is not in the table");
        return -1;
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
                    Node currNode = table[i];
                    while (currNode != null) {
                        data[count] = currNode.data;
                        currNode = currNode.next;
                        count++;
                    }
                }
            } else {
                break;
            }
            i++;
        }
        return new MyIterator(data);
    }

    class Node {
        DataCount data;
        Node next;

        public Node(String keyword) {
            data = new DataCount(keyword, 1);
        }

        public Node(DataCount data) {
            this.data = data;
        }

    }


}