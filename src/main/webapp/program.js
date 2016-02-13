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

($(document).on("ready",function() {
    $('#hand-page-seat').on('pageinit', function() {
           $('input[type="radio"]').checkboxradio();
    });
}));


($(document).on("ready",function() {
    $('#auctionForm').trigger("create");
}));


($(document).on("ready", function() {

    $(document).on("change", "#contract_level", function()
    {
        $('#contract_result').html("MADE");
        $('#contract_result_by').html("");
    });


}));

/* Updates description of hand result;  MADE | SET */
($(document).on("ready", function() {
$(document).on("change", "#real_tricksid", function()
{
    var tricks = parseInt($('#real_tricksid').val());
    var level = parseInt($('#real_levelid').val());

    var cmp = tricks - (level + 6);

    var adverb = "";
    if(cmp < 0)
    {
        adverb = "Down ";
        $('#contract_result').html("SET");

        cmp = Math.abs(cmp);
    } else {
        adverb = "+";
        $('#contract_result').html("MADE");

    }

    if(cmp != 0)
        $('#contract_result_by').html(adverb + cmp);
    else
        $('#contract_result_by').html("");

});
}));


function print_form_values()
{
    console.log("Form values before submitting:");
    console.log($('#real_seatid').val());
    console.log($('#real_suitid').val());
    console.log($('#real_penaltyid').val());
    console.log($('#real_levelid').val());
    console.log($('#real_tricksid').val());
}


/* registers events for multi-page hand form */
($(document).on("ready", function () {

         
     $('#hand-page-seat-submit').click(function() {
     		console.log("seat submit clicked");
            $('#form-hand-seat').submit();
     });
     
     $('#hand-page-suit-submit').click(function() {
            $('#form-hand-suit').submit();
     });
     
     $('#hand-page-level-submit').click(function() {
            $('#form-hand-level').submit();
     });
     
     $('#hand-page-penalty-submit').click(function() {
            $('#form-hand-penalty').submit();
     });

     $('#hand-page-final-submit').click(function() {
            print_form_values();
            $('#auctionForm').submit();
     });
     
     
 }));



function test_fill()
{
    $("input[type='radio']").attr("checked",false).checkboxradio("refresh"); 
    $("input[type='radio']:last").attr("checked",true).checkboxradio("refresh");
    //$("input[type='radio']").checkboxradio("refresh");

    $('#real_levelid').val(4).slider("refresh");
    $('#real_tricksid').val(10).slider("refresh");
}


function hand_seat_submit()
{
    var selected = $('#form-hand-seat input[type="radio"]:checked');
    console.log(selected[0]);
    
    if(typeof selected[0] !== "undefined")
    { 
        var idx = -1;
        switch(selected.attr('value'))
        {
            case 'SOUTH':
                idx = 0;
                break;
            case 'WEST':
                idx = 1;
                break;            
            case 'NORTH':
                idx = 2;
                break;
            case 'EAST':
                idx = 3;
                break;
        }
        
        if(idx < 0)
            return false;
                     
        $('#real_seatid-' + idx).attr("checked",true).checkboxradio("refresh");
        
        $.mobile.pageContainer.pagecontainer("change", "#hand-page-suit", {transition:"slide"});
    }
    return false;
}

function hand_suit_submit()
{
    var selected = $('#form-hand-suit input[type="radio"]:checked');
    console.log(selected[0]);
    
    if(typeof selected[0] !== "undefined")
    { 
        var idx = -1;
        switch(selected.attr('value'))
        {
            case 'CLUBS':
                idx = 0;
                break;
            case 'DIAMONDS':
                idx = 1;
                break;            
            case 'HEARTS':
                idx = 2;
                break;
            case 'SPADES':
                idx = 3;
                break;
            case 'NOTRUMP':
                idx = 4;
                break;
        }
        
        if(idx < 0)
            return false;
                     
        $('#real_suitid-' + idx).attr("checked",true).checkboxradio("refresh");
    
        $.mobile.pageContainer.pagecontainer("change", "#hand-page-level", {transition:"slide"});
    }
    return false;
}

function hand_level_submit()
{
    var selected = $('#form-hand-level input[type="number"]').val();
    console.log(selected);

    var num = parseInt(selected);

    $('#real_levelid').val(num).slider("refresh");
    $('#real_tricksid').val(num + 6).slider("refresh");
    
    
    $.mobile.pageContainer.pagecontainer("change", "#hand-page-penalty", {transition:"slide"});
    return false;
}

function hand_penalty_submit()
{
    var selected = $('#form-hand-penalty input[type="radio"]:checked');
    console.log(selected[0]);
    
    if(typeof selected[0] !== "undefined")
    { 
    
        var idx = -1;
        switch(selected.attr('value'))
        {
            case 'UNDOUBLED':
                idx = 0;
                break;
            case 'DOUBLED':
                idx = 1;
                break;            
            case 'REDOUBLED':
                idx = 2;
                break;
        }
        
        if(idx < 0)
            return false;
            
        $('#real_penaltyid-' + idx).attr("checked",true).checkboxradio("refresh");
            
        $.mobile.pageContainer.pagecontainer("change", "#hand-page-final", {transition:"slide"});
    }
    return false;
}

