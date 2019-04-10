var canvas = document.getElementById("canvas");
var ctx = canvas.getContext('2d');
var cL = parseInt(canvas.getBoundingClientRect().left.toString()),
    cT = parseInt(canvas.getBoundingClientRect().top.toString());
var double = [];
var central;

window.onload = function(event){
    debugDraw();
};

canvas.addEventListener('click', function (event) {
    var x = event.pageX - cL;
    var y = event.pageY - cT;
    let point = {x: x, y: y};

    // let curImgData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    // alert(double.length);
    // alert(getPixelClr(curImgData, point, 2));

    pushDouble(point);
    SymmetricLine();
}, false);
canvas.addEventListener('contextmenu', function (event) {
    var x = event.pageX - cL;
    var y = event.pageY - cT;
    let point = {x: x, y: y};
    central = point;

    // let curImgData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    // alert(double.length);
    // alert(getPixelClr(curImgData, point, 2));

    MidpointCircle(point, 60);
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

    var x1, y1, x2, y2;
    if(double[0].x < double[1].x) {
        x1 = double[0].x;
        y1 = double[0].y;
        x2 = double[1].x;
        y2 = double[1].y;
    }
    else{
        x1 = double[1].x;
        y1 = double[1].y;
        x2 = double[0].x;
        y2 = double[0].y;
    }

    var dx = x2 - x1;
    var dy = y2 - y1;
    var dym = -dy;
    var d = 2*dy - dx;
    var d1 = 2*dx - dy;
    var dm = 2*dym-dx;
    var d1m = 2*dx - dym;
    var dE = 2*dy;
    var dEm = 2*dym;
    var dE1 = 2*dx;
    var dNE = 2*(dy - dx);
    var dNEm = 2*(dym - dx);
    var dNE1 = 2*(dx - dy);
    var dNE1m = 2*(dx - dym);
    var xf = x1, yf = y1;
    var xb = x2, yb = y2;
    drawPoint(xf, yf);
    drawPoint(xb, yb);

    while (xf < xb)
    {
        if(dy > 0){
            if(dx > dy){
                ++xf; --xb;
                if ( d < 0 )
                    d += dE;
                else
                {
                    d += dNE;
                    if(yf < yb){
                        ++yf;
                        --yb;
                    }
                    else{
                        --yf;
                        ++yb;
                    }
                }
            }
            else {
                if(yf < yb){
                    ++yf;
                    --yb;
                }
                else{
                    --yf;
                    ++yb;
                }
                if(d1 < 0)
                    d1 += dE1;
                else{
                    d1 += dNE1;
                    ++xf;
                    --xb;
                }
            }
        }
        else {
            if(dx > dym){
                ++xf; --xb;
                if ( dm < 0 )
                    dm += dEm;
                else
                {
                    dm += dNEm;
                    if(yf < yb){
                        ++yf;
                        --yb;
                    }
                    else{
                        --yf;
                        ++yb;
                    }
                }
            }
            else {
                if(yf < yb){
                    ++yf;
                    --yb;
                }
                else{
                    --yf;
                    ++yb;
                }
                if(d1m < 0)
                    d1m += dE1;
                else{
                    d1m += dNE1m;
                    ++xf;
                    --xb;
                }
            }
        }
        drawPoint(xf, yf);
        drawPoint(xb, yb);
    }
    if(xf === xb){
        while(Math.abs(yf - yb) > 1){
            if(dy > 0){
                drawPoint(xf, ++yf)
            }
            else {
                drawPoint(xf, ++yb)
            }
        }
    }
};

MidpointCircle = function(point, R)
{
    var dE = 3;
    var dSE = 5-2*R;
    var d = 1-R;
    var x = 0;
    var y = R;
    drawPoint(x + point.x, y + point.y);
    while (y > x)
    {
        if ( d < 0 ) //move to E
        {
            d += dE;
            dE += 2;
            dSE += 2;
        }
        else //move to SE
        {
            d += dSE;
            dE += 2;
            dSE += 4;
            --y;
        }
        ++x;
        drawPoint(x + point.x, y + point.y);
        drawPoint(-x + point.x, y + point.y);
        drawPoint(x + point.x, -y + point.y);
        drawPoint(-x + point.x, -y + point.y);
        drawPoint(y + point.x, x + point.y);
        drawPoint(-y + point.x, x + point.y);
        drawPoint(y + point.x, -x + point.y);
        drawPoint(-y + point.x, -x + point.y);
    }
};

lerp = function (value1, value2, amount) {
    amount = amount < 0 ? 0 : amount;
    amount = amount > 1 ? 1 : amount;
    return value1 + (value2 - value1) * amount;
};

IntensifyPixel = function(x, y, thickness, distance)
{
    var r = 0.5;
    var cov = coverage(thickness, distance, r);
    if ( cov > 0 )
        drawPoint(x, y, lerp(255, 0, cov));
    return cov;
};

ThickAntialiasedLine = function(x1, y1, x2, y2, thickness)
{
//initial values in Bresenham;s algorithm
    var dx = x2 - x1, dy = y2 - y1;
    var dE = 2*dy, dNE = 2*(dy - dx);
    var d = 2*dy - dx;
    var two_v_dx = 0; //numerator, v=0 for the first pixel
    var invDenom = 1/(2*Math.sqrt(dx*dx + dy*dy)); //inverted denominator
    var two_dx_invDenom = 2*dx*invDenom; //precomputed constant
    var x = x1, y = y1;
    var i;
    IntensifyPixel(x, y, thickness, 0);
    for(i=1; IntensifyPixel(x, y+i, thickness, i*two_dx_invDenom); ++i){}
    for(i=1; IntensifyPixel(x, y-i, thickness, i*two_dx_invDenom); ++i){}
    while ( x < x2 )
    {
        ++x;
        if ( d < 0 ) // move to E
        {
            two_v_dx = d + dx;
            d += dE;
        }
        else // move to NE
        {
            two_v_dx = d-dx;
            d += dNE;
            ++y;
        }
// Now set the chosen pixel and its neighbors
        IntensifyPixel(x, y, thickness, two_v_dx*invDenom);
        for(i=1; IntensifyPixel(x, y+i, thickness, i*two_dx_invDenom - two_v_dx*invDenom); ++i){}
        for(i=1; IntensifyPixel(x, y-i, thickness, i*two_dx_invDenom + two_v_dx*invDenom); ++i){}
    }
};


debugDraw = function(){
    ctx.fillStyle = "rgb(255,255,255)";
    ctx.fillRect(0, 0, canvas.width, canvas.height);

};



