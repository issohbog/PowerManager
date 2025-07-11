document.addEventListener("DOMContentLoaded", () => {
  const cards = document.querySelectorAll(".plan-card");
  const ticketItem = document.querySelector(".ticket-item");

  cards.forEach(card => {
    card.addEventListener("click", () => {
      const name = card.dataset.name;
      const price = parseInt(card.dataset.price).toLocaleString();
      const time = card.dataset.time;

      const itemHTML = `
        <div class="selected-ticket">
            <div class="select-ticket-name">${name}</div>
            <div class="select-ticket-info">
                <span>${price}원(${time}분)</span>
                <span>
            </div>
        </div>
      `;

      // 기존 항목 초기화하고 하나만 보여줄 경우:
      ticketItem.innerHTML = itemHTML;

      // 여러 개 누적하고 싶으면 대신 아래 사용:
      // ticketItem.insertAdjacentHTML("beforeend", itemHTML);
    });
  });
});