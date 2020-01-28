package test;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class TestServer extends TestServiceGrpc.TestServiceImplBase {
    public static void startServer() throws Exception {
        Server server = ServerBuilder.forPort(10064)
                .addService(new TestServer())
                .build();
        server.start();
    }

    @Override
    public StreamObserver<TestRequest> process(StreamObserver<TestResponse> responseObserver) {
        return new StreamObserver<TestRequest>() {
            @Override
            public void onNext(TestRequest value) {
                TestResponse response = TestResponse.newBuilder().setResponseCode(value.getRequestCode()).build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        };
    }
}
