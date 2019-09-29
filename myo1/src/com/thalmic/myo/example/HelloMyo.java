package com.thalmic.myo.example;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

import javax.xml.crypto.Data;

/*
 * JVM Arguments to help debug.
 -Xcheck:jni
 -XX:+ShowMessageBoxOnError
 -XX:+UseOSErrorReporting
 */
public class HelloMyo {
	public static void main(String[] args) {
		double roll, pitch, yaw;
		try {
			Hub hub = new Hub("com.example.hello-myo");

			System.out.println("Attempting to find a Myo...");
			Myo myo = hub.waitForMyo(10000);

			if (myo == null) {
				throw new RuntimeException("Unable to find a Myo!");
			}

			System.out.println("Connected to a Myo armband!");
			DeviceListener dataCollector = new DataCollector();
			DataCollector temp = (DataCollector)dataCollector; // Use this to use functions you add to the 'datacollector' object.
			hub.addListener(dataCollector);

			while (true) {
				hub.run(1000 / 20);
				System.out.print(dataCollector);
				/*
				roll = temp.getRoll();
				pitch = temp.getPitch();
				yaw = temp.getYaw();
				*/
			}
		} catch (Exception e) {
			System.err.println("Error: ");
			e.printStackTrace();
			System.exit(1);
		}
	}
}