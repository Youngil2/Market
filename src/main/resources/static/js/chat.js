// collapsible 클래스를 가진 모든 요소를 찾아 클릭 이벤트를 추가.

// firstBotMessage 호출 후 초기 메시지를 설정.

// getHardResponse 챗봇의 응답을 가져오기.

// getResponse 인풋의 필드 내용값을 처리 한 뒤 getHardResponse 1초 후 호출하여 답변을 가져온다.

// buttonSendText 함수를 사용하여 버튼을 클릭 시 사용자 입력을 처리.

// getBotResponse 함수를 사용하여 사용자 입력에 대한 챗봇 응답을 반환.
var coll="";


window.onload = function(){
coll = document.getElementsByClassName("collapsible");

for (let i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function () {
    this.classList.toggle("active");

    var content = this.nextElementSibling;

    if (content.style.maxHeight) {
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    }
  });
}

// 엔터를 눌러도 보내지는 코드
$("#textInput").keypress(function (e) {
  if (e.which == 13) {
    getResponse();
  }
});

firstBotMessage();

}






function getTime() {
  let today = new Date();
  hours = today.getHours();
  minutes = today.getMinutes();

  if (hours < 10) {
    hours = "0" + hours;
  }

  if (minutes < 10) {
    minutes = "0" + minutes;
  }

  let time = hours + ":" + minutes;
  return time;
}

// 맨 처음 채팅을 시작하면 뜨게 할 CHAT
function firstBotMessage() {
  let firstMessage = "가위바위보 게임밖에 없습니다.";
  document.getElementById("botStarterMessage").innerHTML =
    '<p class="botText"><span>' + firstMessage + "</span></p>";

  let time = getTime();

  $("#chat-timestamp").append(time);
  document.getElementById("userInput").scrollIntoView(false);
}



// 인풋 필드를 검색
function getHardResponse(userText) {
  let botResponse = getBotResponse(userText);
  let botHtml = '<p class="botText"><span>' + botResponse + "</span></p>";
  $("#chatbox").append(botHtml);

  document.getElementById("chat-bar-bottom").scrollIntoView(true);
}

// 입력 인풋에서 텍스틀 가져와 처리
function getResponse() {
  let userText = $("#textInput").val();

  if (userText == "") {
    userText = "아무것도 입력되지 않았습니다.";
  }

  let userHtml = '<p class="userText"><span>' + userText + "</span></p>";

  $("#textInput").val("");
  $("#chatbox").append(userHtml);
  document.getElementById("chat-bar-bottom").scrollIntoView(true);

  setTimeout(() => {
    getHardResponse(userText);
  }, 1000);
}

// 보내기 버튼을 클릭 했을 시
function buttonSendText(sampleText) {
  let userHtml = '<p class="userText"><span>' + sampleText + "</span></p>";

  $("#textInput").val("");
  $("#chatbox").append(userHtml);
  document.getElementById("chat-bar-bottom").scrollIntoView(true);
}

function sendButton() {
  getResponse();
}

function heartButton() {
  buttonSendText("❤️");
}



// 가위바위보 게임
function getBotResponse(input) {
  // Simple responses
  if (input == "ㅎㅇ") {
    return "안녕 ~ !!";
  } else if (input == "ㅂㅇ") {
    return "잘가 ~ !!";
  } else {
    return "다른것을 물어보세요 !!";
  }
}

function getBotResponse(input) {

  if (input == "운송거래는 사기가 자주있나요?") {
    return "그런 걱정 하실필요 없습니다^^";
  } else if (input == "이 사이트 신뢰성있는 사이트인가요?") {
    return "당근마켓,중고나라 보다도 신뢰성있는 사이트입니다^^";
  } else if (input == "카드도되나요?") {
    return "판매자에따라 다르답니다^^";
  } else if (input == "배송은 어디 택배사인가요?") {
    return "보통은 로젠,CJ,우체국 으로 간답니다^^";
  } else if (input == "배송중 훼손되면 어떻게하죠?") {
    return "그건 택배사에물어보세요;;";
  } else if (input == "뭐하고싶어?") {
    return "너랑놀고싶어 ~ !!";
  } else if (input == "ㅂㅇ") {
    return "잘가 ~ !!";
  } else if (input == "정영준") {
    return "고자식";
  } else if (input == "김시호") {
    return "비둘기아빠";
  } else if (input == "임현석") {
    return "행사장풍선";
  } else if (input == "김영일") {
    return "공공공콩콩콩땅콩";
  } else if (input == "조경호") {
    return "여봉";
  }

}