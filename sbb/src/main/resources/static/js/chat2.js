document.addEventListener("DOMContentLoaded", function() { //이건 DOM오류 방지를 위해 문서가 로드가 다 될때까지 기다렸다가 코드를 실행하라는 소리


let username=prompt("내담자 성함을 입력해 주세요");
let roomNum = prompt("상담자 식별번호를 입력해 주세요");
//로그인 시스템 연동 전 임시조치.
// let 변수명 =prompt("멘트"), 사용자 입력 값을 prompt에 저장했다가 변수명에 넣어주는역할

document.querySelector("#username").innerHTML=username;
//html안에 id='username'을 = 변수 username으로 받는다는거임.
//document.quertSelector("#변수명") html에서 변수명(id)를 선택하는거
//.innerHTML 위에서 선택한 요소의 콘텐츠를 변경하거나 읽을 수 있도록 해주는녀석.
//채팅방 상단에 유저 이름이 뜨는 칸을 html에서 비워놨음, 그 후 js로 실행하는거

//const eventSource=new EventSource("http://localhost:8080/sender/성현/receiver/우성"); 이건 귓속말 기능에 쓸거임.
const eventSource=new EventSource(`http://localhost:8080/chat2/roomNum/${roomNum}`); //`` 백틱. 이부분은 SSE연결하는 부분
//EventSource 객체는 이벤트 스트림을 설정하게 해주는데, 간략하게 말하면 SSE라고 한다

eventSource.onmessage=(event)=>{
    const data = JSON.parse(event.data);
    if(data.sender===username){ //로그인한 유저가 보낸 메시지(파란박스로 만들예정)
    initMyMessage(data); //파란박스 만들기/
    }else{
        initYourMessage(data); // 회색박스 만들기
    }
}
//onmessage 이벤트 핸들러는 새로운 메시지를 수신할 때 실행되며, 다른 이벤트 핸들러는 연결 상태 변경, 에러 처리 등 다른 이벤트에 대한 동작을 정의합니다.
//이벤트 핸들러는 특정 동작이 들어왔을떄 실행 되는 함수!!임.
function getSendMsgBox(data){

    let md = data.createdAt.substring(5,10)
    let tm = data.createdAt.substring(11,16)
    convertTime = tm + "|" + md

    return ` <div class="sent_msg">
    <p>${data.msg}</p>
    <span class="time_date"> ${convertTime}/${data.sender}</span> 
  </div>`;
}
//리턴안에 문장들은 백틱으로 감싸야하고
//p태그 안에 ${} 이걸로 변수를 받을수있음.

function getReceiveMsgBox(data){
    
    let md = data.createdAt.substring(5,10)
    let tm = data.createdAt.substring(11,16)
    convertTime = tm + "|" + md
    return ` <div class="received_withd_msg">
    <p>${data.msg}</p>
    <span class="time_date"> ${convertTime}/${data.sender}</span> 
  </div>`;
}

//파란박스 초기화, 이벤트가 걸려있어서 데이터가 들어올때마다 초기화
function initMyMessage(data){ //이부분은 몽고디비에 있는 대화내용을 웹페이지로 가져오기 위함임.
    let chatBox = document.querySelector("#chat-box");
    let sendBox = document.createElement("div");

    sendBox.className="outgoing_msg";

    sendBox.innerHTML=getSendMsgBox(data); 
    chatBox.append(sendBox);
}

//회색박스 초기화 하기
function initYourMessage(data){ //이부분은 몽고디비에 있는 대화내용을 웹페이지로 가져오기 위함임.
    let chatBox = document.querySelector("#chat-box");
    
    let receivedBox = document.createElement("div");
    receivedBox.className="incomming_msg"; // 받는 메시지니까 html에서 received_msg라는 class따온거
//receivedBox.className="received_msg";
    receivedBox.innerHTML=getReceiveMsgBox(data); 
    chatBox.append(receivedBox);
}

//AJAX로 채팅 메시지를 전송
async function addMessage(){ //이부분은 몽고디비에 있는 대화내용을 웹페이지로 가져오기 위함임.

    let msginput=document.querySelector("#chat-outgoing-msg"); 
/*let chat ={
    sender:"성현",
    receiver:"우성",
    msg: msginput.value
}; 귓속말 할때 만들거임.*/
let chat ={
    sender: username, //여기에 ""잘못 붙혀서 코드가 실행이 안됫음.
    roomNum: roomNum,
    msg: msginput.value
};

fetch("/chat2",{
    method:"post",//내가 지금 너한테 데이터를 보낼테니까 너는 데이터베이스에 데이터를 저장해
    body:JSON.stringify(chat), //자바스크립트 객체를 서버에 직접 전달이 안되서 JSON형태 타입으로 변환하라는뜻
    headers:{
        "Content-Type":"application/json; charset=utf-8"
    }
});

    msginput.value=""; //채팅을 치고 나서 입력칸을 공벡으로 만들어줌    

}


//버튼 클릭시 메시지 전송
document.querySelector("#chat-outgoing-button").addEventListener("click", () => {
    addMessage();
});

//엔터를 치면 메시지 전송
document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e) => {
    
    if (e.keyCode === 13) {
        addMessage();
    } 
});

});