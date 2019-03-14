package com.tibrep;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;
import com.tibrep.HelloServiceInterface;

public class HelloServiceConsumerVerticle extends AbstractVerticle {
    // Lets define the hello-type service here
    String serviceName = "hello-service";
    private ServiceDiscovery service;
    private ServiceReference helloSerRef;
    @Override
    public void start() throws Exception {
        super.start();
        System.out.println("Consumer Verticle Started");
         service = ServiceDiscovery.create(Vertx.vertx());

    //  ser.getRecord( r -> serviceName.equals(r.getName()),
        service.getRecord( r -> {return true;},
                      ar -> {
                System.out.println("Found the Record");
                if(ar.result() == null)
                    System.out.println("ar.result is NULL ");

               if( ar.succeeded() && ar.result() != null ){

    //                helloSerRef = ser.getReference(ar.result());
    //                HelloServiceInterface helloSer = helloSerRef.get();
    //               helloSer.sayHello("Mr Sudhir");
                   System.out.println("Found the Hello Service" + serviceName + ar.result().getType());
               }
               else
               {
                   System.out.println("Could found the Hello Service" + serviceName);
               }
        });
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Consumer Verticle Stoped");

        helloSerRef.release();
        service.close();
        super.stop();
    }
}
