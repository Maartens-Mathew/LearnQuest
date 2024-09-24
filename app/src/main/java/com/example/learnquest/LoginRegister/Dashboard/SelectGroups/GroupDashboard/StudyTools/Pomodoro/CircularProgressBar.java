package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.StudyTools.Pomodoro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressBar extends View {
    private Paint circlePaint;
    private Paint progressPaint;
    private Paint textPaint;
    private float progress = 0; // Progress in percentage
    private String timeText = "00:00"; // Default text
    private int strokeWidth = 20;

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(0xFFDDDDDD); // Background circle color
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(0xFF3F51B5); // Progress color
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(0xFF000000); // Text color
        textPaint.setTextSize(80);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float radius = Math.min(centerX, centerY) - strokeWidth / 2;

        // Draw background circle
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Draw progress arc
        canvas.drawArc(strokeWidth / 2, strokeWidth / 2, getWidth() - strokeWidth / 2, getHeight() - strokeWidth / 2,
                -90, 360 * (progress / 100), false, progressPaint);

        // Draw text in the center
        canvas.drawText(timeText, centerX, centerY + 25, textPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate(); // Redraw the view
    }

    public void setTimerText(String timeText) {
        this.timeText = timeText;
        invalidate(); // Redraw the view
    }
}
