var canvas = document.getElementById("canvas");
var ctx = canvas.getContext('2d');
var cL = parseInt(canvas.getBoundingClientRect().left.toString()),
    cT = parseInt(canvas.getBoundingClientRect().top.toString());
var double = [];
var central;

window.onload = function(event){
    debugDraw();
};

debugDraw = function(){
    ctx.fillStyle = "rgb(255,255,255)";
    ctx.fillRect(0, 0, canvas.width, canvas.height);
};

canvas.addEventListener('click', function (event) {
    var x = event.pageX - cL;
    var y = event.pageY - cT;
    let point = {x: x, y: y};

    pushDouble(point);
    SymmetricLine(1);
}, false);

canvas.addEventListener('contextmenu', function (event) {
    var x = event.pageX - cL;
    var y = event.pageY - cT;
    let point = {x: x, y: y};
    central = point;

    MidpointCircle(point, 60);
}, false);

document.addEventListener('keydown', (event) => {
    const keyName = event.key;

    if (keyName === 'g') {
        if(double.length !== 2)
            return;
        ThickAntialiasedLine(10);
    }
    else if(keyName === 't'){
        if(double.length !== 2)
            return;
        ThickAntialiasedLine(1);
    }
    else if(keyName === 'd'){
        if(double.length !== 2)
            return;
        Original(10);
    }
    else if(keyName === 's'){
        if(double.length !== 2)
            return;
        ThickAntialiasedLine(1);
    }
}, false);

