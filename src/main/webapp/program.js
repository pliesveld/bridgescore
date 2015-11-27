
/*
To update a jquery mobile slider ui

$("input[type='range']").val(60).slider("refresh");

To update a jquery mobile select ui

var myselect = $("#selectfoo");
myselect[0].selectedIndex = 3;
myselect.selectmenu("refresh");


To update a jquery mobile radio ui

$("input[type='radio']").prop("checked",true).checkboxradio("refresh");
*/

/*
    Updates lower slider when the contract level has been input by the user.
*/

($(document).on("ready", function() {

    $(document).on("change", "#contract_level", function()
    {
        var newv = parseInt($(this).val()) + 6;
        var slider = $('#tricks_taken').val(newv);
        if(slider["slider"])
            slider.slider("refresh");

        $('span#contract_result').html("MADE");
        $('span#contract_result_by').html("");
    });


}));

/* Updates description of hand result;  MADE | SET */
($(document).on("ready", function() {
$(document).on("change", "#tricks_taken", function()
{
    var tricks = parseInt($('#tricks_taken').val());
    var level = parseInt($('#contract_level').val());

    var cmp = tricks - (level + 6);

    var adverb = "";
    if(cmp < 0)
    {
        adverb = "Down ";
        $('span#contract_result').html("SET");

        cmp = Math.abs(cmp);
    } else {
        adverb = "+";
        $('span#contract_result').html("MADE");

    }

    if(cmp != 0)
        $('span#contract_result_by').html(adverb + cmp);
    else
        $('span#contract_result_by').html("");

});
}));


/* header button submits auctionForm */
($(document).on("ready", function () {
     $('#page2-submit').click(function() {
            $('#auctionForm').submit();
     });
 }));


