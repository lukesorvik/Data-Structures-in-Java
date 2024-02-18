package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable> extends FixedSizeFIFOWorkList<E> {

    /*
    * Array under the hood, represented kinda like a donut
    * ints for front and back
    *
    * */

    int capacity; //how many elements can fit in the array
    E[] array; //array for the class
    public int front = 0; //pointer to the index of the oldest item in queue "front", points to front of worklist
    public int back = 0;//pointer to the index of the most recently added item in queue "back", points to back of worklist
    int size = 0; //keeps track of how many elements are added, worklist size



    public CircularArrayFIFOQueue(int capacity) {
        super(capacity); //calls the superclasses constructor method with the parameter (capacity)
        //^ this should cause an exception if the user inputs 0

        this.capacity = capacity; //sets the capacity variable for this class
        this.array = (E[])new Comparable[capacity];


    }

    /**
     * Adds work to the worklist. This method should conform to any additional
     * contracts that the particular type of worklist has.
     *
     * @param work the work to add to the worklist
     * @throws IllegalStateException iff isFull()
     * @precondition isFull() is false
     */
    @Override
    public void add(E work) {
        //add to the back of the queue

        //if is full (size >= capacity then throw exception)
        //uses isfull() method from superclass
        if(isFull()) {
            throw new IllegalStateException();
        }
        //need to check if front is empty tho

        array[back] = work;
        size++;
        back = (back+1) % capacity; //sets the back pointer to the mod of the array
    }



    /**
     * Returns the next element of the worklist.
     *
     * @return the next element in this worklist
     * @throws NoSuchElementException if hasWork() is false
     * @precondition hasWork() is true
     * @postcondition return(peek ()) is return(next())
     * @postcondition the structure of this worklist remains unchanged.
     */
    @Override
    public E peek() {
        //if has work is false
        if (!hasWork()) { //checks if this.size = 0
            throw new NoSuchElementException();
        }

        return array[front]; //gets the element next to leave
    }


    /**
     * Returns a view of the ith element of the worklist. Since this worklist is
     * a FIFO worklist, it has a well-defined order.
     *
     * @param i the index of the element to peek at
     * @return the ith element in this worklist
     * @throws NoSuchElementException    if hasWork() is false (this exception takes precedence over
     *                                   all others)
     * @throws IndexOutOfBoundsException if i < 0 or i >= size()
     * @precondition 0 <= i < size()
     * @postcondition the structure of this worklist remains unchanged
     */

    @Override
    public E peek(int i) {
        //if has work is false
        if (this.size<= 0) { //checks if this.size = 0
            throw new NoSuchElementException();
        }

        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        //we want the ith element from the front of the work order
        //since circular we could have first=9 and back =3

        return array[(front+i) % capacity]; //gets the remainder of the start of the queue / size of array
        //should work if we wrap around to front
    }


    /**
     * Returns and removes the next element of the worklist
     *
     * @return the next element in this worklist
     * @throws NoSuchElementException if hasWork() is false
     * @precondition hasWork() is true
     * @postcondition return(next ()) ++ after(next()) == before(next())
     * @postcondition after(size ()) + 1 == before(size())
     */
    @Override
    public E next() {
        if (this.size<= 0) {
            throw new NoSuchElementException();
        }

        //we want the element in the front of the line
        E result = array[front];
        this.array[front] = null;
        front = (front+1) % capacity; //mod capacity since it may be circular so we want to set the front accurately
        size--;//decrement the size of the worklist

        return result;


    }


    /**
     * Replaces the ith element of this worklist with value. Since this worklist
     * is a FIFO worklist it has a well-defined order.
     *
     * @param i     the index of the element to update
     * @param value the value to update index i with
     * @throws NoSuchElementException    if hasWork() is false (this exception takes precedence over
     *                                   all others)
     * @throws IndexOutOfBoundsException if i < 0 or i >= size()
     * @precondition 0 <= i < size()
     * @postcondition only the ith element of the structure is changed
     */
    @Override
    public void update(int i, E value) {
        if (!hasWork()) { //checks if this.size = 0
            throw new NoSuchElementException();
        }

        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        array[(front+i) % capacity] = value;
    }

    /**
     * Returns the number of elements of work remaining in this worklist
     *
     * @return the size of this worklist
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Resets this worklist to the same state it was in right after
     * construction.
     */
    @Override
    public void clear() {
        E[] temp = (E[])new Comparable[capacity]; //create new empty list
        array = temp; //set our main list to the empty one
        size = 0; //update working size to zero since we no longer have any valid elements
        front = 0;
        back = 0;
    }








     /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param   o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.

        //get smallest size of the two
         //get the minimum size of the two lists
        int minSize = Math.min(this.size(), other.size());

        //iterate through the lists and compare the elements
        //the first unequal pair of elements determines the order of the lists "lexicographically: like how dictionarry puts "apple" before "eggplant" because a comes before e"
        for (int i = 0; i < minSize; i++) {
            if (this.peek(i).compareTo(other.peek(i)) != 0) { //if the two elements are not the same
                return this.peek(i).compareTo(other.peek(i)); //return the comparison of the two elements
            }
        }

        //maybe the val it returns matters (cant just be 1 or -1)
        return this.size() - other.size();


    }



     /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param   obj   the reference object with which to compare.
     * @return  {@code true} if this object is the same as the obj
     *          argument; {@code false} otherwise.
     * @see     #hashCode()
     * @see     java.util.HashMap
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {

        //instead of comparing the pointers, we want to compare the actual objects
        //that is why we implement our own equals method
        if (this == obj) { //if pointers are the same
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) { //if the object is not an instance of the same class
            return false;
        } else { //if the object is an instance of the same class, so it is a FixedSizeFIFOWorkList

            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            //check if object is in current array
            //check if the lengths are the same
            //iterate through each object and check if they are the same
            //could we compare pointers?

            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj; //cast the object to a FixedSizeFIFOWorkList
           

            //compare the sizes of the two lists
            if(this.size() != other.size()) {
                return false;
            }
                

            //at this point we know they have the same size, so now compare each element
            //since FixedSizeFIFOWorkList has an iterator, we can use that to compare the two lists
            //compare each element in the list to the other list, if one is different they are not equal
            for (int i = 0; i < this.size(); i++) {
                if (other.peek(i).equals(this.peek(i)) == false) { //if the current element is not the same as the element in the other list
                    //we can use .equals since fixedsizefifoworklist is implementing comparable
                    return false;
                }
            }

            return true; //did not find one that was different
        }
    }


    @Override
    public int hashCode() {
        int result = 0;
        int relativelyPrime = 31; //relatively prime number to use for hashing, maybe make function to check for gcd if tests dont pass
        //cannot be a multiple of the size of hash table

        //get the sum of elements in the curcular array
        for (int i = 0; i < size; i++) {
            if (array[i] != null) { //if the element is not null
                E val = array[front+i % array.length];
                result += val.hashCode() * Math.pow(relativelyPrime, i); //get the hashcode of the element and add it to the result
            }

        }
        return result;
    }
    
}
