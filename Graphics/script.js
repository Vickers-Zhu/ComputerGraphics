var canvas = document.getElementById("canvas");
var ctx = canvas.getContext('2d');
var cL = parseInt(canvas.getBoundingClientRect().left.toString()),
    cT = parseInt(canvas.getBoundingClientRect().top.toString());
var double = [];

window.onload = function(event){
    debugDraw();
};

canvas.addEventListener('click', function (event) {
    var x = event.pageX - cL;
    var y = event.pageY - cT;
    let point = {x: x, y: y};
    // let curImgData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    pushDouble(point);
    alert(double.length);
    // alert(getPixelClr(curImgData, point, 2));
    SymmetricLine();


}, false);

pushDouble = function(point){
    switch (double.length) {
        case 0:
        case 1:
            double.push(point);
            break;
        case 2:
            double = [];
            double.push(point);
            break;
        default:
            double = [];
            double.push(point);
    }
};

getPixelClr = function(imageData, point, which){
    switch (which) {
        case 1:
        case 2:
        case 3:
            return imageData.data[((point.y - 1) * imageData.width + (point.x - 1)) * 4 - 1 + which];
        default:
            return null;
    }
};

drawLine = function(){
    let curImgData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    switch (double.length) {
        case 2:
            curImgData.data[((double[1].y - 1) * curImgData.width + (double[1].x - 1)) * 4 - 1 + 1] = 0;
            curImgData.data[((double[1].y - 1) * curImgData.width + (double[1].x - 1)) * 4 - 1 + 2] = 0;
            curImgData.data[((double[1].y - 1) * curImgData.width + (double[1].x - 1)) * 4 - 1 + 3] = 0;
        case 1:
            curImgData.data[((double[0].y - 1) * curImgData.width + (double[0].x - 1)) * 4 - 1 + 1] = 0;
            curImgData.data[((double[0].y - 1) * curImgData.width + (double[0].x - 1)) * 4 - 1 + 2] = 0;
            curImgData.data[((double[0].y - 1) * curImgData.width + (double[0].x - 1)) * 4 - 1 + 3] = 0;
            break;
        default:
    }
    ctx.putImageData(curImgData, 0, 0);
};

drawPoint = function(x, y){
    let curImgData = ctx.getImageData(0, 0, canvas.width, canvas.height);

    curImgData.data[((y - 1) * curImgData.width + (x - 1)) * 4 - 1 + 1] = 0;
    curImgData.data[((y - 1) * curImgData.width + (x - 1)) * 4 - 1 + 2] = 0;
    curImgData.data[((y - 1) * curImgData.width + (x - 1)) * 4 - 1 + 3] = 0;

    ctx.putImageData(curImgData, 0, 0);
};


SymmetricLine = function()
{
    if(double.length !== 2)
        return;
    var x1 = double[0].x,
        y1 = double[0].y,
        x2 = double[1].x,
        y2 = double[1].y;
    var dx = x2 - x1;
    var dy = y2 - y1;
    var d = 2*dy - dx;
    var dE = 2*dy;
    var dNE = 2*(dy - dx);
    var xf = x1, yf = y1;
    var xb = x2, yb = y2;
    drawPoint(xf, yf);
    drawPoint(xb, yb);
    while (xf < xb)
    {
        ++xf; --xb;
        if ( d < 0 )
            d += dE;
        else
        {
            d += dNE;
            ++yf;
            --yb;
        }
        drawPoint(xf, yf);
        drawPoint(xb, yb);
    }
};

debugDraw = function(){
    ctx.fillStyle = "white";
    ctx.fillRect(0, 0, canvas.width, canvas.height);

};



