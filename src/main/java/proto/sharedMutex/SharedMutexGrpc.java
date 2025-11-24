package proto.sharedMutex;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class SharedMutexGrpc {

  private SharedMutexGrpc() {}

  public static final java.lang.String SERVICE_NAME = "SharedMutex";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.sharedMutex.Request,
      com.google.protobuf.Empty> getRegisterRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerRequest",
      requestType = proto.sharedMutex.Request.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.sharedMutex.Request,
      com.google.protobuf.Empty> getRegisterRequestMethod() {
    io.grpc.MethodDescriptor<proto.sharedMutex.Request, com.google.protobuf.Empty> getRegisterRequestMethod;
    if ((getRegisterRequestMethod = SharedMutexGrpc.getRegisterRequestMethod) == null) {
      synchronized (SharedMutexGrpc.class) {
        if ((getRegisterRequestMethod = SharedMutexGrpc.getRegisterRequestMethod) == null) {
          SharedMutexGrpc.getRegisterRequestMethod = getRegisterRequestMethod =
              io.grpc.MethodDescriptor.<proto.sharedMutex.Request, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.sharedMutex.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new SharedMutexMethodDescriptorSupplier("registerRequest"))
              .build();
        }
      }
    }
    return getRegisterRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.sharedMutex.Reply,
      com.google.protobuf.Empty> getRegisterReplyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerReply",
      requestType = proto.sharedMutex.Reply.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.sharedMutex.Reply,
      com.google.protobuf.Empty> getRegisterReplyMethod() {
    io.grpc.MethodDescriptor<proto.sharedMutex.Reply, com.google.protobuf.Empty> getRegisterReplyMethod;
    if ((getRegisterReplyMethod = SharedMutexGrpc.getRegisterReplyMethod) == null) {
      synchronized (SharedMutexGrpc.class) {
        if ((getRegisterReplyMethod = SharedMutexGrpc.getRegisterReplyMethod) == null) {
          SharedMutexGrpc.getRegisterReplyMethod = getRegisterReplyMethod =
              io.grpc.MethodDescriptor.<proto.sharedMutex.Reply, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerReply"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.sharedMutex.Reply.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new SharedMutexMethodDescriptorSupplier("registerReply"))
              .build();
        }
      }
    }
    return getRegisterReplyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.common.SharedVariableUpdate,
      com.google.protobuf.Empty> getUpdateSharedVariableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateSharedVariable",
      requestType = proto.common.SharedVariableUpdate.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.common.SharedVariableUpdate,
      com.google.protobuf.Empty> getUpdateSharedVariableMethod() {
    io.grpc.MethodDescriptor<proto.common.SharedVariableUpdate, com.google.protobuf.Empty> getUpdateSharedVariableMethod;
    if ((getUpdateSharedVariableMethod = SharedMutexGrpc.getUpdateSharedVariableMethod) == null) {
      synchronized (SharedMutexGrpc.class) {
        if ((getUpdateSharedVariableMethod = SharedMutexGrpc.getUpdateSharedVariableMethod) == null) {
          SharedMutexGrpc.getUpdateSharedVariableMethod = getUpdateSharedVariableMethod =
              io.grpc.MethodDescriptor.<proto.common.SharedVariableUpdate, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateSharedVariable"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.common.SharedVariableUpdate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new SharedMutexMethodDescriptorSupplier("updateSharedVariable"))
              .build();
        }
      }
    }
    return getUpdateSharedVariableMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SharedMutexStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SharedMutexStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SharedMutexStub>() {
        @java.lang.Override
        public SharedMutexStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SharedMutexStub(channel, callOptions);
        }
      };
    return SharedMutexStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static SharedMutexBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SharedMutexBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SharedMutexBlockingV2Stub>() {
        @java.lang.Override
        public SharedMutexBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SharedMutexBlockingV2Stub(channel, callOptions);
        }
      };
    return SharedMutexBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SharedMutexBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SharedMutexBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SharedMutexBlockingStub>() {
        @java.lang.Override
        public SharedMutexBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SharedMutexBlockingStub(channel, callOptions);
        }
      };
    return SharedMutexBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SharedMutexFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SharedMutexFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SharedMutexFutureStub>() {
        @java.lang.Override
        public SharedMutexFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SharedMutexFutureStub(channel, callOptions);
        }
      };
    return SharedMutexFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void registerRequest(proto.sharedMutex.Request request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterRequestMethod(), responseObserver);
    }

    /**
     */
    default void registerReply(proto.sharedMutex.Reply request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterReplyMethod(), responseObserver);
    }

    /**
     */
    default void updateSharedVariable(proto.common.SharedVariableUpdate request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateSharedVariableMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SharedMutex.
   */
  public static abstract class SharedMutexImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SharedMutexGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SharedMutex.
   */
  public static final class SharedMutexStub
      extends io.grpc.stub.AbstractAsyncStub<SharedMutexStub> {
    private SharedMutexStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SharedMutexStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SharedMutexStub(channel, callOptions);
    }

    /**
     */
    public void registerRequest(proto.sharedMutex.Request request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerReply(proto.sharedMutex.Reply request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterReplyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateSharedVariable(proto.common.SharedVariableUpdate request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateSharedVariableMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SharedMutex.
   */
  public static final class SharedMutexBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<SharedMutexBlockingV2Stub> {
    private SharedMutexBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SharedMutexBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SharedMutexBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty registerRequest(proto.sharedMutex.Request request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getRegisterRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty registerReply(proto.sharedMutex.Reply request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getRegisterReplyMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateSharedVariable(proto.common.SharedVariableUpdate request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getUpdateSharedVariableMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service SharedMutex.
   */
  public static final class SharedMutexBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SharedMutexBlockingStub> {
    private SharedMutexBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SharedMutexBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SharedMutexBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty registerRequest(proto.sharedMutex.Request request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty registerReply(proto.sharedMutex.Reply request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterReplyMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateSharedVariable(proto.common.SharedVariableUpdate request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateSharedVariableMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SharedMutex.
   */
  public static final class SharedMutexFutureStub
      extends io.grpc.stub.AbstractFutureStub<SharedMutexFutureStub> {
    private SharedMutexFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SharedMutexFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SharedMutexFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerRequest(
        proto.sharedMutex.Request request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterRequestMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerReply(
        proto.sharedMutex.Reply request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterReplyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> updateSharedVariable(
        proto.common.SharedVariableUpdate request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateSharedVariableMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_REQUEST = 0;
  private static final int METHODID_REGISTER_REPLY = 1;
  private static final int METHODID_UPDATE_SHARED_VARIABLE = 2;

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
        case METHODID_REGISTER_REQUEST:
          serviceImpl.registerRequest((proto.sharedMutex.Request) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_REGISTER_REPLY:
          serviceImpl.registerReply((proto.sharedMutex.Reply) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_UPDATE_SHARED_VARIABLE:
          serviceImpl.updateSharedVariable((proto.common.SharedVariableUpdate) request,
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
          getRegisterRequestMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              proto.sharedMutex.Request,
              com.google.protobuf.Empty>(
                service, METHODID_REGISTER_REQUEST)))
        .addMethod(
          getRegisterReplyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              proto.sharedMutex.Reply,
              com.google.protobuf.Empty>(
                service, METHODID_REGISTER_REPLY)))
        .addMethod(
          getUpdateSharedVariableMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              proto.common.SharedVariableUpdate,
              com.google.protobuf.Empty>(
                service, METHODID_UPDATE_SHARED_VARIABLE)))
        .build();
  }

  private static abstract class SharedMutexBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SharedMutexBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.sharedMutex.SharedMutexProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SharedMutex");
    }
  }

  private static final class SharedMutexFileDescriptorSupplier
      extends SharedMutexBaseDescriptorSupplier {
    SharedMutexFileDescriptorSupplier() {}
  }

  private static final class SharedMutexMethodDescriptorSupplier
      extends SharedMutexBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SharedMutexMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SharedMutexGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SharedMutexFileDescriptorSupplier())
              .addMethod(getRegisterRequestMethod())
              .addMethod(getRegisterReplyMethod())
              .addMethod(getUpdateSharedVariableMethod())
              .build();
        }
      }
    }
    return result;
  }
}
