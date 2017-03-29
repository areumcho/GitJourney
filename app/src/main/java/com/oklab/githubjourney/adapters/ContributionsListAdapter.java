package com.oklab.githubjourney.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.oklab.githubjourney.R;
import com.oklab.githubjourney.data.ContributionsDataLoader;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by olgakuklina on 2017-01-24.
 */

public class ContributionsListAdapter extends BaseAdapter {
    private static final String TAG = ContributionsListAdapter.class.getSimpleName();
    private final Context context;
    private final int numberOfDays;
    private final int numberOfEmptyDaysInMonth;
    private final HashMap<Integer, Integer> contributionsMap;
    private final int colorIntensivity;
    private Calendar calendar = (Calendar) Calendar.getInstance().clone();

    public ContributionsListAdapter(Context context, int offset, Cursor cursor) {
        this.context = context;
        calendar.add(Calendar.MONTH, -offset);
        numberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        numberOfEmptyDaysInMonth = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;

        contributionsMap = new HashMap<Integer, Integer>();
        cursor.moveToPosition(-1);
        Calendar calendar = Calendar.getInstance();
        int colorIntensivity = 0;
        while (cursor.moveToNext()) {
            long date = cursor.getLong(ContributionsDataLoader.Query.PUBLISHED_DATE);
            calendar.setTimeInMillis(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (contributionsMap.containsKey(day)) {
                contributionsMap.put(day, contributionsMap.get(day) + 1);
            } else {
                contributionsMap.put(day, 1);
            }
            if (contributionsMap.get(day) > colorIntensivity) {
                colorIntensivity = contributionsMap.get(day);
            }
        }
        this.colorIntensivity = colorIntensivity;
    }

    @Override
    public int getCount() {
        return numberOfDays + numberOfEmptyDaysInMonth;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        if (i >= numberOfEmptyDaysInMonth) {
            v = inflater.inflate(R.layout.grid_list_item, viewGroup, false);
            ImageButton button = (ImageButton) v.findViewById(R.id.contribution_button);

            Log.v(TAG, "contribMap = " + contributionsMap);
            Integer colorIntRes = contributionsMap.get(i - numberOfEmptyDaysInMonth + 1);

            if (colorIntRes == null) {
                colorIntRes = -1;
            }
            switch (colorIntRes) {
                case 0:
                    button.setBackground(context.getResources().getDrawable(R.color.empty));
                    break;
                case 1:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_10));
                    break;
                case 2:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_9));
                    break;
                case 3:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_8));
                    break;
                case 4:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_7));
                    break;
                case 5:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_6));
                    break;
                case 6:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_5));
                    break;
                case 7:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_4));
                    break;
                case 8:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_3));
                    break;
                case 9:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_2));
                    break;
                case 10:
                    button.setBackground(context.getResources().getDrawable(R.drawable.contributions_grid_color_1));
                    break;

                default:
                    button.setBackgroundColor(context.getResources().getColor(R.color.empty));
                    break;
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, contributionsMap.get(i - numberOfEmptyDaysInMonth + 1) + " contributions at " + (i - numberOfEmptyDaysInMonth + 1), Toast.LENGTH_SHORT).show();
                }
            });
            Log.v(TAG, "i " + i);

        } else {
            v = inflater.inflate(R.layout.empty_grid_list_item, viewGroup, false);
        }

        return v;
    }
}
