function openAdminTicketModal() {
  const modal = document.getElementById("admin-ticket-modal");
  if (modal) {
    modal.style.display = "flex";
    loadTicketsToModal();       // 티켓 목록 불러오는 ajax 호출
  }
}

function closeAdminTicketModal() {
  const modal = document.getElementById("admin-ticket-modal");
  if (modal) {
    modal.style.display = "none";
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const openBtn = document.getElementById("openAdminTicketModalBtn");
  if (openBtn) {
    openBtn.addEventListener("click", (e) => {
      e.preventDefault();
      openAdminTicketModal();
    });
  }

  // 실시간 유저 검색 
  const input = document.getElementById('admin-user-search-input');
  const resultBox = document.getElementById('admin-user-search-result');
  const hiddenUserNoInput = document.getElementById('selected-user-no');

  let debounceTimer;                            // 타이핑이 멈췄을 때만 요청을 보내기 위한 타이머 변수 

  if (input) {                                  // 검색창에 글자가 입력되면 실행되는 코드 작성 (input 요소가 있을때만 작동!)
    input.addEventListener('input', () => {
      clearTimeout(debounceTimer);              // 타이머를 초기화 해서 계속 입력중이면 검색하지 않도록 함 
      const keyword = input.value.trim();       // 입력한 글자에서 앞 뒤 공백 제거해서 검색어로 저장

      if (keyword.length < 1) {                 // 아무 글자도 없으면 
        resultBox.innerHTML = '';               // 검색 결과를 비우고 
        return;                                 // 그만 실행함                                             
      }

      debounceTimer = setTimeout(() => {
        fetch(`/usertickets/admin/usersearch?keyword=${encodeURIComponent(keyword)}`)
          .then(response => response.json())                // json으로 받은 데이터 js에서 사용할 수 있도록 변환
          .then(data => {                                   // 이전 검색 결과 지우기 
            resultBox.innerHTML = '';                       // 결과 초기화
            resultBox.style.display = 'block';              // 검색 결과 창 다시 보여주기

            if (data.length === 0) {                        // 결과가 아무것도 없으면 안내 메세지 
              resultBox.innerHTML = '<li>일치하는 회원이 없습니다</li>';
              return;
            }

            data.forEach(user => {                          // 사용자 수만큼 li를 만들어 한명씩 보여줌
              const li = document.createElement('li');
              li.textContent = `${user.username} (${user.userId})`;     // 홍길동(hong1234) 이런식으로 작성
              li.dataset.userNo = user.userNo;                          // 선택했을때 쓸 수 있도록 userNo 도 숨겨서 저장
              resultBox.appendChild(li);                                // 만든 li를 검색 결과 박스 안에 추가 
            });
          });
      }, 300);                          // 0.3초동안 입력이 없으면 위 요청 실행
    });

    resultBox.addEventListener('click', (e) => {                    // 검색 결과 중 li를 클릭했을 때만 동작하는 이벤트 
      const li = e.target.closest('li');
      if (!li) return;
      
      input.value = li.textContent;                                 // 검색창에 클릭한 회원 이름 표시
      hiddenUserNoInput.value = li.dataset.userNo;                  // 숨은 input에 userNo 저장
      resultBox.innerHTML = '';                                     // 검색 결과 창 닫음
      resultBox.style.display = 'none';                             // ul도 화면에서 숨기기
      

    });

    // 포커스 아웃 시 검색 결과 닫기
    document.addEventListener('click', (e) => {                     // 바깥 아무곳이나 클릭하면 자동으로 결과 창 닫음
      if (!e.target.closest('.admin-search-bar')) {
        resultBox.innerHTML = '';
      }
    });
  }
});

// Ajax로 티켓 목록 불러와서 테이블에 바인딩
function loadTicketsToModal() {
  fetch("/usertickets/admin/tickets")
    .then(res => res.json())
    .then(ticketList => {
      const tbody = document.querySelector(".admin-ticket-table tbody");
      tbody.innerHTML = "";

      ticketList.forEach(ticket => {
        const row = `
          <tr>
            <td>${ticket.ticketName}</td>
            <td>${formatTime(ticket.time)}</td>
            <td>${ticket.price.toLocaleString()}원</td>
            <td><button class="admin-add-btn" data-tno="${ticket.no}" data-name="${ticket.ticketName}" data-time="${ticket.time}" data-price="${ticket.price}">＋</button></td>
          </tr>
        `;
        tbody.insertAdjacentHTML("beforeend", row);
      });
    })
    .catch(err => {
      console.error("티켓 목록 불러오기 실패:", err);
    });
}

// 시간 포맷: 분 → "시:분" 형식 (예: 40 → 00:40)
function formatTime(minutes) {
  const h = String(Math.floor(minutes / 60)).padStart(2, "0");
  const m = String(minutes % 60).padStart(2, "0");
  return `${h}:${m}`;
}

// document 전체에 이벤트 위임
document.addEventListener("click", (e) => {
  // + 버튼 클릭 시
  if (e.target.classList.contains("admin-add-btn")) {
    const btn = e.target;

    // data-* 속성에서 값 추출
    const name = btn.dataset.name;
    const time = formatTime(btn.dataset.time);
    const price = parseInt(btn.dataset.price).toLocaleString() + "원";

    // 우측 영역에 값 세팅
    document.querySelector(".admin-ticket-name").textContent = name;
    document.querySelector(".admin-ticket-info").textContent = `${price} (${time})`;

    // 선택된 티켓의 번호도 저장 (결제 API용)
    document.querySelector(".admin-confirm-btn").dataset.tno = btn.dataset.tno;
  }
});