package test;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class TestClient {
    public static void startClient() throws InterruptedException {
        StreamObserver<TestResponse> newStreamObserver = new StreamObserver<TestResponse>() {
            @Override
            public void onNext(TestResponse response) {
                System.out.println("Received: " + response.getResponseCode());
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                System.out.println("Response onCompleted");
            }
        };

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 10064)
                .usePlaintext()
                .build();
        TestServiceGrpc.TestServiceStub service = TestServiceGrpc.newStub(channel);
        StreamObserver<TestRequest> process = service.process(newStreamObserver);
        TestRequest hey = TestRequest.newBuilder().setRequestCode("hey").build();
        System.out.println("Sending 'hey' to server");
        process.onNext(hey);
        System.out.println("Sleeping for 1 second");
        Thread.sleep(1000);
        System.out.println("Calling onCompleted on both sides of BiDi stream");
        process.onCompleted();
        newStreamObserver.onCompleted();
        System.out.println("Calling channel shutdown");
        channel.shutdown();
        System.out.println("calling awaitTermination");
        channel.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("done");
    }
}
