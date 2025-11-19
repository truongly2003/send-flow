package com.example.sendflow.service.impl;

import com.example.sendflow.dto.response.DashboardAdminResponse;
import com.example.sendflow.dto.response.DashboardUserResponse;
import com.example.sendflow.dto.response.RevenueResponse;
import com.example.sendflow.entity.*;
import com.example.sendflow.enums.CampaignStatus;
import com.example.sendflow.enums.PaymentStatus;
import com.example.sendflow.enums.SubscriptionStatus;
import com.example.sendflow.repository.*;
import com.example.sendflow.service.IDashBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashBoardService implements IDashBoardService {
    private final CampaignRepository campaignRepository;
    private final ContactRepository contactRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UsageRepository usageRepository;
    private final TemplateRepository templateRepository;
    private final TransactionRepository transactionRepository;
    // user
    @Override
    public DashboardUserResponse getAllDashBoardUser(Long userId) {
        // check subscription
        Optional<Subscription> sub = subscriptionRepository.findActiveSubscription(userId, SubscriptionStatus.ACTIVE, LocalDateTime.now());
        if (sub.isEmpty()) {
            log.warn("No active subscription for user: {}", userId);
            return DashboardUserResponse.builder()
                    .limitMail(0)
                    .useMail(0)
                    .totalEmail(0)
                    .totalContacts(contactRepository.countByUserId(userId))
                    .totalCampaigns(0)
                    .totalTemplate(0)
                    .totalCampaignRating(0.0)
                    .build();
        }
        // get usage
        Usage usage = usageRepository.findBySubscriptionId(sub.get().getId());
        // get template
        List<Template> templates = templateRepository.findAllByUserId(userId);
        // Campaign stats
        long totalCampaigns = campaignRepository.countByUserId(userId);
        long completedCampaigns = campaignRepository.countByUserIdAndStatus(userId, CampaignStatus.COMPLETED);
        double totalCampaignRating = totalCampaigns > 0 ? (double) completedCampaigns / totalCampaigns * 100 : 0.0;


        return DashboardUserResponse.builder()
                .limitMail(sub.get().getPlan().getMaxEmailsPerMonth())
                .useMail(usage.getEmailCount())
                .totalEmail(sub.get().getPlan().getMaxEmailsPerMonth())
                .totalContacts(contactRepository.countByUserId(userId))
                .totalCampaigns(totalCampaigns)
                .totalTemplate(templates.size())
                .totalCampaignRating(totalCampaignRating)
                .build();
    }
    // admin
    @Override
    public DashboardAdminResponse getAllDashBoardAdmin() {
        // Tổng số user
        long totalUsers = subscriptionRepository.countDistinctByStatus(SubscriptionStatus.ACTIVE);

        // Tổng campaign
        long totalCampaigns = campaignRepository.count();

        // Tổng doanh thu tất cả transaction đã thanh toán
        long totalRevenue = transactionRepository
                .findAll().stream()
                .filter(t -> t.getPaymentStatus().equals(PaymentStatus.SUCCESS)) // hoặc TransactionStatus.PAID nếu có enum
                .mapToLong(t -> (long) t.getAmount().doubleValue())
                .sum();


        // Tổng email đã gửi
        long emailsSent = usageRepository.findAll().stream()
                .mapToLong(Usage::getEmailCount)
                .sum();

        // Số subscription đang active
        long activeSubscriptions = subscriptionRepository.countByStatus(SubscriptionStatus.ACTIVE);

        // Tỉ lệ thành công campaign
        long completedCampaigns = campaignRepository.countByStatus(CampaignStatus.COMPLETED);
        double successRate = totalCampaigns > 0 ? (double) completedCampaigns / totalCampaigns * 100 : 0.0;

        //
        double percentIncrease = 23.0;

        return DashboardAdminResponse.builder()
                .totalUsers(totalUsers)
                .totalCampaigns(totalCampaigns)
                .totalRevenue(totalRevenue)
                .emailsSent(emailsSent)
                .activeSubscriptions(activeSubscriptions)
                .successRate((long) successRate)
                .build();
    }

    @Override
    public List<RevenueResponse> getAllRevenue(int year, int month) {
        List<Object[]> raw=transactionRepository.getRevenueByPlan(year, month);
        BigDecimal total=raw.stream()
                .map(r->(BigDecimal) r[2])
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        List<RevenueResponse> result=new ArrayList<>();
        for(Object[] row: raw){
            String plan=(String) row[0];
            Long subscribers=(Long) row[1];
            BigDecimal revenue =(BigDecimal) row[2];
            double percent=total.compareTo(BigDecimal.ZERO)==0
                    ? 0
                    : revenue.multiply(BigDecimal.valueOf(100))
                    .divide(total,2, RoundingMode.HALF_UP)
                    .doubleValue();

            result.add(new RevenueResponse(
                    plan,
                    subscribers,
                    revenue,
                    percent
            ));
        }
        return result;
    }
}
