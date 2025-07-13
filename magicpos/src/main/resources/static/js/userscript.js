
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



// 요금제 결제 완료 모달 열기 
document.addEventListener("DOMContentLoaded", () => {
  const showModalBtn = document.querySelector(".ticket-payment-btn"); // 결제하기 버튼
  const modal = document.getElementById("paymentSuccessModal");
  const closeBtn = modal.querySelector(".paysucc-modal-footer button");

  // 모달 열기
  showModalBtn.addEventListener("click", () => {
  const selectedTicket = document.querySelector(".selected-ticket");
  if (!selectedTicket) {
    alert("요금제를 선택해주세요.");
    return;
  }

  const remainTime = parseInt(selectedTicket.dataset.time);
  const price = parseInt(selectedTicket.dataset.price);
  const ticketNo = parseInt(selectedTicket.dataset.ticketNo);

  if (!remainTime || !price || !ticketNo) {
    alert("요금제 정보가 올바르지 않습니다.");
    return;
  }

  const userNoInput = document.getElementById("user-no");
  if (!userNoInput || !userNoInput.value) {
    alert("유저 정보가 없습니다.");
    return;
  }
  const userNo = parseInt(userNoInput.value);

  console.log({ userNo, ticketNo, remainTime });

  // 서버로 결제 정보 전송
  fetch("/usertickets/insert", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      uNo: userNo,
      tNo: ticketNo,
      remainTime: remainTime,
      payAt: new Date().toISOString()
    })
  })
  .then(response => response.text())  // ✅ 문자열로 받기
  .then(text => {
    if (text === "success") {
      modal.classList.remove("fade-out");
      modal.style.display = "flex"; // 성공 시 모달 열기
    } else {
      alert("결제 실패");
    }
  })
  .catch(err => {
    console.error(err);
    alert("에러 발생");
  });
  });

  // 모달 닫기
  closeBtn.addEventListener("click", () => {
    modal.style.display = "none";
  });
});

