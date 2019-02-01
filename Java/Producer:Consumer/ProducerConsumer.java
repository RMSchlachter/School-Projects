// Create bounded buffer (FIFO) with 1000 element cap
// Create producer that can produce 1,000,000 random doubles
// Create consumer that consumes elements from the buffer,
//		if the buffer is empty, consumer must wait for the 
//		producer to add an item to the buffer

import java.util.Random;

	class Buffer extends Thread{
		final int maxBuff;
		int head, tail, totalConsume, buffSize;
		double buffAccum;
		double[] buffQueue;

		public Buffer() {
			maxBuff = 1000;
			head = 0;
			tail = 0;
			totalConsume = 0;
			buffSize = 0;						// if -1, no elements in buffer
			buffQueue = new double[maxBuff];
			buffAccum = 0;
		}

		// will insert at end of array
		public synchronized void insert(double toInsert) {
			//System.out.println("insert(): inserting " + toInsert);
			try {
				while(buffSize == maxBuff) {		// buffer is full
					//System.out.println("insert(): buffer is full. buffSize = " + buffSize);
					wait();		
				}

				tail = (tail + 1) % maxBuff;		// set tail location
				buffQueue[tail] = toInsert;
				buffAccum += toInsert;
				buffSize++;
				notify();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		// Consumer
		public synchronized double delete() {
			double remove = 0;
			//System.out.println("delete(): deleting " + buffQueue[head]);
			try {
				while(buffSize == 0) {
					wait();
				}

				remove = buffQueue[head];	// remove from front
				head = (head + 1) % maxBuff;		// when head points to tail, put head to front (0) again
				buffSize--;
				notify();	

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			return remove;
		}

		public void printBuffer() {
			for(int i = 0; i <= 1000; i++) {
				System.out.println("Buffer[" + i + "]: " + buffQueue[i]);
			}
			//System.out.println("Buffed 1000000 items, Cumulative value of buffed items = " + buffAccum);
		}
	}


	class Producer extends Thread {
		Buffer buffer;
		final int maxProd;
		int prodGenerated;
		double prodAccum;

		public Producer(Buffer b) {
			buffer = b;
			maxProd = 1000000000;
			prodGenerated = 0;
			prodAccum = 0;
		}

		public void run() {
			//System.out.println("Got to prodStart()");
			// output every 100,000

			//try {
				while(!Thread.currentThread().isInterrupted()) {
					Random random = new Random();
					Double bufferElement = random.nextDouble() * 100.0;	
					prodGenerated++;
					//System.out.println("prodGenerated: " + prodGenerated);
					prodAccum += bufferElement;
					buffer.insert(bufferElement);

					if(prodGenerated == 100000 || prodGenerated == 200000 || prodGenerated == 300000 || prodGenerated == 400000 ||
	 					prodGenerated == 500000 || prodGenerated == 600000 || prodGenerated == 700000 || prodGenerated == 800000 ||
	 					prodGenerated == 900000 || prodGenerated == 1000000) {
							
							prodPrint();
					}
					if(prodGenerated == 1000000) {
						System.out.println("Producer: Finished generating 1,000,000 items");
						return;
					}
				}
			} //catch (InterruptedException e) {
			//	Thread.currentThread().interrupt();
			//}


		public void prodPrint() {
			System.out.println("Producer: Produced " + prodGenerated + " items, Cumulative value of consumed items = " + prodAccum);
		}
	}

	class Consumer extends Thread {
		private final Buffer buffer;
		int totalConsume;
		double consAccum;

		public Consumer(Buffer b) {
			buffer = b;
			totalConsume = 0;
			consAccum = 0;
		}

		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				consAccum += buffer.delete();
				totalConsume++;

				if(totalConsume == 100000 || totalConsume == 200000 || totalConsume == 300000 || totalConsume == 400000 ||
					totalConsume == 500000 || totalConsume == 600000 || totalConsume == 700000 || totalConsume == 800000 ||
					totalConsume == 900000 || totalConsume == 1000000) {

					consPrint(buffer);
				}

				if(totalConsume == 1000000) {
					System.out.println("Consumer: Finished consuming 1,000,000 items");
					return;
				}
			}
		}

		public void consPrint(Buffer b) {
			System.out.println("Consumer: Consumed " + totalConsume + " items, Cumulative value of consumed items = " + b.buffAccum);
		}
	}

class ProducerConsumer {
	public static void main(String[] args) throws InterruptedException {
		Buffer buffer = new Buffer();
		Producer producer = new Producer(buffer);
		Consumer consumer = new Consumer(buffer);
		producer.start();
		consumer.start();

		try {
			producer.join();
			consumer.interrupt();
		} catch (InterruptedException e) {}

		buffer.interrupt();
		producer.interrupt();
		//buffer.printBuffer();
		System.out.println("Exiting.");
	}
}