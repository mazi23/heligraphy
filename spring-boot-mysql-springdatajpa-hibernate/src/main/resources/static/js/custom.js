/**
 * Created by mazi on 16.04.17.
 */
function LoadMap(propertes) {
    var defaultLat = 48.206367;
    var defaultLng = 13.817067;
    var mapOptions = {
        center: new google.maps.LatLng(defaultLat, defaultLng),
        zoom: 15,
        scrollwheel: false,
        styles: [
            {
                featureType: "administrative",
                elementType: "labels",
                stylers: [
                    {visibility: "off"}
                ]
            },
            {
                featureType: "water",
                elementType: "labels",
                stylers: [
                    {visibility: "off"}
                ]
            },
            {
                featureType: 'poi.business',
                stylers: [{visibility: 'off'}]
            },
            {
                featureType: 'transit',
                elementType: 'labels.icon',
                stylers: [{visibility: 'off'}]
            },
        ]
    };
    var map = new google.maps.Map(document.getElementById("map"), mapOptions);
    var infoWindow = new google.maps.InfoWindow();
    var myLatlng = new google.maps.LatLng(48.206367, 13.817067);

    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map
    });
    (function (marker) {
        google.maps.event.addListener(marker, "click", function (e) {
            infoWindow.setContent("" +
                "<div class='map-properties contact-map-content'>" +
                "<div class='map-content'>" +
                "<p class='address'>HeliGraphy</p>" +
                "<ul class='map-properties-list'> " +
                "<li><i class='fa fa-phone'></i>  +43 676 4677 516</li> " +
                "<li><i class='fa fa-envelope'></i>  info@heligraphy.at</li> " +
                "<li><a href='index.html'><i class='fa fa-globe'></i>  http://www.heligraphy.at</li>" +
                "</a> " +
                "</ul>" +
                "</div>" +
                "</div>");
            infoWindow.open(map, marker);
        });
    })(marker);
}
LoadMap();