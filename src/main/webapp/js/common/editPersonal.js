$(function () {
    $.post(
        "/user/findUser?uid="+$("#user").attr("userid"),
        function (result) {
            var user=result.hashmap.user;
            $("#edi_Personal_Model").find("#username").val(user.username);
            $("#edi_Personal_Model").find("#phone").val(user.phone);
            $("#edi_Personal_Model").find("#bind_email").val(user.bind_email);
            $("#edi_Personal_Model").find("#address").val(user.address);
            var options=$("#edi_Personal_Model").find("#sex").find("option");
            options.each(function () {
                if($(this).text()==user.sex){
                    $(this).attr("selected","selected");
                    return false;
                }
            });
            $("#edi_Personal_Model").find("#dsqId").val(user.dsqId);
        }
    )
})