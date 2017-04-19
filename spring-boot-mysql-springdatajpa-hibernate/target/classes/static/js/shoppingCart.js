/**
 * Created by mazi on 19.04.17.
 */
var sum = 0.0;


for(var i=0;i<$('.p').length;i++){
    sum  = sum +  parseFloat($('#pr'+i).text().match(/\d+/)[0]);
}
$("#total").text("Gesamt: " +sum +"€");
