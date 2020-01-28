# grpc-issue-6642

Test case to show problem described in https://github.com/grpc/grpc-java/issues/6642

To run:

```
mvn verify exec:java
```

You should see (after the Maven build):

```
Starting server...
Starting client...
Sending 'hey' to server
Sleeping for 1 second
Received: hey
Calling onCompleted on both sides of BiDi stream
Response onCompleted
Calling channel shutdown
calling awaitTermination
```

It's hanging at https://github.com/Randgalt/grpc-issue-6642/blob/master/src/main/java/test/TestClient.java#L44
