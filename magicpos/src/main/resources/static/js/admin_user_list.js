  console.log("loaded")

  // 회원등록 버튼
  document.querySelector('.button-group .action:nth-child(1)').addEventListener('click', () => {
    modalTitle.textContent = "회원등록";
    submitBtn.textContent = "저장";
    modeInput.value = "register";
    userNoInput.value = "";
    document.getElementById("user-form").reset();
    idWrapper.classList.add("inline-field");
    idCheckBtn.style.display = "inline-block"; 
    modal.style.display = "flex";
  });

  // 아이디 중복체크 
  idCheckBtn.addEventListener("click", async () => {
  const userId = idInput.value.trim();
  idMessage.textContent = ""; // 초기화
  idMessage.classList.remove("success", "error");

  if (!userId) {
    idMessage.textContent = "아이디를 입력해주세요.";
    idMessage.classList.add("error");
    return;
  }

  try {
    const response = await fetch(`/users/admin/check-id?id=${encodeURIComponent(userId)}`);
    
    console.log("response.ok:", response.ok);
    console.log("response.status:", response.status);
    
    if (!response.ok) throw new Error("서버 오류");

    

    const result = await response.json();
    console.log("result: ", result); 
    if (result.exists) {
      idMessage.textContent = "이미 사용 중인 아이디입니다.";
      idMessage.classList.add("error");
    } else {
      idMessage.textContent = "사용 가능한 아이디입니다.";
      idMessage.classList.add("success");
    }
  } catch (err) {
    console.error(err);
    idMessage.textContent = "중복확인 중 오류 발생";
    idMessage.classList.add("error");
  }
  });



  // 회원수정 버튼
  document.querySelector('.button-group .action:nth-child(2)').addEventListener('click', async () => {
  const checkedBoxes = document.querySelectorAll("tbody input[type='checkbox']:checked");

  if (checkedBoxes.length === 0) {
    alert("수정할 회원을 선택하세요.");
    return;
  }

  if (checkedBoxes.length > 1) {
    alert("회원 한 명만 선택해주세요.");
    return;
  }

  const checked = checkedBoxes[0];
  const row = checked.closest("tr");
  const userNo = row.getAttribute("data-user-no");

  try {
    // 서버에서 사용자정보 + 사용시간/남은시간 가져오기 
    const url = `/users/admin/${userNo}/info`;
    const response = await fetch(url);

    console.log("fetch url:", url);
    console.log("response.ok:", response.ok);
    console.log("response status:", response.status);

    if( !response.ok){
      throw new Error(`서버응답오류 : ${response.status}`);
    }

    const data = await response.json();
    console.log("data: ", data);

    const user = data.user;
    if (!user) {
    console.error("user 정보 없음", data);
    alert("사용자 정보가 없습니다.");
    return;
    }

    // input 요소 채우기 
    document.getElementById("username").value = user.username;
    document.getElementById("user-id").value = user.id;
    document.getElementById("birth").value = user.birth?.substring(0, 10);
    document.getElementById("phone").value = user.phone;
    document.getElementById("email").value = user.email;
    document.getElementById("memo").value = user.memo; 
    document.getElementById("remainMin").value = data.remainTime;
    document.getElementById("usedMin").value = data.usedTime;

    userNoInput.value = user.no;
    modalTitle.textContent = "회원수정";
    submitBtn.textContent = "수정";
    modeInput.value = "edit";
    document.getElementById("user-form").action = `/users/update`; 
    idWrapper.classList.remove("inline-field");
    idCheckBtn.style.display = "none";
    modal.style.display = "flex";


  } catch (error) {
    console.error("회원정보 불러오기 실패", error);
    alert("회원 정보를 불러오지 못했습니다.");
  }

    
  });
  // 모달 닫기 
  function closeModal() {
    modal.style.display = "none";
  }



// 임시 비밀번호 모달 닫기
function closeTempPasswordModal() {
  const tempModal = document.getElementById("temp-password-modal");
  tempModal.style.display = "none";
}









