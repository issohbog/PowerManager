
document.addEventListener('DOMContentLoaded', () => {
  // ✅ 모달 열기/닫기
  const modal = document.getElementById('orderModal');
  const openBtn = document.getElementById('openModalBtn');
  const closeBtn = document.getElementById('closeModalBtn');

  if (openBtn && modal) {
    openBtn.addEventListener('click', () => {
      modal.style.display = 'flex';
    });
  }

  if (closeBtn && modal) {
    closeBtn.addEventListener('click', () => {
      modal.style.display = 'none';
    });
  }

  // ✅ 주문 탭 전환
  const progressBtn = document.getElementById('progressBtn');
  const historyBtn = document.getElementById('historyBtn');
  const progressTab = document.getElementById('progressTab');
  const historyTab = document.getElementById('historyTab');

  if (progressBtn && historyBtn && progressTab && historyTab) {
    function activateTab(activeBtn, inactiveBtn, showTab, hideTab) {
      // 탭 내용 전환
      showTab.style.display = 'block';
      hideTab.style.display = 'none';

      // 버튼 스타일 전환
      activeBtn.style.backgroundColor = '#F4D03F';
      activeBtn.style.color = 'black';
      inactiveBtn.style.backgroundColor = 'white';
      inactiveBtn.style.color = 'black';
    }

    progressBtn.addEventListener('click', () => {
      activateTab(progressBtn, historyBtn, progressTab, historyTab);
    });

    historyBtn.addEventListener('click', () => {
      activateTab(historyBtn, progressBtn, historyTab, progressTab);
    });
  }

  // ✅ 카드 뒤집기
  const cards = document.querySelectorAll(".product-card");
  if (cards.length > 0) {
    cards.forEach(card => {
      card.addEventListener("click", () => {
        card.classList.toggle("flipped");
      });
    });
  }
});

document.addEventListener('DOMContentLoaded', function () {
    const paymentRadios = document.querySelectorAll('input[name="payment"]');
    const cashRadios = document.querySelectorAll('.cash-option');
    const manualInput = document.querySelector('input[name="cashManual"]');
    const orderForm = document.getElementById('orderForm');

    
    function updateCashRequirement() {
        const selectedPayment = document.querySelector('input[name="payment"]:checked')?.value;

        if (selectedPayment === '현금') {
            cashRadios.forEach(r => r.required = true);
        } else {
            cashRadios.forEach(r => r.required = false);
        }
    }
    // 💥 form 제출할 때 "직접 입력"이면 값 있는지 확인
    orderForm.addEventListener('submit', function (e) {
        const selectedPayment = document.querySelector('input[name="payment"]:checked')?.value;
        const selectedCash = document.querySelector('input[name="cash"]:checked')?.value;

        // 현금인데 아무 금액도 선택 안 했을 때도 막아주기
        if (selectedPayment === '현금' && !selectedCash) {
            e.preventDefault();
            alert('결제 금액을 선택해주세요!');
            return;
        }
        // "직접 입력" 선택했는데 값이 없으면 막아주기
        if (selectedPayment === '현금' && selectedCash === 'manual') {
            if (!manualInput.value || manualInput.value.trim() === '') {
                e.preventDefault(); // 제출 막기
                alert('금액을 입력해주세요!');
                manualInput.focus();
            }
        }
    });

    // 최초, 변경 시마다 cash required 업데이트
    updateCashRequirement();
    paymentRadios.forEach(radio => {
        radio.addEventListener('change', updateCashRequirement);
    });
});



// 주문 완료 모달 열기
document.addEventListener('DOMContentLoaded', () => {
  if (window.orderSuccess) {
    const modal = document.getElementById('orderCompleteModal');
    if (modal) modal.style.display = 'flex';

    const closeBtn = document.getElementById('closeOrderCompleteModalBtn');
    if (closeBtn) {
      closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
      });
    }
  }
});


// 요금제 구매 모달 열기 

