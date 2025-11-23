package com.example.sendflow;

import com.example.sendflow.entity.*;
import com.example.sendflow.enums.CampaignStatus;
import com.example.sendflow.enums.EventStatus;
import com.example.sendflow.repository.*;
import com.example.sendflow.service.ISendMailService;
import com.example.sendflow.service.impl.CampaignService;
import jakarta.mail.internet.AddressException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ActiveProfiles("test")
@Transactional // QUAN TRỌNG: rollback tự động sau mỗi test
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SendEmailsTest {

    @Autowired private CampaignService campaignService;
    @Autowired private CampaignRepository campaignRepository;
    @Autowired private ContactListRepository contactListRepository;
    @Autowired private ContactRepository contactRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SmtpConfigRepository smtpConfigRepository;
    @Autowired private SendLogRepository sendLogRepository;

    @MockBean
    private ISendMailService sendMailService;

    private Long campaignId;

    @BeforeAll
    void setUp() throws AddressException {
        // Mock gửi mail thật → không gửi gì cả
        doNothing().when(sendMailService)
                .sendMailWithSmtp(any(), any(), any(), any(), any());

        // Tạo dữ liệu (toàn bộ trong 1 transaction → nhanh)
        User user = userRepository.save(User.builder().email("boss@test.com").build());

        smtpConfigRepository.save(SmtpConfig.builder()
                .user(user)
                .smtpHost("localhost")
                .smtpPort(25)
                .usernameSmtp("user")
                .passwordSmtp("pass")
                .auth(true)
                .starttls(true)
                .build());

        ContactList list = contactListRepository.save(ContactList.builder()
                .name("List 1000")
                .user(user)
                .contacts(new ArrayList<>())
                .build());

        // Tạo 1000 contact
        for (int i = 1; i <= 1000; i++) {
            Contact c = contactRepository.save(Contact.builder()
                    .email("user" + i + "@test.com")
                    .contactList(list)
                    .build());
            list.getContacts().add(c);
        }

        Campaign campaign = campaignRepository.save(Campaign.builder()
                .name("Test 1000 emails")
                .messageContent("<h1>Hello {{name}}</h1>")
                .user(user)
                .contactList(list)
                .status(CampaignStatus.DRAFT)
                .build());

        this.campaignId = campaign.getId();
    }

    @Test
    @Order(1)
    @Timeout(15) // đủ rồi, thực tế chỉ ~3-6 giây
    void test_gui_1000_email_qua_queue() throws InterruptedException {
        long start = System.currentTimeMillis();

        // GỌI SERVICE
        campaignService.sendCampaignMail(campaignId);

        // Đợi RabbitMQ consumer xử lý xong (vì mock nên cực nhanh)
        // Dùng CountDownLatch trong service thật thì tốt hơn, nhưng tạm dùng sleep
        Thread.sleep(8000); // 1000 email mock → ~5-7 giây là đủ

        long end = System.currentTimeMillis();
        System.out.println("Tổng thời gian xử lý 1000 email: " + (end - start) + "ms");

        // KIỂM TRA KẾT QUẢ
        assertThat(sendLogRepository.findAll())
                .hasSize(1000)
                .allMatch(log -> log.getStatus() == EventStatus.SENT);

        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        assertThat(campaign.getStatus()).isEqualTo(CampaignStatus.SENDING);
        // hoặc COMPLETED nếu bạn đã xử lý xong queue
    }
}