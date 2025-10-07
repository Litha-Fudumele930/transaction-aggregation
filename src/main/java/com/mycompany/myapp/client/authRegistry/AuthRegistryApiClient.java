package com.mycompany.myapp.client.authRegistry;

import com.mycompany.myapp.client.AuthApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "${application.authRegistry.feignClientName}",
    url = "${application.authRegistry.url}",
    configuration = ClientConfiguration.class
)
public interface AuthRegistryApiClient extends AuthApi {}