document.addEventListener("DOMContentLoaded", () => {
  const openBtn = document.getElementById("openTicketBtn");
  const modal = document.getElementById("ticketModal");
  const ticketItem = modal.querySelector(".ticket-item");
  const cards = modal.querySelectorAll(".plan-card");

    if (openBtn && modal) {
    openBtn.addEventListener("click", () => {
      modal.style.display = "flex";
      modal.classList.remove("fade-out");

      // 이전 선택 초기화 
      ticketItem.innerHTML = "";
    });
    }

  console.log("카드 개수:", cards.length);
  console.log("ticketItem 존재 여부:", ticketItem !== null);

  cards.forEach(card => {
    console.log("카드 연결:", card.dataset.name);
    card.addEventListener("click", () => {
      const name = card.dataset.name;
      const price = parseInt(card.dataset.price).toLocaleString();
      const time = card.dataset.time;

      const itemHTML = `
        <div class="selected-ticket"
            data-price="${card.dataset.price}"
            data-time="${card.dataset.time}"
            data-ticket-no="${card.dataset.ticketNo}"                        
        >
          <div class="select-ticket-name">${name}</div>
          <div class="select-ticket-info">
            <span>${price}원 (${time}분)</span>
          </div>
        </div>
      `;

      ticketItem.innerHTML = itemHTML;

      
    });
  });
  const paymentBtn = document.querySelector(".ticket-payment-btn");

  let selectedTicket = null;

  cards.forEach(card => {
    card.addEventListener("click", () => {
      selectedTicket = {
        price: card.dataset.price,
        time: card.dataset.time,
        ticketNo: card.dataset.ticketNo,
        name: card.dataset.name
      };
    });
  });

  // TossPayments 객체를 한 번만 생성 (테스트용 client key)
  const clientKey = "test_ck_ZLKGPx4M3MGPnBZkRAlwrBaWypv1";
  const tossPayments = TossPayments(clientKey);

  // CSRF 토큰과 헤더 이름을 meta 태그에서 읽어옴
  const csrfToken = document.querySelector('meta[name=\"_csrf\"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name=\"_csrf_header\"]').getAttribute('content');

  // 결제 버튼 클릭 이벤트
  if (paymentBtn) {
    paymentBtn.addEventListener("click", async () => {
      if (!selectedTicket) {
        alert("요금제를 선택해주세요.");
        return;
      }

      // 사용자 번호 가져오기
      const userNo = document.getElementById("user-no").value;

      // UserTicketController에 결제 정보 요청
      try {
        const response = await fetch("/usertickets/payment-info", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken 
          },
          body: JSON.stringify({
            userNo: userNo,
            ticketNo: selectedTicket.ticketNo
          })
        });

        if (!response.ok) {
          alert("결제 정보 요청 실패");
          return;
        }

        const paymentInfo = await response.json();

        // Toss Payments 결제창 띄우기 (테스트 키 사용)
        tossPayments.requestPayment("카드", {
          amount: paymentInfo.amount,
          orderId: paymentInfo.orderId,
          orderName: paymentInfo.orderName,
          customerName: paymentInfo.customerName,
          successUrl: paymentInfo.successUrl,
          failUrl: paymentInfo.failUrl
        });
      } catch (e) {
        alert("결제 요청 중 오류 발생");
        console.error(e);
      }
    });
  }
});



// 요금제 결제 하지 않고 닫기 버튼(x)으로 모달 닫기 
function closeTicketModal() {
  const modal = document.getElementById("ticketModal");
  if (!modal) return;

  if (!modal.classList.contains("fade-out")) {
    modal.classList.add("fade-out");
  }

  setTimeout(() => {
    modal.style.display = "none";
    modal.classList.remove("fade-out");
  }, 300);
}



// 결제 성공 시 쿼리 파라미터로 모달 자동 오픈
(function() {
  const urlParams = new URLSearchParams(window.location.search);
  if (urlParams.get("payment") === "success") {
    if (typeof showPaymentSuccessModal === "function") {
      showPaymentSuccessModal();
    } else {
      // 함수가 아직 정의되지 않은 경우를 대비해 약간의 딜레이 후 재시도
      setTimeout(() => {
        if (typeof showPaymentSuccessModal === "function") {
          showPaymentSuccessModal();
        }
      }, 300);
    }
  }
})();

