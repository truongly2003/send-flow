//package com.example.sendflow.fakeData;
//
//import com.example.sendflow.entity.*;
//import com.example.sendflow.enums.*;
//import com.example.sendflow.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//    @Autowired
//    private UserRepository userRepo;  // Giả sử bạn đã tạo repos
//
//    @Autowired
//    private PlanRepository planRepo;
//
//    @Autowired
//    private SubscriptionRepository subRepo;
//
//    @Autowired
//    private TransactionRepository transRepo;
//
//    @Autowired
//    private UsageRepository usageRepo;
//
//    @Autowired
//    private TemplateRepository templateRepo;
//
//    @Autowired
//    private ContactListRepository listRepo;
//
//    @Autowired
//    private ContactRepository contactRepo;
//
//    @Autowired
//    private CampaignRepository campaignRepo;
//
//    @Autowired
//    private SendLogRepository sendLogRepo;
//    @Override
//    public void run(String... args) throws Exception {
//        if (userRepo.count() > 0) {
//            System.out.println("DB đã có data, skip.");
//            return;
//        }
//
//        System.out.println("Tạo data thực tế cho tất cả 10 bảng (ngày 31/10/2025)...");
//
//        // 1. Users: 2 users (admin + user thường)
//        User admin = User.builder()
//                .name("Admin VN")
//                .email("admin@saas.vn")
//                .password("12345")
////                .passwordHash("$2a$10$admin_pass_hash")  // Hash 'admin123'
//                .role(Role.ADMIN)
//                .build();
//        admin = userRepo.save(admin);
//
//        User regularUser = User.builder()
//                .name("User Marketer")
//                .email("user@marketing.com")
//                .password("12345")
////                .passwordHash("$2a$10$user_pass_hash")  // Hash 'user123'
//                .role(Role.USER)
//                .build();
//        regularUser = userRepo.save(regularUser);
//        System.out.println("Thêm 2 users.");
//
//        // 2. Plans: 2 plans (Basic + Pro)
//        Plan basicPlan = Plan.builder()
//                .name("Basic Plan")
//                .price("20000")
//                .description("Gói cơ bản cho startup")
//                .status(Status.ACTIVE)
//                .limit("{\"max_recipients\":1000,\"max_campaigns\":10}")
//                .build();
//        basicPlan = planRepo.save(basicPlan);
//
//        Plan proPlan = Plan.builder()
//                .price("20000")
//                .description("Gói cơ bản cho startup")
//                .status(Status.ACTIVE)
//                .limit("{\"max_recipients\":1000,\"max_campaigns\":10}")
//                .build();
//        proPlan = planRepo.save(proPlan);
//        System.out.println("Thêm 2 plans.");
//
//        // 3. Subscriptions: 2 subs (1 cho admin Basic, 1 cho user Pro)
//        Subscription adminSub = Subscription.builder()
//                .user(admin)
//                .plan(basicPlan)
//                .startTime(LocalDateTime.of(2025, 10, 1, 0, 0))  // 01/10/2025
//                .endTime(LocalDateTime.of(2025, 10, 31, 23, 59))  // Hôm nay hết hạn
//                .status(SubscriptionStatus.ACTIVE)
//                .build();
//        adminSub = subRepo.save(adminSub);
//
//        Subscription userSub = Subscription.builder()
//                .user(admin)
//                .plan(basicPlan)
//                .startTime(LocalDateTime.of(2025, 10, 1, 0, 0))  // 01/10/2025
//                .endTime(LocalDateTime.of(2025, 10, 31, 23, 59))  // Hôm nay hết hạn
//                .status(SubscriptionStatus.ACTIVE)
//                .build();
//        userSub = subRepo.save(userSub);
//        System.out.println("Thêm 2 subscriptions.");
//
//        // 4. Transactions: 3 trans (2 success, 1 failed)
//        Transaction trans1 = Transaction.builder()
//                .user(admin)
//                .plan(basicPlan)
//                .amount(new BigDecimal("9.99"))
//                .paymentMethod("VNPAY")
//                .paymentStatus(PaymentStatus.SUCCESS)
//                .build();
//        transRepo.save(trans1);
//
//        Transaction trans2 = Transaction.builder()
//                .user(regularUser)
//                .plan(proPlan)
//                .amount(new BigDecimal("29.99"))
//                .paymentMethod("STRIPE")
//                .paymentStatus(PaymentStatus.SUCCESS)
//                .build();
//        transRepo.save(trans2);
//
//        Transaction trans3 = Transaction.builder()
//                .user(admin)
//                .plan(proPlan)
//                .amount(new BigDecimal("29.99"))
//                .paymentMethod("CREDIT_CARD")
//                .paymentStatus(PaymentStatus.FAILED)
//                .build();
//        transRepo.save(trans3);
//        System.out.println("Thêm 3 transactions.");
//
//        // 5. Usage: 2 usage (1 cho admin tháng 10/2025, 1 cho user)
//        Usage adminUsage = Usage.builder()
//                .subscription(adminSub)
//                .period("2025-10")
//                .setCount(150)
//                .recipient(800)
//                .build();
//        usageRepo.save(adminUsage);
//
//        Usage userUsage = Usage.builder()
//                .subscription(userSub)
//                .period("2025-10")
//                .setCount(150)
//                .recipient(800)
//                .build();
//        usageRepo.save(userUsage);
//        System.out.println("Thêm 2 usages.");
//
//        // 6. Templates: 2 templates (1 HTML, 1 TEXT)
//        Template htmlTemplate = Template.builder()
//                .user(admin)
//                .name("Promo HTML")
//                .type(TemplateType.HTML)
//                .subject("Khuyến mãi 50% - {{name}}!")
//                .body("<h1>Xin chào {{name}}!</h1><p>Giảm giá hôm nay 31/10/2025.</p>")
//                .build();
//        htmlTemplate = templateRepo.save(htmlTemplate);
//
//        Template textTemplate = Template.builder()
//                .user(regularUser)
//                .name("Newsletter Text")
//                .type(TemplateType.TEXT)
//                .subject("Cập nhật tin tức")
//                .body("Chào {{name}}, bạn đã gửi {{sent_count}} emails tháng này.")
//                .build();
//        textTemplate = templateRepo.save(textTemplate);
//        System.out.println("Thêm 2 templates.");
//
//        // 7. Contact Lists: 2 lists (1 cho admin, 1 cho user)
//        ContactList adminList = ContactList.builder()
//                .user(admin)
//                .name("Danh sách Khách Hàng VN")
//                .description("Khách hàng Việt Nam")
//                .build();
//        adminList = listRepo.save(adminList);
//
//        ContactList userList = ContactList.builder()
//                .user(regularUser)
//                .name("Leads Marketing")
//                .description("Leads từ campaign")
//                .build();
//        userList = listRepo.save(userList);
//        System.out.println("Thêm 2 contact lists.");
//
//        // 8. Contacts: 6 contacts (3 cũ + 3 mới, phân bổ 3/list)
//        List<Contact> adminContacts = Arrays.asList(
//                Contact.builder().contactList(adminList).name("Nguyễn Văn A").email("a@vn.com").status(ContactStatus.SUBSCRIBED).build(),
//                Contact.builder().contactList(adminList).name("Trần Thị B").email("b@vn.com").phone("0123456789").status(ContactStatus.SUBSCRIBED).build(),
//                Contact.builder().contactList(adminList).name("Lê Văn C").email("c@vn.com").status(ContactStatus.UNSUBSCRIBED).build()
//        );
//        contactRepo.saveAll(adminContacts);
//
//        List<Contact> userContacts = Arrays.asList(
//                Contact.builder().contactList(userList).name("Phạm Thị D").email("d@marketing.vn").phone("0987654321").status(ContactStatus.SUBSCRIBED).build(),
//                Contact.builder().contactList(userList).name("Hoàng Văn E").email("e@sales.com").phone("0976543210").status(ContactStatus.BOUNCED).build(),
//                Contact.builder().contactList(userList).name("Vũ Thị F").email("f@newsletter.net").phone("0965432109").status(ContactStatus.UNSUBSCRIBED).build()
//        );
//        contactRepo.saveAll(userContacts);
//        System.out.println("Thêm 6 contacts (3/list).");
//
//        // 9. Campaigns: 3 campaigns (2 scheduled, 1 completed)
//        Campaign adminCampaign = Campaign.builder()
//                .user(admin)
//                .name("Black Friday Promo")
//                .messageContent("Nội dung đặc biệt hôm 31/10/2025")
//                .scheduleTime(LocalDateTime.of(2025, 10, 31, 18, 0))  // 6pm hôm nay
//                .template(htmlTemplate)
//                .contactList(adminList)
//                .status(CampaignStatus.SCHEDULED)
//                .build();
//        adminCampaign = campaignRepo.save(adminCampaign);
//
//        Campaign userCampaign1 = Campaign.builder()
//                .user(regularUser)
//                .name("Weekly Newsletter")
//                .template(textTemplate)
//                .contactList(userList)
//                .status(CampaignStatus.COMPLETED)
//                .build();
//        userCampaign1 = campaignRepo.save(userCampaign1);
//
//        Campaign userCampaign2 = Campaign.builder()
//                .user(regularUser)
//                .name("Test CampaignRepository")
//                .scheduleTime(LocalDateTime.of(2025, 11, 1, 9, 0))  // Mai
//                .contactList(userList)
//                .status(CampaignStatus.DRAFT)
//                .build();
//        userCampaign2 = campaignRepo.save(userCampaign2);
//        System.out.println("Thêm 3 campaigns.");
//
//        // 10. Sendlogs: 6 logs (2/campaign, mix status)
//        List<SendLog> adminLogs = Arrays.asList(
//                SendLog.builder().campaign(adminCampaign).contact(adminContacts.get(0)).status(EventStatus.SENT).providerResponse("{\"id\":\"log1\"}").build(),
//                SendLog.builder().campaign(adminCampaign).contact(adminContacts.get(1)).status(EventStatus.DELIVERED).providerResponse("{\"delivered\":\"yes\"}").build()
//        );
//        sendLogRepo.saveAll(adminLogs);
//
//        List<SendLog> userLogs1 = Arrays.asList(
//                SendLog.builder().campaign(userCampaign1).contact(userContacts.get(0)).status(EventStatus.OPENED).providerResponse("{\"open\":\"31/10/2025\"}").build(),
//                SendLog.builder().campaign(userCampaign1).contact(userContacts.get(1)).status(EventStatus.CLICKED).providerResponse("{\"click\":\"link\"}").build()
//        );
//        sendLogRepo.saveAll(userLogs1);
//
//        List<SendLog> userLogs2 = Arrays.asList(
//                SendLog.builder().campaign(userCampaign2).contact(userContacts.get(2)).status(EventStatus.FAILED).providerResponse("{\"error\":\"quota\"}").build(),
//                SendLog.builder().campaign(userCampaign2).contact(adminContacts.get(2)).status(EventStatus.BOUNCED).providerResponse("{\"bounce\":\"invalid\"}").build()
//        );
//        sendLogRepo.saveAll(userLogs2);
//        System.out.println("Thêm 6 sendlogs.");
//
//        System.out.println("Hoàn thành! Tổng: Users=2, Plans=2, Subs=2, Trans=3, Usage=2, Templates=2, Lists=2, Contacts=6, Campaigns=3, Logs=6.");
//    }
//}
