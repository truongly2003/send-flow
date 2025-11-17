package com.example.sendflow.repository;

import com.example.sendflow.dto.response.CampaignResponse;
import com.example.sendflow.entity.Campaign;
import com.example.sendflow.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("""
                SELECT new com.example.sendflow.dto.response.CampaignResponse(
                    c.id,
                    c.user.id,
                    c.name,
                    c.messageContent,
                    t.name,
                    cl.name,
                    COUNT(sl.id),
                    SUM(CASE WHEN sl.status = com.example.sendflow.enums.EventStatus.DELIVERED THEN 1 ELSE 0 END),
                    c.scheduleTime,
                    c.createdAt,
                    c.status
                )
                FROM Campaign c
                LEFT JOIN c.template t
                LEFT JOIN c.contactList cl
                LEFT JOIN cl.contacts ct
                LEFT JOIN ct.sendLogs sl
                WHERE c.user.id = :userId
                GROUP BY c.id, c.user.id, c.name, c.messageContent, t.name, cl.name, c.scheduleTime, c.createdAt
            """)
    List<CampaignResponse> findCampaignResponsesByUserId(Long userId);

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.template.id = :templateId")
    Integer countByTemplateId(Long templateId);

    List<Campaign> getCampaignsByUserId(Long userId);
    // đếm số campaign complete
    long countByUserIdAndStatus(Long userId, CampaignStatus status);
    // đêm tổng số campaign
    long countByUserId(Long userId);

    long countByStatus(CampaignStatus status);
}


//SELECT
//c.id AS campaign_id,
//c.user_id,
//c.name AS campaign_name,
//c.message_content,
//t.name AS template_name,
//cl.name AS contact_list_name,
//COUNT(sl.id) AS total_send_logs,
//SUM(CASE WHEN sl.status = 'DELIVERED' THEN 1 ELSE 0 END) AS delivered_count,
//c.schedule_time,
//c.created_at,
//c.status
//FROM campaign c
//LEFT JOIN template t ON c.template_id = t.id
//LEFT JOIN contact_list cl ON c.contact_list_id = cl.id
//LEFT JOIN contact ct ON ct.contact_list_id = cl.id
//LEFT JOIN send_log sl ON sl.contact_id = ct.id
//WHERE c.user_id = 1
//GROUP BY
//c.id,
//c.user_id,
//c.name,
//c.message_content,
//t.name,
//cl.name,
//c.schedule_time,
//c.created_at,
//c.status;
