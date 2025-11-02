package com.example.sendflow.mapper;

import com.example.sendflow.dto.response.SubscriptionResponse;
import com.example.sendflow.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionResponse toSubscriptionResponse(Subscription subscription);
}
