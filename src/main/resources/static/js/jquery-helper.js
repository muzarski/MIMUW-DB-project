$(document).ready(function() {
    $(".dropdown").click(function () {
        $(this).find(".dropdown-menu").slideToggle("fast");
    });
});

$(document).on("click", function(event){
    let $trigger = $(".dropdown");
    if($trigger !== event.target && !$trigger.has(event.target).length){
        $(".dropdown-menu").slideUp("fast");
    }
});