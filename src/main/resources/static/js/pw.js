function reg(action){


        var pw = document.getElementById("password1")
        var rw = document.getElementById("password2");
        var form = document.getElementById("form");


        if(pw.value==""){
            alert("패스워드를 입력하세요");
            pw.focus();
        }else if(rw.value==""){
            alert("패스워드 확인란에 입력하세요");
            rw.focus();
        }else if(pw.value != rw.value){
            alert("패스워드와 패스워드 확인란이 다릅니다");
            pw.value = "";
            rw.value ="";
        }else{
        $("#form").attr("action", action);
        form.submit();
        }
        }



             $(".form5")
                .find("input, textarea")
                .on("keyup blur focus", function (e) {
                  var $this = $(this),
                    label = $this.prev("label");

                  if (e.type === "keyup") {
                    if ($this.val() === "") {
                      label.removeClass("active highlight");
                    } else {
                      label.addClass("active highlight");
                    }
                  } else if (e.type === "blur") {
                    if ($this.val() === "") {
                      label.removeClass("active highlight");
                    } else {
                      label.removeClass("highlight");
                    }
                  } else if (e.type === "focus") {
                    if ($this.val() === "") {
                      label.removeClass("highlight");
                    } else if ($this.val() !== "") {
                      label.addClass("highlight");
                    }
                  }
                });

              $(".tab a").on("click", function (e) {
                e.preventDefault();

                $(this).parent().addClass("active");
                $(this).parent().siblings().removeClass("active");

                target = $(this).attr("href");

                $(".tab-content > div").not(target).hide();

                $(target).fadeIn(600);
              });