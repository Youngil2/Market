//const commentWrite = () => {
//    const writer = document.getElementById("commentWriter").value;
//    const contents = document.getElementById("commentContents").value;
//    console.log("작성자 : ", writer);
//    console.log("내용 : ", contents);
//    const id = [[$({salesBulletinBoardFormDto.id)]];
//    $.ajax({
//        //요청방식 : post , 요청주소: /comment/save, 요청데이터 : 작성자, 장석내용, 게시글번호
//        type:"post",
//        url:"/comment/save",
//        date:{
//            "commentWriter" : writer,
//            "commentContents": contents,
//            "boardId" : id
//        }
//        success: function(res){
//            console.log("요청 성공", res);
//
//        },
//        error: function(err){
//            console.log("요청 실패", err);
//        }
//
//    });
//}