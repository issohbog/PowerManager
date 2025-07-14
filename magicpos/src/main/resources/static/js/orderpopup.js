      console.log("📦 orderpopup.js 실행됨");
      // 주문현황 수량 갱신
      function refreshCounts() {
      fetch('/admin/orders/status/counts')
          .then(res => res.json())
          .then(data => {
          document.querySelector('.number-tab1 span').textContent =
              data.orderCount.toString().padStart(2, '0');
          document.querySelector('.number-tab2 span').textContent =
              data.prepareCount.toString().padStart(2, '0');
          });
      }
  // 반드시 먼저 선언해줘
  function closeCancelModal(orderNo) {
    const modal = document.getElementById(`cancel-modal-${orderNo}`);
    if (modal) {
      modal.remove(); // DOM에서 제거
    }
  }

  // 또는 window에 명시적으로 붙이기
  window.closeCancelModal = closeCancelModal;

  document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.sell-cancel').forEach(function (btn) {
      btn.addEventListener('click', function () {
        const orderNo = btn.dataset.orderNo;

        fetch(`/admin/cancel/${orderNo}`)
          .then(res => res.text())
          .then(html => {
            // 기존 모달 제거
            const existing = document.getElementById(`cancel-modal-${orderNo}`);
            if (existing) existing.remove();

            // 응답 HTML 삽입
            document.body.insertAdjacentHTML("beforeend", html);

            // 모달 보여주기
            const modal = document.getElementById(`cancel-modal-${orderNo}`);
            if (modal) modal.style.display = 'block';
          });
      });
    });
  });


// 페이지 로딩 시 수량 나옴
document.addEventListener("DOMContentLoaded", () => {
refreshCounts(); // 상태 숫자 최신화
});

// 준비중, 전달완료 상태 변경
function updateStatus(orderNo, status, el) {
    console.log('orderNo:', orderNo, 'status:', status);
    // const data = {
        //   no: orderNo,
        //   orderStatus: status,
        // }
        const formData = new FormData();    // Content-Type: multipart/form-data
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        formData.append("no", orderNo);
        formData.append("orderStatus", status);
        fetch('/admin/orders/status', {
            method: 'POST',
            headers: {
              [csrfHeader]: csrfToken
            },
            body: formData // FormData 객체를 전송
            })
            .then(res => res.text())
            .then(data => {
                if (data === 'ok') {
                    refreshCounts();
                    if (status == 2) {
                        // 전달완료 → 카드 제거
                        el.closest('.order-card').style.display = 'none';
                    } else {
                        // 준비중 → 강조 표시
                        const badges = el.parentElement.querySelectorAll('.badge');
                        badges.forEach(b => b.classList.remove('active'));
                        el.classList.add('active');
                    }
                } else {
                    alert("상태 변경 실패!");
                }
            });
        }
document.addEventListener("DOMContentLoaded", () => {
  const modal = document.getElementById("customConfirmModal");
  const modalMsg = document.getElementById("modalMessage");
  const confirmBtn = document.getElementById("modalConfirmBtn");
  const cancelBtn = document.getElementById("modalCancelBtn");

  let confirmAction = null; // 🔥 실행될 함수 저장

  // 모달 열기
  function openConfirmModal(message, onConfirm) {
    modalMsg.textContent = message;
    confirmAction = onConfirm;
    modal.style.display = "flex";
  }

  // 모달 닫기
  function closeModal() {
    modal.style.display = "none";
    confirmAction = null;
  }

  confirmBtn.addEventListener("click", () => {
    if (confirmAction) confirmAction();
    closeModal();
  });

  cancelBtn.addEventListener("click", () => {
    closeModal();
  });

  // ✅ 이벤트 위임
  document.body.addEventListener("click", function (e) {
    const target = e.target;

    // 수량 변경 버튼 (.quantity-change-btn 포함하는 요소)
    if (target.closest(".cartcontrolBtn")) {
      e.preventDefault();
      const form = target.closest("form");
      openConfirmModal("수량을 변경하시겠습니까?", () => form.submit());
    }

    // 상품 삭제 버튼 (.deleteBtn 포함)
    if (target.closest(".deleteBtn")) {
      e.preventDefault();
      const form = target.closest("form");
      openConfirmModal("삭제하시겠습니까?", () => form.submit());
    }
  });

  // ✅ submit 이벤트 위임 (전체 주문 취소 전용)
  document.body.addEventListener("submit", function (e) {
    if (e.target && e.target.classList.contains("order-delete-form")) {
      e.preventDefault();
      const form = e.target;
      openConfirmModal("전체 주문을 취소하시겠습니까?", () => form.submit());
    }
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const popup = document.getElementById("orderPopup");
  const toggleBtn = document.querySelector(".toggle-btn");
  const productBtn = document.getElementById("toggle-orderpopup");

  function showPopup() {
    if (popup) popup.style.display = "flex";
  }

  if (toggleBtn) {
    toggleBtn.addEventListener("click", (e) => {
      e.preventDefault();
      console.log("⬅ 버튼 눌림");
      showPopup();
    });
  }

  if (productBtn) {
    productBtn.addEventListener("click", (e) => {
      e.preventDefault();
      console.log("🛒 상품 버튼 눌림");
      showPopup();
    });
  }
});

