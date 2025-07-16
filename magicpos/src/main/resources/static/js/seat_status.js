
// HTML <meta>에서 CSRF 토큰 정보 읽기
const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');


document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll(".clear-btn").forEach(btn => {
    btn.addEventListener("click", async (e) => {
      const seatId = e.currentTarget.getAttribute("data-seat-id");
      if (!seatId) return;

      try {
        const res = await fetch(`/admin/seats/clear/${seatId}`, {
          method: "POST",
          headers: {
            [csrfHeader]: csrfToken
          }
        });

        const text = await res.text();

        if (text === "success") {
          location.reload(); // 또는 UI만 업데이트
        } else if (text === "fail") {
          alert("좌석 상태 변경에 실패했습니다.");
        } else {
          alert("서버 오류 발생");
        }
      } catch (err) {
        console.error("요청 중 오류:", err);
        alert("요청 처리 중 오류가 발생했습니다.");
      }
    });
  });
});