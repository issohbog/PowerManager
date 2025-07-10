document.addEventListener("DOMContentLoaded", () => {
  initCategoryModal();
  initProductModal();
  initUpdateModal();

  const imageBtn = document.querySelector('.image-btn');
  const imageInput = document.getElementById('product-image-input');
  const placeholder = document.querySelector('.image-placeholder');

  imageBtn.addEventListener('click', () => {
    imageInput.click(); // 파일 선택창 띄우기
  });

  // ------------------------- 상품 등록 요청 시작 ---------------------
  imageInput.addEventListener('change', (event) => {
    const file = event.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = function(e) {
      // 이미지를 미리보기로 표시
      placeholder.innerHTML = `<img src="${e.target.result}" alt="미리보기" style="max-width: 100%; height: auto;">`;
    };
    reader.readAsDataURL(file);
  });

  document.querySelector(".btn-save").addEventListener("click", () => {
  const formData = new FormData();

  const category = document.getElementById("product-category").value;
  const name = document.getElementById("product-name").value;
  const price = document.getElementById("product-price").value;
  const description = document.getElementById("product-desc").value;
  if (!category || !name || !price) {
  alert("상품분류, 상품명, 상품가격은 필수 항목입니다.");
  return;zzzzz
  }

  formData.append("cNo", category);
  formData.append("pName", name);
  formData.append("description", description);
  formData.append("pPrice", price);

  // 노출설정
  formData.append("sellStatus", document.getElementById("pc-sale").checked);

  // 이미지
  const fileInput = document.getElementById("product-image-input");
  const file = fileInput.files[0];
  if (file) {
    formData.append("imageFile", file);
  }

  fetch("/products/admin/create", {
    method: "POST",
    body: formData
  })
    .then(res => res.ok ? res.text() : Promise.reject("상품 등록 실패"))
    .then(result => {
      alert("상품 등록 완료!");
      location.reload(); // 또는 모달 닫고 목록 갱신
    })
    .catch(err => alert(err));
  });
  // --------------------- 상품 등록 요청 완료 -----------------------





});

function initCategoryModal() {
  const modal = document.getElementById("category-modal");
  if (!modal) return;

  document.querySelector(".category-add-btn")?.addEventListener("click", () => {
    modal.classList.remove("fade-out");
    modal.classList.add("fade-in");
    modal.style.display = "flex";
  });

  document.querySelector(".category-close-btn")?.addEventListener("click", () => closeModal(modal));
  document.querySelector(".category-button-group .cancel-btn")?.addEventListener("click", () => closeModal(modal));
  modal.addEventListener("click", e => {
    if (e.target === modal) closeModal(modal);
  });
}

function initProductModal() {
  const modal = document.getElementById("product-modal");
  if (!modal) return;

  document.querySelector(".product-add-btn")?.addEventListener("click", () => {
    modal.classList.remove("fade-out");
    modal.classList.add("fade-in");
    modal.style.display = "flex";
  });

  document.querySelector(".product-close-btn")?.addEventListener("click", () => {
    resetProductForm();
    closeModal(modal);
  });

  document.querySelector(".product-button-group .cancel-btn")?.addEventListener("click", () => {
    resetProductForm();
    closeModal(modal);
  });

  modal.addEventListener("click", e => {
    if (e.target === modal) {
      resetProductForm();
      closeModal(modal);
    }
  });
}

// 상품 수정 
function initUpdateModal() {
  const modal = document.getElementById("edit-product-modal");
  if (!modal) return;

  // ✅ 상품 수정 버튼 클릭 시 모달 열기
  document.querySelector(".product-update-btn")?.addEventListener("click", () => {
    const checked = document.querySelector("tbody input[type='checkbox']:checked");
    if (!checked) {
      alert("수정할 상품을 선택하세요.");
      return;
    }

    const allChecked = document.querySelectorAll("tbody input[type='checkbox']:checked");
    if (allChecked.length > 1) {
      alert("하나의 상품만 선택해주세요.");
      return;
    }

    const row = checked.closest("tr");

    // ✅ 데이터 속성 가져오기 (HTML에서 tr에 data-* 추가되어 있어야 함)
    const productNo = row.dataset.productNo;
    const categoryNo = row.dataset.categoryNo;
    const pname = row.dataset.pname;
    const desc = row.dataset.description;
    const price = row.dataset.price;
    const sellStatus = row.dataset.sellStatus === "true";

    // ✅ 모달 열기
    modal.style.display = "flex";
    modal.classList.remove("fade-out");
    modal.classList.add("fade-in");

    // ✅ 수정 폼 값 채우기
    document.getElementById("edit-product-category").value = categoryNo;
    document.getElementById("edit-product-name").value = pname;
    document.getElementById("edit-product-desc").value = desc;
    document.getElementById("edit-product-price").value = price;
    document.getElementById("edit-pc-sale").checked = sellStatus;

    // ✅ 제품 번호를 data 속성으로 저장
    modal.setAttribute("data-product-no", productNo);

    // 이미지 미리보기 초기화 (추가로 이미지 경로 표시하려면 여기에 작성)
    const placeholder = modal.querySelector(".image-placeholder");
    placeholder.innerHTML = `<img src="" alt="미리보기">`; // 기존 이미지 경로가 있다면 여기서 세팅 가능
  });

  // ✅ 닫기 버튼 클릭 시
  modal.querySelector(".product-close-btn")?.addEventListener("click", () => {
    closeModal(modal);
  });

  // ✅ 모달 외부 클릭 시 닫기
  modal.addEventListener("click", (e) => {
    if (e.target === modal) {
      closeModal(modal);
    }
  });

  // ✅ 수정 버튼 클릭 시 서버로 전송
  modal.querySelector(".btn-update")?.addEventListener("click", () => {
    const productNo = modal.getAttribute("data-product-no");
    const formData = new FormData();
    formData.append("no", productNo);
    formData.append("cNo", document.getElementById("edit-product-category").value);
    formData.append("pName", document.getElementById("edit-product-name").value);
    formData.append("description", document.getElementById("edit-product-desc").value);
    formData.append("pPrice", document.getElementById("edit-product-price").value);
    formData.append("sellStatus", document.getElementById("edit-pc-sale").checked);

    const file = document.getElementById("edit-product-image-input").files[0];
    if (file) {
      formData.append("imageFile", file);
    }

    fetch("/products/admin/update", {
      method: "POST",
      body: formData
    })
      .then(res => res.ok ? res.text() : Promise.reject("상품 수정 실패"))
      .then(msg => {
        alert("상품 수정 완료!");
        location.reload(); // 또는 테이블만 갱신
      })
      .catch(err => alert(err));
  });
}


function closeModal(modal) {
  modal.classList.remove("fade-in");
  modal.classList.add("fade-out");
  setTimeout(() => {
    modal.style.display = "none";
    modal.classList.remove("fade-out");
  }, 300);
}

// 상품 등록 후 입력 창 초기화 
function resetProductForm() {
  document.getElementById("product-category").value = "";
  document.getElementById("product-name").value = "";
  document.getElementById("product-price").value = "";
  document.getElementById("product-desc").value = "";
  document.getElementById("pc-sale").checked = false;
  document.getElementById("product-image-input").value = "";

  const placeholder = document.querySelector('.image-placeholder');
  placeholder.innerHTML = "제품 이미지 등록"; // 또는 기본 placeholder 이미지/텍스트
}
