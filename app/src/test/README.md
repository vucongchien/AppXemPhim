# 🧪 Test Suite Documentation - AppXemPhim

Tài liệu mục lục mô tả kế hoạch kiểm thử, danh mục test cases và hướng dẫn thực thi Unit Test cho dự án **AppXemPhim**.

---

## 📑 1. Mục Lục Test Cases (Test Catalog)

| ID | Module / Lớp kiểm thử | Mục tiêu kiểm thử | Scenarios / Cases chính |
| :--- | :--- | :--- | :--- |
| **TC-AUTH-01** | `TokenAuthenticatorTest` | Kiểm tra luồng Silent Refresh Token khi nhận HTTP 401 | - Bắt 401, gọi API refresh thành công -> Retry request với Bearer token mới.<br>- Refresh token lỗi hoặc hết hạn -> Dừng retry, trả về null.<br>- Ngăn chặn vòng lặp vô hạn khi count >= 2. |
| **TC-MEDIA-01** | `ResumePositionAlgorithmTest` | Kiểm tra thuật toán khôi phục tiến độ xem video | - `person_view` < 5% -> Không seek (xem từ đầu 00:00).<br>- `person_view` > 95% -> Không seek (đã xem xong phần lớn).<br>- 5% <= `person_view` <= 95% -> Tính chính xác `resumePosition = duration * percent / 100`. |
| **TC-MEDIA-02** | `TimeFormattingTest` | Kiểm tra hàm format thời gian hiển thị | - Format `0 ms` -> `00:00:00` hoặc `00:00`.<br>- Format `3665000 ms` -> `01:01:05`. |
| **TC-COMMENT-01**| `CommentViewModelTest` | Kiểm tra thuật toán gom nhóm cây bình luận | - Phân biệt chính xác parent comments (`parent_comment_id` rỗng hoặc null).<br>- Thuật toán đệ quy `getDescendants()` gom đúng toàn bộ reply thuộc về comment cha. |
| **TC-COMMENT-02**| `AtomicLikeTransactionTest` | Kiểm tra logic tăng/giảm like nguyên tử | - Like comment -> currentLike + 1.<br>- Bỏ like comment -> max(0, currentLike - 1). |
| **TC-SHOWTIME-01**| `ShowTimeWeekdayHelperTest` | Kiểm tra hàm chuyển đổi Calendar weekday | - Sunday (1) -> index 6.<br>- Monday (2) -> index 0.<br>- Saturday (7) -> index 5. |
| **TC-STATE-01** | `ResourceStateTest` | Kiểm tra đóng gói trạng thái `Resource<T>` | - Resource.loading() -> status = LOADING.<br>- Resource.success(data) -> status = SUCCESS, getData() == data.<br>- Resource.error(msg) -> status = ERROR, getMessage() == msg. |

---

## 🛠️ 2. Công Cụ & Thư Viện Kiểm Thử Sử Dụng

* **JUnit 4**: Framework kiểm thử đơn vị cơ bản.
* **Mockito / MockWebServer**: Mock HTTP responses từ Retrofit API và Firebase Callbacks.
* **AndroidX Arch Core Testing**: `InstantTaskExecutorRule` để test `LiveData` và `MutableLiveData` đồng bộ trên main thread.

---

## 🚀 3. Hướng Dẫn Chạy Test

### Chạy qua dòng lệnh (Command Line / Terminal):
```bash
# Chạy toàn bộ Unit Test của module app
./gradlew testDebugUnitTest

# Chạy một test class cụ thể
./gradlew testDebugUnitTest --tests "com.example.appxemphim.TokenAuthenticatorTest"
```

### Chạy qua Android Studio:
1. Mở thư mục `app/src/test/java/com/example/appxemphim/`.
2. Click chuột phải vào file test hoặc thư mục test.
3. Chọn **Run 'Tests in com.example.appxemphim'** (Ctrl + Shift + F10).
