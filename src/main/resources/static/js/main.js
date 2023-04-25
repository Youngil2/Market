// 인기매물 더보기 클릭 시 상품추가
  const moreBtn = document.querySelector(".home-popular__more"); // 인기매물 더보기 버튼
  const popularList = document.querySelector(".popular-list"); // 인기매물 리스트
  const popularListItems = document.querySelectorAll(".home-popular__list"); // 인기매물 리스트 아이템

  moreBtn.addEventListener("click", function(){
    // 버튼 클릭 시
    popularListItems.forEach((item) => {
      item.style.display = "block"; // 보이게 함.
    });
  });

  // 탑 버튼
  var topBtn = document.querySelector(".topBtn");
  topBtn.style.visibility = "hidden"; // topBtn의 visibility >  hidden으로 .
  window.addEventListener("scroll", function () {
    // 스크롤이 발생하면 실행되는 함수.
    if (window.scrollY > 0) {
      // 스크롤 높이가 0보다 크면 ,
      topBtn.style.visibility = "visible"; // topBtn의 visibility > visible.
      topBtn.style.pointerEvents = "auto"; // topBtn의 포인트 이벤트 > 자동.
      topBtn.style.opacity = "1"; // topBtn의 opacity를 1로.
    } else {
      // 스크롤 높이가 0보다 작으면 ,
      topBtn.style.visibility = "hidden"; // topBtn의 visibility를 hidden으로 변경.
      topBtn.style.pointerEvents = "none"; // topBtn의 pointerEvents를 none으로 변경.
      topBtn.style.opacity = "0"; // topBtn의 opacity를 0으로 변경.
    }
  });

  // 이미지 애니메이션 추가 한 것
  window.addEventListener("scroll", function () {
    // action 1 ~ 4 에 항목 가져오기.
    var action1 = document.getElementById("action1");
    var action2 = document.getElementById("action2");
    var action3 = document.getElementById("action3");
    var action4 = document.getElementById("action4");

    // 항목의 좌표 가져오기.
    var rect1 = action1.getBoundingClientRect();
    var rect2 = action2.getBoundingClientRect();
    var rect3 = action3.getBoundingClientRect();
    var rect4 = action4.getBoundingClientRect();

    // 항목이 viewport 안에 보이는지 여부를 판단
    // 요소의 높이가 윈도우 높이보다 작고, 바닥이 0보다 큰지 ?
    // 요소가 화면내에 보이는지 확인하기 위한 코드
    var inView1 = rect1.top < window.innerHeight && rect1.bottom >= 0;
    var inView2 = rect2.top < window.innerHeight && rect2.bottom >= 0;
    var inView3 = rect3.top < window.innerHeight && rect3.bottom >= 0;
    var inView4 = rect4.top < window.innerHeight && rect4.bottom >= 0;

    //항목이 viewport 안에 보이면 move-left 라는 클래스를 추가
    if (inView1) {
      action1.classList.add("move-left");
    }
    if (inView2) {
      action2.classList.add("move-left");
    }
    if (inView3) {
      action3.classList.add("move-left");
    }
    if (inView4) {
      action4.classList.add("move-left");
    }
  });
  window.addEventListener("scroll", handleScroll);