package proto.connection;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class JoinNetworkGrpc {

  private JoinNetworkGrpc() {}

  public static final java.lang.String SERVICE_NAME = "JoinNetwork";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.connection.Join,
      proto.connection.AllNetworkNodes> getJoinWithNodesRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "JoinWithNodesRequest",
      requestType = proto.connection.Join.class,
      responseType = proto.connection.AllNetworkNodes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.connection.Join,
      proto.connection.AllNetworkNodes> getJoinWithNodesRequestMethod() {
    io.grpc.MethodDescriptor<proto.connection.Join, proto.connection.AllNetworkNodes> getJoinWithNodesRequestMethod;
    if ((getJoinWithNodesRequestMethod = JoinNetworkGrpc.getJoinWithNodesRequestMethod) == null) {
      synchronized (JoinNetworkGrpc.class) {
        if ((getJoinWithNodesRequestMethod = JoinNetworkGrpc.getJoinWithNodesRequestMethod) == null) {
          JoinNetworkGrpc.getJoinWithNodesRequestMethod = getJoinWithNodesRequestMethod =
              io.grpc.MethodDescriptor.<proto.connection.Join, proto.connection.AllNetworkNodes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "JoinWithNodesRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.connection.Join.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.connection.AllNetworkNodes.getDefaultInstance()))
              .setSchemaDescriptor(new JoinNetworkMethodDescriptorSupplier("JoinWithNodesRequest"))
              .build();
        }
      }
    }
    return getJoinWithNodesRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.connection.Join,
      proto.connection.JoinResponse> getJoinToNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "JoinToNode",
      requestType = proto.connection.Join.class,
      responseType = proto.connection.JoinResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.connection.Join,
      proto.connection.JoinResponse> getJoinToNodeMethod() {
    io.grpc.MethodDescriptor<proto.connection.Join, proto.connection.JoinResponse> getJoinToNodeMethod;
    if ((getJoinToNodeMethod = JoinNetworkGrpc.getJoinToNodeMethod) == null) {
      synchronized (JoinNetworkGrpc.class) {
        if ((getJoinToNodeMethod = JoinNetworkGrpc.getJoinToNodeMethod) == null) {
          JoinNetworkGrpc.getJoinToNodeMethod = getJoinToNodeMethod =
              io.grpc.MethodDescriptor.<proto.connection.Join, proto.connection.JoinResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "JoinToNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.connection.Join.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.connection.JoinResponse.getDefaultInstance()))
              .setSchemaDescriptor(new JoinNetworkMethodDescriptorSupplier("JoinToNode"))
              .build();
        }
      }
    }
    return getJoinToNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.common.Address,
      com.google.protobuf.Empty> getPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Ping",
      requestType = proto.common.Address.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.common.Address,
      com.google.protobuf.Empty> getPingMethod() {
    io.grpc.MethodDescriptor<proto.common.Address, com.google.protobuf.Empty> getPingMethod;
    if ((getPingMethod = JoinNetworkGrpc.getPingMethod) == null) {
      synchronized (JoinNetworkGrpc.class) {
        if ((getPingMethod = JoinNetworkGrpc.getPingMethod) == null) {
          JoinNetworkGrpc.getPingMethod = getPingMethod =
              io.grpc.MethodDescriptor.<proto.common.Address, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.common.Address.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new JoinNetworkMethodDescriptorSupplier("Ping"))
              .build();
        }
      }
    }
    return getPingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.connection.NodeDied,
      com.google.protobuf.Empty> getNodeDiedInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "NodeDiedInfo",
      requestType = proto.connection.NodeDied.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.connection.NodeDied,
      com.google.protobuf.Empty> getNodeDiedInfoMethod() {
    io.grpc.MethodDescriptor<proto.connection.NodeDied, com.google.protobuf.Empty> getNodeDiedInfoMethod;
    if ((getNodeDiedInfoMethod = JoinNetworkGrpc.getNodeDiedInfoMethod) == null) {
      synchronized (JoinNetworkGrpc.class) {
        if ((getNodeDiedInfoMethod = JoinNetworkGrpc.getNodeDiedInfoMethod) == null) {
          JoinNetworkGrpc.getNodeDiedInfoMethod = getNodeDiedInfoMethod =
              io.grpc.MethodDescriptor.<proto.connection.NodeDied, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "NodeDiedInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.connection.NodeDied.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new JoinNetworkMethodDescriptorSupplier("NodeDiedInfo"))
              .build();
        }
      }
    }
    return getNodeDiedInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static JoinNetworkStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JoinNetworkStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JoinNetworkStub>() {
        @java.lang.Override
        public JoinNetworkStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JoinNetworkStub(channel, callOptions);
        }
      };
    return JoinNetworkStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static JoinNetworkBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JoinNetworkBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JoinNetworkBlockingV2Stub>() {
        @java.lang.Override
        public JoinNetworkBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JoinNetworkBlockingV2Stub(channel, callOptions);
        }
      };
    return JoinNetworkBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static JoinNetworkBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JoinNetworkBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JoinNetworkBlockingStub>() {
        @java.lang.Override
        public JoinNetworkBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JoinNetworkBlockingStub(channel, callOptions);
        }
      };
    return JoinNetworkBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static JoinNetworkFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JoinNetworkFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JoinNetworkFutureStub>() {
        @java.lang.Override
        public JoinNetworkFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JoinNetworkFutureStub(channel, callOptions);
        }
      };
    return JoinNetworkFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void joinWithNodesRequest(proto.connection.Join request,
        io.grpc.stub.StreamObserver<proto.connection.AllNetworkNodes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinWithNodesRequestMethod(), responseObserver);
    }

    /**
     */
    default void joinToNode(proto.connection.Join request,
        io.grpc.stub.StreamObserver<proto.connection.JoinResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinToNodeMethod(), responseObserver);
    }

    /**
     */
    default void ping(proto.common.Address request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }

    /**
     */
    default void nodeDiedInfo(proto.connection.NodeDied request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNodeDiedInfoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service JoinNetwork.
   */
  public static abstract class JoinNetworkImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return JoinNetworkGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service JoinNetwork.
   */
  public static final class JoinNetworkStub
      extends io.grpc.stub.AbstractAsyncStub<JoinNetworkStub> {
    private JoinNetworkStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JoinNetworkStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JoinNetworkStub(channel, callOptions);
    }

    /**
     */
    public void joinWithNodesRequest(proto.connection.Join request,
        io.grpc.stub.StreamObserver<proto.connection.AllNetworkNodes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinWithNodesRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void joinToNode(proto.connection.Join request,
        io.grpc.stub.StreamObserver<proto.connection.JoinResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinToNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ping(proto.common.Address request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void nodeDiedInfo(proto.connection.NodeDied request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNodeDiedInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service JoinNetwork.
   */
  public static final class JoinNetworkBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<JoinNetworkBlockingV2Stub> {
    private JoinNetworkBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JoinNetworkBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JoinNetworkBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public proto.connection.AllNetworkNodes joinWithNodesRequest(proto.connection.Join request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getJoinWithNodesRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.connection.JoinResponse joinToNode(proto.connection.Join request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getJoinToNodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty ping(proto.common.Address request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty nodeDiedInfo(proto.connection.NodeDied request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getNodeDiedInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service JoinNetwork.
   */
  public static final class JoinNetworkBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<JoinNetworkBlockingStub> {
    private JoinNetworkBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JoinNetworkBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JoinNetworkBlockingStub(channel, callOptions);
    }

    /**
     */
    public proto.connection.AllNetworkNodes joinWithNodesRequest(proto.connection.Join request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinWithNodesRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.connection.JoinResponse joinToNode(proto.connection.Join request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinToNodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty ping(proto.common.Address request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty nodeDiedInfo(proto.connection.NodeDied request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNodeDiedInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service JoinNetwork.
   */
  public static final class JoinNetworkFutureStub
      extends io.grpc.stub.AbstractFutureStub<JoinNetworkFutureStub> {
    private JoinNetworkFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JoinNetworkFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JoinNetworkFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.connection.AllNetworkNodes> joinWithNodesRequest(
        proto.connection.Join request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinWithNodesRequestMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.connection.JoinResponse> joinToNode(
        proto.connection.Join request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinToNodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> ping(
        proto.common.Address request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> nodeDiedInfo(
        proto.connection.NodeDied request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNodeDiedInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_JOIN_WITH_NODES_REQUEST = 0;
  private static final int METHODID_JOIN_TO_NODE = 1;
  private static final int METHODID_PING = 2;
  private static final int METHODID_NODE_DIED_INFO = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_JOIN_WITH_NODES_REQUEST:
          serviceImpl.joinWithNodesRequest((proto.connection.Join) request,
              (io.grpc.stub.StreamObserver<proto.connection.AllNetworkNodes>) responseObserver);
          break;
        case METHODID_JOIN_TO_NODE:
          serviceImpl.joinToNode((proto.connection.Join) request,
              (io.grpc.stub.StreamObserver<proto.connection.JoinResponse>) responseObserver);
          break;
        case METHODID_PING:
          serviceImpl.ping((proto.common.Address) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_NODE_DIED_INFO:
          serviceImpl.nodeDiedInfo((proto.connection.NodeDied) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getJoinWithNodesRequestMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              proto.connection.Join,
              proto.connection.AllNetworkNodes>(
                service, METHODID_JOIN_WITH_NODES_REQUEST)))
        .addMethod(
          getJoinToNodeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              proto.connection.Join,
              proto.connection.JoinResponse>(
                service, METHODID_JOIN_TO_NODE)))
        .addMethod(
          getPingMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              proto.common.Address,
              com.google.protobuf.Empty>(
                service, METHODID_PING)))
        .addMethod(
          getNodeDiedInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              proto.connection.NodeDied,
              com.google.protobuf.Empty>(
                service, METHODID_NODE_DIED_INFO)))
        .build();
  }

  private static abstract class JoinNetworkBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    JoinNetworkBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.connection.ConnectionProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("JoinNetwork");
    }
  }

  private static final class JoinNetworkFileDescriptorSupplier
      extends JoinNetworkBaseDescriptorSupplier {
    JoinNetworkFileDescriptorSupplier() {}
  }

  private static final class JoinNetworkMethodDescriptorSupplier
      extends JoinNetworkBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    JoinNetworkMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (JoinNetworkGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new JoinNetworkFileDescriptorSupplier())
              .addMethod(getJoinWithNodesRequestMethod())
              .addMethod(getJoinToNodeMethod())
              .addMethod(getPingMethod())
              .addMethod(getNodeDiedInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
