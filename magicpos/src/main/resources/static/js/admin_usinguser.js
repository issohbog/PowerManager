document.addEventListener("DOMContentLoaded", () => {
  const modal = document.getElementById("userModal");
  const closeBtn = document.querySelector(".close");
  const openBtn = document.getElementById("showUsersBtn");
  const tableBody = document.getElementById("userTableBody");

  openBtn.addEventListener("click", () => {
    fetch("/admin/seats/inuse")
      .then(res => res.json())
      .then(data => {
        tableBody.innerHTML = ""; // 초기화
        data.forEach(user => {
          const row = document.createElement("tr");
          row.innerHTML = `
            <td>${user.username}(${user.userId})</td>
            <td>${user.remainTime}분</td>
          `;
          tableBody.appendChild(row);
        });
        modal.style.display = "block";
      })
      .catch(err => console.error("회원 목록 불러오기 실패:", err));
  });

  closeBtn.addEventListener("click", () => {
    modal.style.display = "none";
  });

  window.addEventListener("click", (e) => {
    if (e.target === modal) {
      modal.style.display = "none";
    }
  });
});
