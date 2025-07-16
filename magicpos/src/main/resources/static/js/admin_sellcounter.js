function openAdminsellcounter() {
  const modal = document.getElementById("adminsellcounter");
  if (modal) {
    modal.style.display = "flex";
    loadProductsToModal(); // 상품 불러오기
    loadCartItems();
  }
}

function closeAdminsellcounter() {
  const modal = document.getElementById("adminsellcounter");
  if (modal) {
    modal.style.display = "none";
  }
}

// 상품 목록 불러오기
function loadProductsToModal() {
  fetch("/admin/products/json")
    .then(res => res.json())
    .then(productList => {
      const tbody = document.getElementById("productTableBody");
      tbody.innerHTML = ""; // 초기화

      productList.forEach(product => {
        const row = `
          <tr data-product-no="${product.no}">
            <td>${product.no}</td>
            <td>${product.categoryName}</td>
            <td>${product.pName}</td>
            <td>${product.stock}개</td>
            <td>${product.pPrice.toLocaleString()}원</td>
            <td>
                <button class="cart-add-btn" data-pno="${product.no}">
                    <img src="/images/회색 플러스.png" alt="담기" />
                </button>
            </td>
          </tr>
        `;
        tbody.insertAdjacentHTML("beforeend", row);
      });
    })
    .catch(err => console.error("상품 목록 로딩 실패:", err));
}

// ----------------------
document.addEventListener("DOMContentLoaded", () => {
  const openBtn = document.getElementById("openAdminSellCounterModalBtn");
  if (openBtn) {
    openBtn.addEventListener("click", (e) => {
      e.preventDefault();
      openAdminsellcounter(); // 모달 열기 + 상품 불러오기
    });
  }


document.addEventListener("click", (e) => {
  const btn = e.target.closest(".cart-add-btn");
  if (btn) {
    const pno = btn.dataset.pno;

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    // 👉 fetch로 장바구니 추가 요청
    fetch("/admin/sellcounter/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        [csrfHeader]: csrfToken, // 💡 csrf 처리 필수
      },
      body: new URLSearchParams({
        pNo: pno
      })
    })
      .then(res => {
        if (res.ok) {
          loadCartItems();
        } else {
          alert("❌ 장바구니 추가 실패");
        }
      })
      .catch(err => {
        console.error("장바구니 담기 에러:", err);
      });
  }
});
});
function loadCartItems() {
  console.log("🛒 loadCartItems() 실행됨!");
  fetch("/admin/orders/cart/json")
    .then(res => res.json())
    .then(cartList => {
      const cartContainer = document.querySelector(".sell-cart-items");
      const totalPriceElem = document.querySelector(".total-price span");
      cartContainer.innerHTML = "";

      let totalPrice = 0;

      // ✅ 장바구니가 비었으면 안내 문구 출력
      if (!cartList || cartList.length === 0) {
        cartContainer.innerHTML = `
          <div class="empty-cart-message" style="padding: 20px; text-align: center; color: #999;">
            🛒 장바구니가 비어 있습니다.
          </div>
        `;
        totalPriceElem.textContent = "0원";
        return;
      }

      // ✅ 장바구니가 있을 경우 렌더링
      cartList.forEach(cart => {
        const itemHTML = `
        <div class="sell-cart-item">
          <input type="hidden" name="pNo" value="${cart.p_no}" />
          <input type="hidden" name="pName" value="${cart.p_name}">
          <input type="hidden" name="quantity" value="${cart.quantity}" />
          <input type="hidden" name="stock" value="${cart.stock}" />
          <div class="cart-item-left">
            <div>${cart.p_name}</div>
            <div class="quantity-control">
              <form action="/admin/sellcounter/decrease" method="post">
                <input type="hidden" name="pNo" value="${cart.p_no}" />
                <button type="submit" class="sellcontrolBtn">
                  <img src="/images/마이너스 하얀색.png" alt="감소">
                </button>
              </form>
              <span>${cart.quantity}</span>
              <form action="/admin/sellcounter/increase" method="post">
                <input type="hidden" name="pNo" value="${cart.p_no}" />
                <button type="submit" class="sellcontrolBtn">
                  <img src="/images/플러스 노란색.png" alt="증가">
                </button>
              </form>
            </div>
          </div>
          <div class="cart-item-right">
            <div>${(cart.p_price * cart.quantity).toLocaleString()}원</div>
            <form action="/admin/sellcounter/delete" method="post">
              <input type="hidden" name="cNo" value="${cart.no}" />
              <button class="selldeleteBtn">✕</button>
            </form>
          </div>
        </div>
        `;
        totalPrice += cart.p_price * cart.quantity;
        cartContainer.insertAdjacentHTML("beforeend", itemHTML);
      });

      totalPriceElem.textContent = totalPrice.toLocaleString() + "원";
    })
    .catch(err => {
      console.error("❌ 장바구니 로딩 에러:", err);
    });
}


