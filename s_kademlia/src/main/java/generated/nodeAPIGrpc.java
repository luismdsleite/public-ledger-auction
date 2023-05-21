package generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Msg Handler with lamport clocks support.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.1)",
    comments = "Source: NodeAPI.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class nodeAPIGrpc {

  private nodeAPIGrpc() {}

  public static final String SERVICE_NAME = "proto.nodeAPI";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<generated.NodeAPI.NodeProto,
      generated.NodeAPI.NodesClose> getFindNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findNode",
      requestType = generated.NodeAPI.NodeProto.class,
      responseType = generated.NodeAPI.NodesClose.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.NodeAPI.NodeProto,
      generated.NodeAPI.NodesClose> getFindNodeMethod() {
    io.grpc.MethodDescriptor<generated.NodeAPI.NodeProto, generated.NodeAPI.NodesClose> getFindNodeMethod;
    if ((getFindNodeMethod = nodeAPIGrpc.getFindNodeMethod) == null) {
      synchronized (nodeAPIGrpc.class) {
        if ((getFindNodeMethod = nodeAPIGrpc.getFindNodeMethod) == null) {
          nodeAPIGrpc.getFindNodeMethod = getFindNodeMethod =
              io.grpc.MethodDescriptor.<generated.NodeAPI.NodeProto, generated.NodeAPI.NodesClose>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.NodeProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.NodesClose.getDefaultInstance()))
              .setSchemaDescriptor(new nodeAPIMethodDescriptorSupplier("findNode"))
              .build();
        }
      }
    }
    return getFindNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.NodeAPI.NodeProto,
      generated.NodeAPI.NodeProto> getPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ping",
      requestType = generated.NodeAPI.NodeProto.class,
      responseType = generated.NodeAPI.NodeProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.NodeAPI.NodeProto,
      generated.NodeAPI.NodeProto> getPingMethod() {
    io.grpc.MethodDescriptor<generated.NodeAPI.NodeProto, generated.NodeAPI.NodeProto> getPingMethod;
    if ((getPingMethod = nodeAPIGrpc.getPingMethod) == null) {
      synchronized (nodeAPIGrpc.class) {
        if ((getPingMethod = nodeAPIGrpc.getPingMethod) == null) {
          nodeAPIGrpc.getPingMethod = getPingMethod =
              io.grpc.MethodDescriptor.<generated.NodeAPI.NodeProto, generated.NodeAPI.NodeProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.NodeProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.NodeProto.getDefaultInstance()))
              .setSchemaDescriptor(new nodeAPIMethodDescriptorSupplier("ping"))
              .build();
        }
      }
    }
    return getPingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.NodeAPI.StoreRequest,
      generated.NodeAPI.StoreResponse> getStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "store",
      requestType = generated.NodeAPI.StoreRequest.class,
      responseType = generated.NodeAPI.StoreResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.NodeAPI.StoreRequest,
      generated.NodeAPI.StoreResponse> getStoreMethod() {
    io.grpc.MethodDescriptor<generated.NodeAPI.StoreRequest, generated.NodeAPI.StoreResponse> getStoreMethod;
    if ((getStoreMethod = nodeAPIGrpc.getStoreMethod) == null) {
      synchronized (nodeAPIGrpc.class) {
        if ((getStoreMethod = nodeAPIGrpc.getStoreMethod) == null) {
          nodeAPIGrpc.getStoreMethod = getStoreMethod =
              io.grpc.MethodDescriptor.<generated.NodeAPI.StoreRequest, generated.NodeAPI.StoreResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "store"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.StoreRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.StoreResponse.getDefaultInstance()))
              .setSchemaDescriptor(new nodeAPIMethodDescriptorSupplier("store"))
              .build();
        }
      }
    }
    return getStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.NodeAPI.FindRequest,
      generated.NodeAPI.FindResponse> getFindValueMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findValue",
      requestType = generated.NodeAPI.FindRequest.class,
      responseType = generated.NodeAPI.FindResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.NodeAPI.FindRequest,
      generated.NodeAPI.FindResponse> getFindValueMethod() {
    io.grpc.MethodDescriptor<generated.NodeAPI.FindRequest, generated.NodeAPI.FindResponse> getFindValueMethod;
    if ((getFindValueMethod = nodeAPIGrpc.getFindValueMethod) == null) {
      synchronized (nodeAPIGrpc.class) {
        if ((getFindValueMethod = nodeAPIGrpc.getFindValueMethod) == null) {
          nodeAPIGrpc.getFindValueMethod = getFindValueMethod =
              io.grpc.MethodDescriptor.<generated.NodeAPI.FindRequest, generated.NodeAPI.FindResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findValue"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.FindRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.FindResponse.getDefaultInstance()))
              .setSchemaDescriptor(new nodeAPIMethodDescriptorSupplier("findValue"))
              .build();
        }
      }
    }
    return getFindValueMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static nodeAPIStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<nodeAPIStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<nodeAPIStub>() {
        @java.lang.Override
        public nodeAPIStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new nodeAPIStub(channel, callOptions);
        }
      };
    return nodeAPIStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static nodeAPIBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<nodeAPIBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<nodeAPIBlockingStub>() {
        @java.lang.Override
        public nodeAPIBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new nodeAPIBlockingStub(channel, callOptions);
        }
      };
    return nodeAPIBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static nodeAPIFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<nodeAPIFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<nodeAPIFutureStub>() {
        @java.lang.Override
        public nodeAPIFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new nodeAPIFutureStub(channel, callOptions);
        }
      };
    return nodeAPIFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Msg Handler with lamport clocks support.
   * </pre>
   */
  public static abstract class nodeAPIImplBase implements io.grpc.BindableService {

    /**
     */
    public void findNode(generated.NodeAPI.NodeProto request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.NodesClose> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindNodeMethod(), responseObserver);
    }

    /**
     */
    public void ping(generated.NodeAPI.NodeProto request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.NodeProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }

    /**
     */
    public void store(generated.NodeAPI.StoreRequest request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.StoreResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStoreMethod(), responseObserver);
    }

    /**
     */
    public void findValue(generated.NodeAPI.FindRequest request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.FindResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindValueMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFindNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generated.NodeAPI.NodeProto,
                generated.NodeAPI.NodesClose>(
                  this, METHODID_FIND_NODE)))
          .addMethod(
            getPingMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generated.NodeAPI.NodeProto,
                generated.NodeAPI.NodeProto>(
                  this, METHODID_PING)))
          .addMethod(
            getStoreMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generated.NodeAPI.StoreRequest,
                generated.NodeAPI.StoreResponse>(
                  this, METHODID_STORE)))
          .addMethod(
            getFindValueMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generated.NodeAPI.FindRequest,
                generated.NodeAPI.FindResponse>(
                  this, METHODID_FIND_VALUE)))
          .build();
    }
  }

  /**
   * <pre>
   * Msg Handler with lamport clocks support.
   * </pre>
   */
  public static final class nodeAPIStub extends io.grpc.stub.AbstractAsyncStub<nodeAPIStub> {
    private nodeAPIStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected nodeAPIStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new nodeAPIStub(channel, callOptions);
    }

    /**
     */
    public void findNode(generated.NodeAPI.NodeProto request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.NodesClose> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ping(generated.NodeAPI.NodeProto request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.NodeProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void store(generated.NodeAPI.StoreRequest request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.StoreResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findValue(generated.NodeAPI.FindRequest request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.FindResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindValueMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Msg Handler with lamport clocks support.
   * </pre>
   */
  public static final class nodeAPIBlockingStub extends io.grpc.stub.AbstractBlockingStub<nodeAPIBlockingStub> {
    private nodeAPIBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected nodeAPIBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new nodeAPIBlockingStub(channel, callOptions);
    }

    /**
     */
    public generated.NodeAPI.NodesClose findNode(generated.NodeAPI.NodeProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindNodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public generated.NodeAPI.NodeProto ping(generated.NodeAPI.NodeProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }

    /**
     */
    public generated.NodeAPI.StoreResponse store(generated.NodeAPI.StoreRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStoreMethod(), getCallOptions(), request);
    }

    /**
     */
    public generated.NodeAPI.FindResponse findValue(generated.NodeAPI.FindRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindValueMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Msg Handler with lamport clocks support.
   * </pre>
   */
  public static final class nodeAPIFutureStub extends io.grpc.stub.AbstractFutureStub<nodeAPIFutureStub> {
    private nodeAPIFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected nodeAPIFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new nodeAPIFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.NodeAPI.NodesClose> findNode(
        generated.NodeAPI.NodeProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindNodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.NodeAPI.NodeProto> ping(
        generated.NodeAPI.NodeProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.NodeAPI.StoreResponse> store(
        generated.NodeAPI.StoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStoreMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.NodeAPI.FindResponse> findValue(
        generated.NodeAPI.FindRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindValueMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIND_NODE = 0;
  private static final int METHODID_PING = 1;
  private static final int METHODID_STORE = 2;
  private static final int METHODID_FIND_VALUE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final nodeAPIImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(nodeAPIImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND_NODE:
          serviceImpl.findNode((generated.NodeAPI.NodeProto) request,
              (io.grpc.stub.StreamObserver<generated.NodeAPI.NodesClose>) responseObserver);
          break;
        case METHODID_PING:
          serviceImpl.ping((generated.NodeAPI.NodeProto) request,
              (io.grpc.stub.StreamObserver<generated.NodeAPI.NodeProto>) responseObserver);
          break;
        case METHODID_STORE:
          serviceImpl.store((generated.NodeAPI.StoreRequest) request,
              (io.grpc.stub.StreamObserver<generated.NodeAPI.StoreResponse>) responseObserver);
          break;
        case METHODID_FIND_VALUE:
          serviceImpl.findValue((generated.NodeAPI.FindRequest) request,
              (io.grpc.stub.StreamObserver<generated.NodeAPI.FindResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class nodeAPIBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    nodeAPIBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return generated.NodeAPI.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("nodeAPI");
    }
  }

  private static final class nodeAPIFileDescriptorSupplier
      extends nodeAPIBaseDescriptorSupplier {
    nodeAPIFileDescriptorSupplier() {}
  }

  private static final class nodeAPIMethodDescriptorSupplier
      extends nodeAPIBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    nodeAPIMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (nodeAPIGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new nodeAPIFileDescriptorSupplier())
              .addMethod(getFindNodeMethod())
              .addMethod(getPingMethod())
              .addMethod(getStoreMethod())
              .addMethod(getFindValueMethod())
              .build();
        }
      }
    }
    return result;
  }
}
