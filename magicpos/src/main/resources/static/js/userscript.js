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

