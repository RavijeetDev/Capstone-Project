package com.ravijeet.teleportal.widgets;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by Ravijeet on 3/2/18.
 */

public class CurvedBackgroundShape extends Shape {

    @Override
    public void draw(Canvas canvas, Paint paint) {

        canvas.drawColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        Path path = new Path();
        path.moveTo(0,0);
        path.quadTo(getWidth() / 2, getWidth() / 10, getWidth(), 0);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        canvas.drawPath(path, paint);

    }
}
