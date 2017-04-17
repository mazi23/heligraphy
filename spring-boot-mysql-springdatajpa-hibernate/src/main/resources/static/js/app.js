// WOW Initialization
wow = new WOW(
    {
        animateClass: 'animated',
        offset:       100,
        mobile: false
    }
);
wow.init();

// Page scroller.
$.scrollUp({
    scrollName: 'page_scroller',
    scrollDistance: 300,
    scrollFrom: 'top',
    scrollSpeed: 500,
    easingType: 'linear',
    animation: 'fade',
    animationSpeed: 200,
    scrollTrigger: false,
    scrollTarget: false,
    scrollText: '<i class="fa fa-chevron-up"></i>',
    scrollTitle: false,
    scrollImg: false,
    activeOverlay: false,
    zIndex: 2147483647
});

// Counter
function isCounterElementVisible($elementToBeChecked)
{
    var TopView = $(window).scrollTop();
    var BotView = TopView + $(window).height();
    var TopElement = $elementToBeChecked.offset().top;
    var BotElement = TopElement + $elementToBeChecked.height();
    return ((BotElement <= BotView) && (TopElement >= TopView));
}

$(window).scroll(function () {
    $( ".counter" ).each(function() {
        isOnView = isCounterElementVisible($(this));
        if(isOnView && !$(this).hasClass('Starting')){
            $(this).addClass('Starting');
            $(this).prop('Counter',0).animate({
                Counter: $(this).text()
            }, {
                duration: 3000,
                easing: 'swing',
                step: function (now) {
                    $(this).text(Math.ceil(now));
                }
            });
        }
    });
});


(function(){
    $('#ourPartners').carousel({ interval: 3600 });
}());

(function(){
    $('.our-partners .item').each(function(){
        var itemToClone = $(this);
        for (var i=1;i<4;i++) {
            itemToClone = itemToClone.next();
            if (!itemToClone.length) {
                itemToClone = $(this).siblings(':first');
            }
            itemToClone.children(':first-child').clone()
                .addClass("cloneditem-"+(i))
                .appendTo($(this));
        }
    });
}());

var carVideoWidth = $('.modal-dialog').width();
if(carVideoWidth < 1)
{
    var carVideoWidth = $('.modal').width();
}
$('iframe').css('height', (carVideoWidth * .61));

var videoWidth = $('.car-details').width();
videoHeight = videoWidth * .61;
$('.car-details iframe').css('height', videoHeight);

var SingleRoomSliderdWidth = $('.simple-slider').width();
var SingleRoomSliderdHeight = SingleRoomSliderdWidth * .74;
$('.thumb-preview').css('height', SingleRoomSliderdHeight);
$('.imng-preview').css('height', SingleRoomSliderdHeight);


$('.selectpicker').selectpicker();

val =  $('.price-slider').slider('getValue');
$('.price-slider').on('slide', function (ev) {
    $('#minPrice').val(ev.value[0]);
    $('#maxPrice').val(ev.value[1]);
});


function toggleChevron(e) {
    $(e.target)
        .prev('.panel-heading')
        .find(".fa")
        .toggleClass('fa-minus fa-plus');
}
$('.panel-group').on('shown.bs.collapse', toggleChevron);
$('.panel-group').on('hidden.bs.collapse', toggleChevron);

var windowHeight = $( window ).height();
$('#map').css('height', windowHeight);

// Change color plate
$('.color-plate').on('click', function () {
    var name = $(this).attr('data-color');
    $('link[id="style_sheet"]').attr('href', 'css/colors/'+name+'.css');
    if(name == 'default'){
        $('.logo img').attr('src', 'img/logos/logo.png');
    }
    else{
        $('.logo img').attr('src', 'img/logos/'+name+'-logo.png');
    }
});

$('.setting-button').on('click', function () {
    $('.option-panel').toggleClass('option-panel-collased');
});