document.getElementById("submitOrderBtn").addEventListener("click", async () => {
  const seatId = document.getElementById("seatIdInput").value;
  if (!seatId || seatId.trim() === "") {
    alert("좌석 ID를 입력해주세요!");
    return;
  }

  const paymentMethod = document.querySelector("input[name='payment']:checked");
  if (!paymentMethod) {
    alert("결제 수단을 선택해주세요!");
    return;
  }

  const cartContainer = document.querySelector(".sell-cart-items");
  const cartItems = cartContainer.querySelectorAll(".sell-cart-item");

  if (cartItems.length === 0) {
    alert("장바구니가 비어 있습니다!");
    return;
  }

  const pNoList = [];
  const quantityList = [];
  const pNameList = [];
  const stockList = [];

  cartItems.forEach(item => {
    const pNo = item.querySelector("input[name='pNo']").value;
    const quantity = item.querySelector("input[name='quantity']").value;
    const pName = item.querySelector("input[name='pName']").value;
    const stock = item.querySelector("input[name='stock']").value;

    pNoList.push(parseInt(pNo));
    quantityList.push(parseInt(quantity));
    pNameList.push(pName);
    stockList.push(parseInt(stock));
  });

  const totalPrice = document.querySelector(".total-price span").textContent
    .replace("원", "")
    .replace(/,/g, "");

  // CSRF 처리
  const csrfToken = document.querySelector('meta[name="_csrf"]').content;
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

  const formData = new FormData();
  formData.append("seatId", seatId);
  formData.append("totalPrice", totalPrice);
  formData.append("payment", paymentMethod.value);
  
  pNoList.forEach(v => formData.append("pNoList", v));
  quantityList.forEach(v => formData.append("quantityList", v));
  pNameList.forEach(v => formData.append("pNameList", v));
  stockList.forEach(v => formData.append("stockList", v));

  // CSRF도 formData에 포함
  formData.append(csrfHeader, csrfToken);

  console.log("🧾 FormData 전송 데이터:");
  for (let pair of formData.entries()) {
    console.log(`${pair[0]} = ${pair[1]}`);
  }

  try {
    const res = await fetch("/admin/sellcounter/create", {
      method: "POST",
      credentials: "same-origin", // 💡 세션 쿠키 포함
      body: formData
    });

    if (res.ok) {
      alert("✅ 주문이 완료되었습니다!");
      location.reload(); // 또는 cart 다시 불러오기
    } else {
      alert("❌ 주문 실패");
    }
  } catch (e) {
    console.error("주문 오류:", e);
    alert("서버 오류 발생");
  }
});


// 수량 변경, 삭제
document.body.addEventListener("click", async (e) => {
  const btn = e.target.closest(".sellcontrolBtn");
  const delBtn = e.target.closest(".selldeleteBtn");

  // CSRF 처리
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  // ✅ 수량 증가 / 감소
  if (btn) {
    e.preventDefault();
    const form = btn.closest("form");
    const formData = new FormData(form);

    try {
      const res = await fetch(form.action, {
        method: "POST",
        headers: {
          [csrfHeader]: csrfToken
        },
        body: formData
      });

      if (res.ok) {
        console.log("✅ 수량 조정 성공!");
        loadCartItems(); // 장바구니만 다시 그려줘!
      } else {
        alert("❌ 수량 조정 실패");
      }
    } catch (err) {
      console.error("❌ 수량 조정 중 오류", err);
      alert("서버 오류 발생!");
    }
  }

  // ❌ 삭제 버튼
  if (delBtn) {
    e.preventDefault();
    const form = delBtn.closest("form");
    const formData = new FormData(form);

    try {
      const res = await fetch(form.action, {
        method: "POST",
        headers: {
          [csrfHeader]: csrfToken
        },
        body: formData
      });

      if (res.ok) {
        console.log("🗑️ 삭제 성공!");
        loadCartItems(); // 삭제 후 장바구니 다시 불러오기
      } else {
        alert("❌ 삭제 실패");
      }
    } catch (err) {
      console.error("❌ 삭제 요청 오류", err);
      alert("서버 오류 발생!");
    }
  }
});
