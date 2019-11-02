/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;


import java.util.Random;

/**
 *
 * @author Admin
 */

class ProducerConsumer implements Runnable {
    BoundedBufferMonitor b = null;
    
    public ProducerConsumer(BoundedBufferMonitor initb) {
        b = initb;
        new Thread(this).start();
    }
    public void run() {
        int item;
        Random r = new Random();
        while (true) {
            item = r.nextInt(100);
            System.out.println("Thread 1: " + item);
            b.deposit(item);
            
            
            Util.mySleep(1000);

        }
    }
}
class Consumer implements Runnable {
    BoundedBufferMonitor b = null;
    public Consumer(BoundedBufferMonitor initb) {
        b = initb;
        new Thread(this).start();
    }
  
    public boolean isNT(int item){
        if(item < 2){
            return false;
        }
        for(int i = 2; i <= Math.sqrt(item); i++){
            if(item % i == 0){
                return false;
            }
        }
        return true;
        
    }
    
    public void run() {
        int item;
        while (true) {
//            Util.mySleep(1000);

            item = (int) b.fetch();
            if(isNT(item)){
                System.out.println("Thread 2: " + item  + " Là số nguyên tố");
            }
            else{
                System.out.println("Thread 2: " + item + " Không là số nguyên tố");
            }
//            System.out.println("Thread 2: " + item);
             Util.mySleep(2000);
             
        }
        
    }
}

class Producer {
    public static void main(String[] args) {
        BoundedBufferMonitor buffer = new BoundedBufferMonitor();
                ProducerConsumer producer = new ProducerConsumer(buffer);

        Consumer consumer = new Consumer(buffer);
    }
}