document.addEventListener('keydown', (event) => {
    const keyName = event.key;

    switch (parseInt(keyName)) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 9:
            SymmetricLine(parseInt(keyName));
            break;
        default:
            return;
    }

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

drawPoint = function(x, y, clr = 0, sz = 1){
    var A1 = [1];
    var A3 = [[0, 1, 0], [1, 1, 1], [0, 1, 0]];
    var A5 = [[0, 1, 1, 1, 0],
        [1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1],
        [0, 1, 1, 1, 0]];
    var A7 = [[0, 0, 1, 1, 1, 0, 0],
        [0, 1, 1, 1, 1, 1, 0],
        [1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1, 1, 1],
        [0, 1, 1, 1, 1, 1, 0],
        [0, 0, 1, 1, 1, 0, 0]];
    var A9 = [[0, 0, 0, 1, 1, 1, 0, 0, 0],
        [0, 0, 1, 1, 1 ,1, 1, 0, 0],
        [0, 1, 1, 1, 1, 1, 1, 1, 0],
        [1, 1, 1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1, 1, 1, 1, 1],
        [0, 1, 1, 1, 1, 1, 1, 1, 0],
        [0, 0, 1, 1, 1 ,1, 1, 0, 0],
        [0, 0, 0, 1, 1, 1, 0, 0, 0]];

    let curImgData = ctx.getImageData(0, 0, canvas.width, canvas.height);

    let A;
    switch (sz) {
        case 1:
            A = A1;
            break;
        case 3:
            A = A3;
            break;
        case 5:
            A = A5;
            break;
        case 7:
            A = A7;
            break;
        case 9:
            A = A9;
            break;
        default:
            return;
    }
    if(sz === 1){
        curImgData.data[((y - 1) * curImgData.width + (x - 1)) * 4 - 1 + 1] = clr;
        curImgData.data[((y - 1) * curImgData.width + (x - 1)) * 4 - 1 + 2] = clr;
        curImgData.data[((y - 1) * curImgData.width + (x - 1)) * 4 - 1 + 3] = clr;
    }
    else{
        for(let i = 0; i < sz; i++){
            for(let j = 0; j < sz; j++){
                console.log("fuck draw");
                if(A[i][j]){
                    curImgData.data[(((y+j - (sz-1)/2) - 1) * curImgData.width + ((x+i - (sz-1)/2) - 1)) * 4 - 1 + 1] = clr;
                    curImgData.data[(((y+j - (sz-1)/2) - 1) * curImgData.width + ((x+i - (sz-1)/2) - 1)) * 4 - 1 + 2] = clr;
                    curImgData.data[(((y+j - (sz-1)/2) - 1) * curImgData.width + ((x+i - (sz-1)/2) - 1)) * 4 - 1 + 3] = clr;
                }
            }
        }
    }
    ctx.putImageData(curImgData, 0, 0);
};

SymmetricLine = function(sz)
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
    drawPoint(xf, yf, 0, sz);
    drawPoint(xb, yb, 0, sz);

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
        drawPoint(xf, yf, 0, sz);
        drawPoint(xb, yb, 0, sz);
    }
    if(xf === xb){
        while(Math.abs(yf - yb) > 1){
            if(dy > 0){
                drawPoint(xf, ++yf, 0, sz)
            }
            else {
                drawPoint(xf, ++yb, 0, sz)
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

covr = function(d, r){
    if(d < r)
        return (1/Math.PI) * Math.acos(d/r) - (d/(Math.PI*r*r)) * Math.sqrt(r*r - d*d);
    else return 0;
};

coverage = function(thickness, distance, r) {
    var w = thickness/2;
    if(w >= r){
        if(w < distance)
            return covr(distance - w, r);
        else if(distance >= 0 && distance <= w)
            return 1 - covr(w - distance, r);
        else {
            alert("Wrong coverage, thicker");
            return null;
        }
    }
    else {
        if(distance >= 0 && distance < w)
            return 1 - covr(w - distance, r) - covr(w + distance, r);
        else if(distance >= w && distance < r - w)
            return covr(distance - w, r) - covr(distance + w, r);
        else if(distance >= r - w && distance <= r + w)
            return covr(distance - w, r);
        else {
            alert("Wrong converage, thinner");
            return null;
        }
    }
};

IntensifyPixel = function(x, y, thickness, distance)
{
    var r = 0.5;
    var cov = coverage(thickness, distance, r);
    if ( cov > 0 )
        drawPoint(x, y, lerp(255, 0, cov));
    return cov;
};

ThickAntialiasedLine = function(thickness)
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

    var two_v_dx = 0; //numerator, v=0 for the first pixel
    var two_v_dy = 0; //numerator, v=0 for the first pixel
    var invDenom = 1/(2*Math.sqrt(Math.abs(dx*dx) + Math.abs(dy*dy))); //inverted denominator
    var two_dx_invDenom = Math.abs(2*dx*invDenom); //precomputed constant
    var two_dy_invDenom = Math.abs(2*dy*invDenom); //precomputed constant

    var i;

    if(Math.abs(dx) > Math.abs(dy)){
        IntensifyPixel(xf, yf, thickness, 0);
        for(i=1; IntensifyPixel(xf, yf+i, thickness, i*two_dx_invDenom); ++i){}
        for(i=1; IntensifyPixel(xf, yf-i, thickness, i*two_dx_invDenom); ++i){}
    }
    else{
        IntensifyPixel(xf, yf, thickness, 0);
        for(i=1; IntensifyPixel(xf+i, yf, thickness, i*two_dy_invDenom); ++i){}
        for(i=1; IntensifyPixel(xf-i, yf, thickness, i*two_dy_invDenom); ++i){}
    }

    if(dx === 0){
        let t;
        let dv = Math.abs(d);
        let dNEv = Math.abs(2 * dy);
        let yq = yf < yb ? yf : yb;
        let yp = yf >= yb ? yf : yb;
        while(yq <= yp){
            yq++;
            IntensifyPixel(xf, yq, thickness, Math.abs(dv*invDenom));
            for(i=1; IntensifyPixel(xf+i, yq, thickness, Math.abs(i*two_dy_invDenom - dv*invDenom)); ++i){}
            for(i=1; IntensifyPixel(xf-i, yq, thickness, Math.abs(i*two_dy_invDenom + dv*invDenom)); ++i){}
        }
    }

    while (xf < xb)
    {
        if(dy > 0){
            if(dx > dy){
                console.log("First");
                ++xf;
                if ( d < 0 ){
                    two_v_dx = d + dx;
                    d += dE;
                }
                else
                {
                    two_v_dx = d - dx;
                    d += dNE;
                    if(yf < yb){
                        ++yf;
                    }
                    else if(yf > yb){
                        --yf;
                    }
                }
                IntensifyPixel(xf, yf, thickness, Math.abs(two_v_dx*invDenom));
                for(i=1; IntensifyPixel(xf, yf+i, thickness, Math.abs(i*two_dx_invDenom - two_v_dx*invDenom)); ++i){}
                for(i=1; IntensifyPixel(xf, yf-i, thickness, Math.abs(i*two_dx_invDenom + two_v_dx*invDenom)); ++i){}
            }
            else {
                console.log("Second");
                if(yf < yb){
                    ++yf;
                }
                else if(yf > yb){
                    --yf;
                }
                if(d1 < 0){
                    two_v_dy = d1 + dy;
                    d1 += dE1;

                }
                else{
                    two_v_dy = d1 - dy;
                    d1 += dNE1;
                    ++xf;
                }
                IntensifyPixel(xf, yf, thickness, Math.abs(two_v_dy*invDenom));
                for(i=1; IntensifyPixel(xf+i, yf, thickness, Math.abs(i*two_dy_invDenom - two_v_dy*invDenom)); ++i){}
                for(i=1; IntensifyPixel(xf-i, yf, thickness, Math.abs(i*two_dy_invDenom + two_v_dy*invDenom)); ++i){}
            }
        }
        else {
            if(dx > dym){
                console.log("Third");
                ++xf;
                if ( dm < 0 ){
                    two_v_dx = dm + dx;
                    dm += dEm;
                }
                else
                {
                    two_v_dx = dm - dx;
                    dm += dNEm;
                    if(yf < yb){
                        ++yf;
                    }
                    else if(yf > yb){
                        --yf;
                    }
                }
                IntensifyPixel(xf, yf, thickness, Math.abs(two_v_dx*invDenom));
                for(i=1; IntensifyPixel(xf, yf-i, thickness, Math.abs(i*two_dx_invDenom - two_v_dx*invDenom)); ++i){}
                for(i=1; IntensifyPixel(xf, yf+i, thickness, Math.abs(i*two_dx_invDenom + two_v_dx*invDenom)); ++i){}
            }
            else {
                console.log("Fourth");
                if(yf < yb){
                    ++yf;
                }
                else if(yf > yb){
                    --yf;
                }
                if(d1m < 0){
                    two_v_dy = d1m + dym;
                    d1m += dE1;
                }
                else{
                    two_v_dy = d1m - dym;
                    d1m += dNE1m;
                    ++xf;
                }
                IntensifyPixel(xf, yf, thickness, Math.abs(two_v_dy*invDenom));
                for(i=1; IntensifyPixel(xf+i, yf, thickness, Math.abs(i*two_dy_invDenom - two_v_dy*invDenom)); ++i){}
                for(i=1; IntensifyPixel(xf-i, yf, thickness, Math.abs(i*two_dy_invDenom + two_v_dy*invDenom)); ++i){}

            }
        }
    }
};

Original = function(thickness){
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
            two_v_dx = d - dx;
            d += dNE;
            ++y;
        }

// Now set the chosen pixel and its neighbors
        IntensifyPixel(x, y, thickness, Math.abs(two_v_dx*invDenom));
        for(i=1; IntensifyPixel(x, y+i, thickness, i*two_dx_invDenom - two_v_dx*invDenom); ++i){}
        for(i=1; IntensifyPixel(x, y-i, thickness, i*two_dx_invDenom + two_v_dx*invDenom); ++i){}

    }
};
