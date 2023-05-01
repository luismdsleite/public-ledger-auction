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
  private static volatile io.grpc.MethodDescriptor<generated.NodeAPI.NodeInfo,
      generated.NodeAPI.NodesClose> getFindNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findNode",
      requestType = generated.NodeAPI.NodeInfo.class,
      responseType = generated.NodeAPI.NodesClose.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.NodeAPI.NodeInfo,
      generated.NodeAPI.NodesClose> getFindNodeMethod() {
    io.grpc.MethodDescriptor<generated.NodeAPI.NodeInfo, generated.NodeAPI.NodesClose> getFindNodeMethod;
    if ((getFindNodeMethod = nodeAPIGrpc.getFindNodeMethod) == null) {
      synchronized (nodeAPIGrpc.class) {
        if ((getFindNodeMethod = nodeAPIGrpc.getFindNodeMethod) == null) {
          nodeAPIGrpc.getFindNodeMethod = getFindNodeMethod =
              io.grpc.MethodDescriptor.<generated.NodeAPI.NodeInfo, generated.NodeAPI.NodesClose>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.NodeInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.NodesClose.getDefaultInstance()))
              .setSchemaDescriptor(new nodeAPIMethodDescriptorSupplier("findNode"))
              .build();
        }
      }
    }
    return getFindNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.NodeAPI.Empty,
      generated.NodeAPI.Empty> getPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ping",
      requestType = generated.NodeAPI.Empty.class,
      responseType = generated.NodeAPI.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.NodeAPI.Empty,
      generated.NodeAPI.Empty> getPingMethod() {
    io.grpc.MethodDescriptor<generated.NodeAPI.Empty, generated.NodeAPI.Empty> getPingMethod;
    if ((getPingMethod = nodeAPIGrpc.getPingMethod) == null) {
      synchronized (nodeAPIGrpc.class) {
        if ((getPingMethod = nodeAPIGrpc.getPingMethod) == null) {
          nodeAPIGrpc.getPingMethod = getPingMethod =
              io.grpc.MethodDescriptor.<generated.NodeAPI.Empty, generated.NodeAPI.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.NodeAPI.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new nodeAPIMethodDescriptorSupplier("ping"))
              .build();
        }
      }
    }
    return getPingMethod;
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
    public void findNode(generated.NodeAPI.NodeInfo request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.NodesClose> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindNodeMethod(), responseObserver);
    }

    /**
     */
    public void ping(generated.NodeAPI.Empty request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFindNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generated.NodeAPI.NodeInfo,
                generated.NodeAPI.NodesClose>(
                  this, METHODID_FIND_NODE)))
          .addMethod(
            getPingMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                generated.NodeAPI.Empty,
                generated.NodeAPI.Empty>(
                  this, METHODID_PING)))
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
    public void findNode(generated.NodeAPI.NodeInfo request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.NodesClose> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ping(generated.NodeAPI.Empty request,
        io.grpc.stub.StreamObserver<generated.NodeAPI.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
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
    public generated.NodeAPI.NodesClose findNode(generated.NodeAPI.NodeInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindNodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public generated.NodeAPI.Empty ping(generated.NodeAPI.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
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
        generated.NodeAPI.NodeInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindNodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.NodeAPI.Empty> ping(
        generated.NodeAPI.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIND_NODE = 0;
  private static final int METHODID_PING = 1;

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
          serviceImpl.findNode((generated.NodeAPI.NodeInfo) request,
              (io.grpc.stub.StreamObserver<generated.NodeAPI.NodesClose>) responseObserver);
          break;
        case METHODID_PING:
          serviceImpl.ping((generated.NodeAPI.Empty) request,
              (io.grpc.stub.StreamObserver<generated.NodeAPI.Empty>) responseObserver);
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
              .build();
        }
      }
    }
    return result;
  }
}
