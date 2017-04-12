package com.cloudzone.cloudlimiter.meter;

import com.cloudzone.cloudlimiter.base.IntervalModel;
import com.cloudzone.cloudlimiter.factory.CloudFactory;
import com.cloudzone.cloudlimiter.limiter.RealTimeLimiter;
import org.junit.Test;

/**
 * @author tantexian<my.oschina.net/tantexian>
 * @since 2017/4/4
 */
public class CloudMeterTest {
    final static private RealTimeLimiter realTimeLimiter = CloudFactory.createRealTimeLimiter(100);
    CloudMeter cloudMeter = CloudFactory.createCloudMeter();

    @Test
    public void printStats() throws Exception {

        cloudMeter.registerListener(new MeterListenerIpml());

        cloudMeter.setIntervalModel(IntervalModel.ALL);
      /*  Topic topic = new Topic();
        topic.setTag("*");*/
        //cloudMeter.setAcquireTopic("mytag66");
        Topic topic = new Topic();
        topic.setTag("mytag777");
        topic.setType("sendMsg");
        cloudMeter.setAcquireTopic(topic);

        // cloudMeter.setAcquireTopic("*");

        for (int i = 0; i < 1000000; i++) {
            if (i == 100) {
                realTimeLimiter.setRate(10);
                System.out.println("setRate(10)");
            } else if (i == 150) {
                realTimeLimiter.setRate(100);
                System.out.println("setRate(100)");
            } else if (i == 600) {
                realTimeLimiter.setRate(1000);
                System.out.println("setRate(1000)");
            }
            realTimeLimiter.acquire();

            Topic topic1 = new Topic();
            topic1.setTag("mytag777");
            topic1.setType("sendMsg");
            cloudMeter.request(topic1);

            cloudMeter.request();
            cloudMeter.request("mytag4");
            cloudMeter.request("mytag66");
            cloudMeter.request("mytag888");
        }
    }

}