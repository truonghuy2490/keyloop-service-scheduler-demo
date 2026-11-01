This is a [Next.js](https://nextjs.org) project bootstrapped with [`create-next-app`](https://nextjs.org/docs/app/api-reference/cli/create-next-app).

## Getting Started

First, run the development server:

```bash
npm run dev
# or
yarn dev
# or
pnpm dev
# or
bun dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

You can start editing the page by modifying `app/page.tsx`. The page auto-updates as you edit the file.

This project uses [`next/font`](https://nextjs.org/docs/app/building-your-application/optimizing/fonts) to automatically optimize and load [Geist](https://vercel.com/font), a new font family for Vercel.

## Learn More

To learn more about Next.js, take a look at the following resources:

- [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API.
- [Learn Next.js](https://nextjs.org/learn) - an interactive Next.js tutorial.

You can check out [the Next.js GitHub repository](https://github.com/vercel/next.js) - your feedback and contributions are welcome!

## Deploy on Vercel

The easiest way to deploy your Next.js app is to use the [Vercel Platform](https://vercel.com/new?utm_medium=default-template&filter=next.js&utm_source=create-next-app&utm_campaign=create-next-app-readme) from the creators of Next.js.

Check out our [Next.js deployment documentation](https://nextjs.org/docs/app/building-your-application/deploying) for more details.

---

Theo tôi, bạn hoàn toàn có thể thiết kế Appointment Booking Automotive theo tư duy đặt vé máy bay, và đây còn là một hướng khá tốt cho Scenario A – The Unified Service Scheduler.

Tuy nhiên, có một điểm cần điều chỉnh: thay vì chọn ServiceBay như chọn sân bay, tôi khuyên bạn nên để hệ thống tìm kiếm các tổ hợp tài nguyên khả dụng (Available Resource Slots) dựa trên yêu cầu đặt lịch.

Lý do là trong automotive booking, một appointment thường cần đồng thời nhiều tài nguyên:

- ServiceBay: Khoang sửa chữa phù hợp với loại dịch vụ.
- Technician: Nhân viên có kỹ năng phù hợp.
- Time Slot: Khoảng thời gian đủ để hoàn thành dịch vụ.
- Dealership: Đại lý nơi thực hiện dịch vụ.

Nếu chỉ chọn một ServiceBay rồi mới tìm giờ, bạn có thể bỏ lỡ những khung giờ mà ServiceBay khác đang trống.

## 1. So sánh với cách đặt vé máy bay

[Budapest Airport - Budapest, Hungary | Trip.com Budapest](https://images.openai.com/static-rsc-4/gnovx18qgZCjwpy0uzVBLcRdbhkpFmBbSiG-wGBlo9Yv--0ivVVucdmriFN8Ey8mELK1-2gxzMtetmzCVsFfgXC_QsxMdQXhjtwzero71gf31xN6QvIkculufiAq2oc3KZ9kGDcGdZs4eNQui_AsFP6cF_GXElo4Cas-n-MDeDM?purpose=inline)

Flight Booking

1. Chọn sân bay đi / đến.
2. Chọn ngày bay.
3. Hệ thống tìm chuyến bay khả dụng.
4. Chọn chuyến bay và ghế.
5. Xác nhận đặt vé.

[Alle vacatures | Werken bij Innovam](https://images.openai.com/static-rsc-4/RAJ7qiWZjZn0HMdPvADZnrltk8N0wc6j4AKNrT__4eGsb42984b8Cv3X3Y4xaBxPtEY7tLe5JF46ovOXI-OESfqI2OaI-oqm1lH0A4spQpmrd3JnkyY-cceqvX9VMPN1WQEwCzuvLinMECso1QuNr9z5x0UjBWlTKguPFYKPOOM?purpose=inline)

Automotive Appointment Booking

1. Chọn Dealership.
2. Chọn Vehicle và Service Type.
3. Chọn ngày muốn đặt hẹn.
4. Hệ thống tìm ServiceBay và Technician phù hợp.
5. Hiển thị các khung giờ khả dụng.
6. Xác nhận appointment.

Điểm khác biệt là chuyến bay thường có lịch trình cố định, trong khi ServiceBay và Technician có thể được phân bổ động theo từng appointment.

## 2. Flow tôi đề xuất

Tôi khuyên nên thiết kế theo hướng người dùng chọn nhu cầu trước, hệ thống tự tìm tài nguyên sau.

## Recommended Booking Flow

1. Select Dealership

   Người dùng chọn đại lý muốn sử dụng dịch vụ.
2. Select Vehicle & Service

   Ví dụ: Toyota Camry → Oil Change.

   Hệ thống xác định loại ServiceBay và kỹ năng Technician cần thiết.
3. Select Appointment Date

   Người dùng chọn ngày muốn đặt lịch.
4. Find Available Slots

   Backend tìm các khoảng thời gian mà đồng thời thỏa mãn:
   - ServiceBay còn trống.
   - Technician có kỹ năng phù hợp và còn trống.
   - Thời gian đủ để hoàn thành dịch vụ.
5. Display Available Slots

   Ví dụ giao diện:

   November 5, 2026

   08:00 – 09:30

   John Smith

   Bay 01

   09:00 – 10:30

   David Lee

   Bay 02

   13:00 – 14:30

   Michael Tran

   Bay 01

   14:30 – 16:00

   John Smith

   Bay 03

   Minh họa dữ liệu, không phải lịch khả dụng thực tế.
6. Confirm Appointment

   Người dùng chọn slot và xác nhận. Backend kiểm tra lại availability trước khi lưu booking.

## 3. Có nên cho người dùng chọn ServiceBay trước không?

Theo tôi, có hai cách thiết kế:

| Approach                   | Ưu điểm                     | Nhược điểm                                            |
| -------------------------- | --------------------------- | ----------------------------------------------------- |
| User chọn ServiceBay trước | Chủ động chọn bay           | Khó sử dụng, người dùng thường không quan tâm bay nào |
| System tự chọn ServiceBay  | Đơn giản, tối ưu tài nguyên | Ít quyền kiểm soát hơn                                |

Tôi chọn cách thứ hai làm default.

Ví dụ người dùng chỉ cần chọn:

- Dealership: Toyota Center
- Vehicle: Camry
- Service: Oil Change
- Date: November 5

Hệ thống tự tìm ra:

- Bay 01 + John Smith: 08:00–09:30
- Bay 02 + David Lee: 09:00–10:30
- Bay 03 + Michael Tran: 13:00–14:30

Người dùng chỉ cần chọn giờ phù hợp.

Nếu muốn nâng cấp, bạn có thể cho phép người dùng chọn Technician cụ thể hoặc ưu tiên một nhân viên nhất định.

## 4. Backend API nên thiết kế như thế nào?

Với flow này, tôi đề xuất các API cốt lõi:

| Method | Endpoint                        | Purpose                  |
| ------ | ------------------------------- | ------------------------ |
| GET    | `/api/dealerships`              | Lấy danh sách đại lý     |
| GET    | `/api/vehicles/{id}`            | Lấy thông tin xe         |
| GET    | `/api/services`                 | Lấy danh sách dịch vụ    |
| GET    | `/api/availability`             | Tìm các slot khả dụng    |
| POST   | `/api/appointments`             | Tạo appointment          |
| GET    | `/api/appointments/{id}`        | Xem chi tiết appointment |
| PUT    | `/api/appointments/{id}/cancel` | Hủy appointment          |

API quan trọng nhất chính là `GET /api/availability`.

Ví dụ request:

```
GET /api/availability?
    dealershipId=1&
    vehicleId=10&
    serviceId=2&
    date=2026-11-05
```

Response minh họa:

```
{
  "date": "2026-11-05",
  "serviceDuration": 90,
  "availableSlots": [
    {
      "startTime": "08:00",
      "endTime": "09:30",
      "serviceBay": {
        "id": 1,
        "name": "Bay 01"
      },
      "technician": {
        "id": 5,
        "name": "John Smith"
      }
    },
    {
      "startTime": "09:00",
      "endTime": "10:30",
      "serviceBay": {
        "id": 2,
        "name": "Bay 02"
      },
      "technician": {
        "id": 8,
        "name": "David Lee"
      }
    }
  ]
}
```

Lưu ý: `serviceDuration` nên được xác định từ Service Type, không nên để frontend tự quyết định.

## 5. Logic kiểm tra availability hoạt động thế nào?

Đây là phần quan trọng nhất về mặt technical design.

Giả sử khách hàng muốn đặt dịch vụ Oil Change trong 90 phút.

Backend sẽ thực hiện:

1. Tìm tất cả ServiceBay thuộc Dealership.
2. Lọc những Bay phù hợp với loại dịch vụ.
3. Tìm Technician có skill tương ứng.
4. Kiểm tra lịch làm việc của Technician.
5. Kiểm tra các appointment đã được đặt của cả Bay và Technician.
6. Tạo các time slot có thể đáp ứng đồng thời cả hai điều kiện.

Ví dụ:

Availability matching

November 5 · 08:00–12:00

ServiceBay 01

Available

Technician John

Available

08:00–09:30

Valid slot

Minh họa: một slot chỉ hợp lệ khi cả hai tài nguyên đều khả dụng trong toàn bộ khoảng thời gian.

Một appointment chỉ được xem là khả dụng khi:

```
ServiceBay is available
AND
Technician is available
AND
Technician has required skills
AND
ServiceBay supports required service
```

Một lưu ý quan trọng: Hai appointment bị overlap nếu:

```
existingStart < requestedEnd
    && existingEnd > requestedStart
```

Bạn cần kiểm tra điều này cho cả ServiceBay và Technician.

## 6. Một vấn đề rất quan trọng: Race Condition

Giả sử:

- User A kiểm tra slot 08:00 → Available.
- User B kiểm tra slot 08:00 → Available.
- User A xác nhận booking.
- User B cũng xác nhận booking.

Nếu không kiểm tra lại, cả hai có thể đặt cùng một ServiceBay và Technician.

Vì vậy, `POST /api/appointments` phải:

1. Mở database transaction.
2. Lock hoặc bảo vệ các tài nguyên liên quan.
3. Kiểm tra availability lần nữa.
4. Nếu còn trống thì tạo appointment.
5. Nếu đã bị đặt thì trả về `409 Conflict`.

Không nên chỉ dựa vào kết quả của API availability trước đó.

## 7. Kiến trúc tôi khuyên bạn triển khai

Với Scenario A, tôi sẽ chia thành các thành phần:

```
Appointment
    |
    v
AppointmentService
    |
    v
AvailabilityService
    |
    +---- ServiceBayRepository
    |
    +---- TechnicianRepository
    |
    +---- AppointmentRepository
    |
    v
Availability Matching
    |
    v
Available Time Slots
```

Các Domain Entity chính:

- `Dealership`
- `Vehicle`
- `ServiceType`
- `ServiceBay`
- `Technician`
- `TechnicianSkill`
- `Appointment`

Bạn chưa cần xây dựng hệ thống flight-like scheduling quá phức tạp. Có thể bắt đầu với time slot cố định (ví dụ 30 phút), sau đó tính toán duration theo từng ServiceType.

## Kết luận

Tôi đề xuất bạn chọn mô hình Service-first, Date-second, Availability-driven thay vì Bay-first.

| Step | User action       | System action                          |
| ---- | ----------------- | -------------------------------------- |
| 1    | Select Dealership | Load dealership                        |
| 2    | Select Vehicle    | Validate vehicle                       |
| 3    | Select Service    | Determine required skills and duration |
| 4    | Select Date       | Search available resources             |
| 5    | Select Time Slot  | Display matching Bay + Technician      |
| 6    | Confirm           | Revalidate and create appointment      |

Một điểm đáng chú ý khi trình bày Scenario A trong interview: Bạn không chỉ xây dựng CRUD Appointment mà đang giải quyết bài toán Resource Scheduling, trong đó nhiều tài nguyên phải được phân bổ đồng thời mà không xảy ra double booking.

Đây chính là phần có giá trị technical nhất của project này.