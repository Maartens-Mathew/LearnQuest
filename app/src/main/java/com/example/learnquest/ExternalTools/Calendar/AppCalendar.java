package com.example.learnquest.ExternalTools.Calendar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AppCalendar implements CalendarAdapter.OnItemListener{

    View view;

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;


    /** <h1>App calendar constructor</h1>
     *  <p>You need to pass through the view that you want to use the calendar on. Use the 'getCurrentFocus()' method. </p>
     *
     * @param view
     */

    public AppCalendar(View view){
        this.view = view;
    }


    /**
     *  <h1> Using Calendar component </h1>
     *  <p> (Mathew) I have managed to place all the code into a class, that carefully manipulates
     *  some UI controls, to achieve the calendar component. It follows 3 steps:</p>
     *
     *  <ol>
     *    <li>initialize Widgets</li>
     *   <li>sets the selectedDate variable</li>
     *   <li>sets the MonthView</li>
     *  </ol>
     *
     *   <p>Before calling this method, it's important that the following UI components exist, and are used in the view:</p>
     *
     *   <ul>
     *    <li>A RecyclerView (the id must be 'calendarRecyclerView') -> will hold the calendar body</li>
     *    <li>A TextView (this id must be 'monthYearTV') -> will display the month and year selected</li>
     *    </ul>
     *
     *    </p>
     *
     *
     *
     */

    public void setCalendarView(){
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals(""))
        {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
