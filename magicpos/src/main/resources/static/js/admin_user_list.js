  const modal = document.getElementById("user-modal");
  const modalTitle = document.getElementById("modal-title");
  const submitBtn = document.getElementById("modal-submit-btn");
  const modeInput = document.getElementById("form-mode");

  const userNo = row.getAttribute("data-user-no");
  
  const userNoInput = document.getElementById("user-no");
  userNoInput.value = userNo;

  // 회원등록 버튼
  document.querySelector('.button-group .action:nth-child(1)').addEventListener('click', () => {
    modalTitle.textContent = "회원등록";
    submitBtn.textContent = "저장";
    modeInput.value = "register";
    userNoInput.value = "";
    document.getElementById("user-form").reset();
    modal.style.display = "flex";
  });

  // 회원수정 버튼
  document.querySelector('.button-group .action:nth-child(2)').addEventListener('click', () => {
    const checked = document.querySelector("tbody input[type='checkbox']:checked");
    if (!checked) {
      alert("수정할 회원을 선택하세요.");
      return;
    }

    const row = checked.closest("tr");
    const cells = row.querySelectorAll("td");

    // 예시: 각 필드를 모달에 채우기
    document.getElementById("username").value = cells[2].innerText;
    document.getElementById("user-id").value = cells[3].innerText;
    document.getElementById("birth").value = cells[4].innerText.replace(/\./g, '-'); // yyyy.MM.dd -> yyyy-MM-dd
    document.getElementById("phone").value = cells[5].innerText;
    document.getElementById("email").value = cells[6].innerText;
    document.getElementById("remainMin").value = parseInt(cells[7].innerText); // "123분" -> 123
    document.getElementById("usedMin").value = parseInt(cells[8].innerText); // "456분" -> 456

    // gender 등 추가 값은 서버에서 직접 가져오거나 hidden field로 넘기도록 처리
    
    const userNo = row.getAttribute("data-user-no");
    userNoInput.value = userNo;

    modalTitle.textContent = "회원수정";
    submitBtn.textContent = "수정";
    modeInput.value = "edit";
    document.getElementById("user-form").action = `/users/update`; 
    modal.style.display = "flex";
  });

  function closeModal() {
    modal.style.display = "none";
  }


// 준비중, 전달완료 상태 변경
function updateStatus(orderNo, status, el) {
  fetch('/orders/status', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: `no=${orderNo}&orderStatus=${status}`
  })
  .then(res => res.text())
  .then(data => {
    if (data === 'ok') {
      if (status === 2) {
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