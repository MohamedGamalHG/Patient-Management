package com.mo.billingservice.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingResponseOrBuilder;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);
    @Override
    public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request  received {}",request.toString());
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("test")
                .setStatus("Active")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

