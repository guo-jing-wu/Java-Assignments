package paystation.domain;
import java.util.*;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    private int money;
    /**
     * The key is the coin type and the associated value is the
     * number of these coins that are returned.
     */
    private int five;
    private int ten;
    private int twentyfive;
    /**
     * The Map object is never null even if no coins are returned.
     * The Map will only contain only keys for coins to be returned.
     * The Map will be cleared after a cancel or buy.
     */
    HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: map.put(5,five+1);
                break;
            case 10: map.put(10,ten+1);
                break;
            case 25: map.put(25,twentyfive+1);
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        money += insertedSoFar;
        reset();
        map.clear();
        return r;
    }

    @Override
    public Map<Integer,Integer> cancel() {
        reset();
        Map<Integer,Integer> copy = new HashMap(map);
        map.clear();
        return copy;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
    }
    
    @Override
    public int empty() {
        int sum = money;
        money = 0;
        reset();
        return sum;
    }
}
