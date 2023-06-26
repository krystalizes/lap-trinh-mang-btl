# FTP Server Client
 
<h1>BÁO CÁO BÀI TẬP LỚN MÔN LẬP TRÌNH MẠNG HỌC KÌ I NĂM HỌC 2022 - 2023</h1>
<span>Nhóm 4_23</span>

<h2>1. Giới thiệu sơ lược chủ đề </h2>
<h3>a. Mục tiêu</h3>
- Gửi file lên server, tải file từ server, xem danh sách file trên server, tìm kiếm file trên server
<h3>b. Kết quả đạt được</h3>
- Đạt được mục tiêu đề ra <br>
- Xây dựng được giao diện Client, Server, down/upload file <br>
- Hỗ trợ nhiều client cùng lúc
<h3>c. Hạn chế, hướng phát triển</h3>
- Chưa tạm dừng, hủy bỏ download, upload <br>
- Chưa xác thực người dùng <br>
- Chưa tải nhiều file cùng lúc trên một client <br>
<h2>2. Tổng hợp công việc</h2>
<table>
 <tr>
  <th>STT</th>
  <th>Mã sinh viên - Họ tên</th>
  <th>Các nội dung thực hiện</th>
  <th>Thể hiện</th>
  <th>Ghi chú</th>
 </tr>
 <tr>
  <td>1</td>
  <td>B19DCCN060 - Phan Vương Bảo</td>
  <td>
   -[23/09 - 29/09]: Khởi tạo socket kết nối với client, gửi và nhận file đơn luồng<br>
   -[30/09 - 10/10]: Cài đặt đa luồng cho server
  </td>
  <td>
  ClientHandler.java<br>
  ServerThread.java
  </td>
  <td></td>
 </tr>
 <tr>
  <td>2</td>
  <td>B19DCCN105 - Đới Thành Chung</td>
  <td>
   -[23/09 - 10/10]: Thiết kế giao diện cho client và server<br>
  </td>
  <td>
  MyOptionPane.java<br>
  ConnectionForm.java <br>
  ServerForm.java<br>
  ClientForm.java
  </td>
  <td></td>
 </tr>
  <tr>
  <td>3</td>
  <td>B19DCCN123 - Lê Văn Dũng</td>
  <td>
   -[23/09 - 29/09]: Khởi tạo socket kết nối với server, gửi và nhận file<br>
  </td>
  <td>
  DownloadFile.java<br>
  UploadFile.java
  </td>
  <td></td>
 </tr>
</table>

<h2>3. Quá trình phát triển</h2>
<table>
 <tr>
  <th>STT</th>
  <th>Xử lý</th>
  <th>Vấn đề</th>
  <th>Xử lí</th>
  <th>Tự đánh giá</th>
  <th>Người thực hiện</th>
 </tr>
 <tr>
  <td>1</td>
  <td>1.0 19/10</td>
  <td>- Thanh Progress bar không hiển thị đúng % download, upload<br>
  - Không hiển thị được các file trong thư mục con, chưa hiển thị kích thước file
  </td>
  <td>
  - Sử dụng SwingWorker<br>
  - Sửa lại hàm getAllFile()<br>
  - Thêm tùy chọn mở file sau khi tải xong
  </td>
  <td>Đạt</td>
  <td>Cả nhóm</td>
 </tr>
</table>

<h2>4. Mã nguồn</h2>
https://github.com/jnp2018/fa-4_23
