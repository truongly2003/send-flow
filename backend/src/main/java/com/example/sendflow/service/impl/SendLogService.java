package com.example.sendflow.service.impl;

import com.example.sendflow.dto.response.PagedResponse;
import com.example.sendflow.dto.response.SendLogResponse;
import com.example.sendflow.entity.SendLog;
import com.example.sendflow.repository.SendLogRepository;
import com.example.sendflow.service.ISendLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SendLogService implements ISendLogService {
    private final SendLogRepository sendLogRepository;

    @Override
    public PagedResponse<SendLogResponse> getAllSendLogByCampaignId(Long campaignId, Pageable pageable) {
        Page<SendLog> sendLogs = sendLogRepository.findAllByCampaignIdOrderBySentAtDesc(campaignId, pageable);
        List<SendLogResponse> content=sendLogs.getContent().stream()
                .map(this::mapToSendLogResponse)
                .toList();
        return PagedResponse.<SendLogResponse>builder()
                .content(content)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPages(sendLogs.getTotalPages())
                .totalElements(sendLogs.getTotalElements())
                .last(sendLogs.isLast())
                .build();
    }

    private SendLogResponse mapToSendLogResponse(SendLog log) {
        return SendLogResponse.builder()
                .id(log.getId())
                .contactId(log.getContact().getId())
                .recipientEmail(log.getContact().getEmail())
                .contactName(log.getContact().getName())
                .status(log.getStatus())
                .messageId(log.getMessageId())
                .sentAt(log.getSentAt())
                .providerResponse(log.getProviderResponse())
                .retryCount(log.getRetryCount())
                .deliveredAt(log.getDeliveredAt())
                .openedAt(log.getOpenedAt())
                .clickedAt(log.getClickedAt())
                .bouncedAt(log.getBouncedAt())
                .unsubscribedAt(log.getUnsubscribedAt())
                .build();
    }
}
