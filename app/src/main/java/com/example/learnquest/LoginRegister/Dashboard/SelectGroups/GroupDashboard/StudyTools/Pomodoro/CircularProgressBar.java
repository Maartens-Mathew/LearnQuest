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

    public CircularProgressBar(Context context) {
        super(context);
        init();
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Background circle paint
        circlePaint = new Paint();
        circlePaint.setColor(0xFFDDDDDD); // Gray color for background circle
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setAntiAlias(true);

        // Progress paint
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setAntiAlias(true);

        // Center text paint
        textPaint = new Paint();
        textPaint.setColor(0xFF000000); // Black color for text
        textPaint.setTextSize(80);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) - (strokeWidth / 2f);

        // Draw background circle
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Update the color of the progress paint based on the progress value
        progressPaint.setColor(getInterpolatedColor(0xFF00796B, 0xFFFF0000, progress / 100f));

        // Draw progress arc
        canvas.drawArc(strokeWidth / 2f, strokeWidth / 2f,
                getWidth() - strokeWidth / 2f,
                getHeight() - strokeWidth / 2f,
                -90, 360 * (progress / 100),
                false, progressPaint);

        // Draw the timer text in the center
        canvas.drawText(timeText, centerX, centerY + 25, textPaint);
    }

    // Method to set the progress in percentage (0 to 100)
    public void setProgress(float progress) {
        this.progress = Math.max(0, Math.min(progress, 100)); // Ensure progress stays within bounds
        invalidate(); // Redraw the view
    }

    // Method to update the time text displayed in the center
    public void setTimerText(String timeText) {
        this.timeText = timeText;
        invalidate(); // Redraw the view
    }

    // Helper method to interpolate between two colors
    private int getInterpolatedColor(int startColor, int endColor, float proportion) {
        int startA = (startColor >> 24) & 0xff;
        int startR = (startColor >> 16) & 0xff;
        int startG = (startColor >> 8) & 0xff;
        int startB = startColor & 0xff;

        int endA = (endColor >> 24) & 0xff;
        int endR = (endColor >> 16) & 0xff;
        int endG = (endColor >> 8) & 0xff;
        int endB = endColor & 0xff;

        int a = (int) (startA + (endA - startA) * proportion);
        int r = (int) (startR + (endR - startR) * proportion);
        int g = (int) (startG + (endG - startG) * proportion);
        int b = (int) (startB + (endB - startB) * proportion);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
