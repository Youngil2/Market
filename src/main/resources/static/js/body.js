 $(document).ready( function() {
        var jbOffset = $(".menu-list" ).offset();
        $(window).scroll(function() {
          if ( $( document ).scrollTop() > jbOffset.top ) {
            $( ".menu-list" ).addClass( "jbFixed");
          }
          else {
            $( ".menu-list" ).removeClass( "jbFixed" );
          }
        });
      } );