package com.example.sendflow.service.impl;

import com.example.sendflow.dto.request.CampaignRequest;
import com.example.sendflow.dto.response.CampaignResponse;
import com.example.sendflow.entity.Campaign;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.CampaignMapper;
import com.example.sendflow.repository.CampaignRepository;
import com.example.sendflow.service.ICampaignService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService implements ICampaignService {
    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;

    @Override
    public List<CampaignResponse> getAllCampaigns(Long userId) {
        return campaignRepository.findCampaignResponsesByUserId(userId);
    }

    @Override
    public void createCampaign(CampaignRequest campaignRequest) {
        Campaign campaign = campaignMapper.toCampaign(campaignRequest);
        Campaign campaignSaved = campaignRepository.save(campaign);
    }

    @Override
    public void updateCampaign(Long campaignId, CampaignRequest campaignRequest) {
        Campaign existingCampaign=campaignRepository.findById(campaignId)
                .orElseThrow(()->new ResourceNotFoundException("Campaign not found"));
        campaignMapper.updateCampaign(campaignRequest,existingCampaign);
        Campaign campaignSaved = campaignRepository.save(existingCampaign);
    }

    @Override
    public void deleteCampaign(Long campaignId) {
        Campaign existingCampaign=campaignRepository.findById(campaignId)
                .orElseThrow(()->new ResourceNotFoundException("Campaign not found"));
        campaignRepository.delete(existingCampaign);
    }

    @Override
    public void sendCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

//        List<Contact> contacts = contactRepository.findByContactListId(campaign.getContactList().getId());
//        campaign.setStatus(CampaignStatus.SENDING);
//        campaignRepository.save(campaign);
//
//        for (Contact contact : contacts) {
//            try {
//                // (1) Giả lập gửi email
//                Thread.sleep(500);
//                String response = "Mail sent successfully to " + contact.getEmail();
//
//                // (2) Lưu SendLog
//                SendLog log = SendLog.builder()
//                        .campaign(campaign)
//                        .contact(contact)
//                        .status(EventStatus.SENT)
//                        .providerResponse(response)
//                        .sentAt(LocalDateTime.now())
//                        .build();
//                sendLogRepository.save(log);
//
//                // (3) Phát sự kiện qua WebSocket
//                socketHandler.broadcastUpdate(campaignId,
//                        "✅ Sent to " + contact.getEmail());
//            } catch (Exception e) {
//                socketHandler.broadcastUpdate(campaignId,
//                        "❌ Failed to send to " + contact.getEmail());
//            }
//        }
//
//        campaign.setStatus(CampaignStatus.COMPLETED);
//        campaignRepository.save(campaign);
//        socketHandler.broadcastUpdate(campaignId, "🎉 Campaign completed!");
    }
}
