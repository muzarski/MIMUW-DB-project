
$(document).ready(function() {
    $("#gameHistory").DataTable({
        'aoColumnDefs': [{
            'bSortable': false,
            'aTargets': [-1, -2] /* 1st one, start by the right */
        }]
    });
})

function showAll(match_id) {
    console.log(match_id)
    let rows = $(".perf-row" + match_id);
    rows.show();
    rows.removeClass("hidden");
    $(".but-show" + match_id).hide();
    let but_hide = $(".but-hide" + match_id);
    but_hide.show();
    but_hide.removeClass("hidden");
}

function hideRest(match_id) {
    let rows = $(".perf-row" + match_id);
    rows.hide();
    $(".but-show" + match_id).show();
    $(".but-hide" + match_id).hide();
}

document
    .getElementById('rank-select')
    .addEventListener('change', function () {
        let vis = document.querySelector('.vis');
        let target = document.getElementById(this.value);
        
        if (vis !== null) {
            vis.classList.remove('vis');
            vis.classList.add('hidden');
        }
        
        if (target !== null) {
            target.classList.remove('hidden');
            target.classList.add('vis');
        }
    })

