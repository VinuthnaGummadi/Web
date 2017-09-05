/**
 * Created by vinuthna on 04-09-2017.
 */
function myAccFunc(value) {
    var x = document.getElementById("demoAcc"+value);
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else {
        x.className = x.className.replace(" w3-show", "");
    }
}
function displayDiv(value1,value2){
    var x = document.getElementById(value1);

        x.style.display = 'block';

    var x = document.getElementById(value2);

        x.style.display = 'none';

}