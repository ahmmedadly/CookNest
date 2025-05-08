package com.example.cooknest.ui.calender;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cooknest.R;
import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.MealResponse;
import com.example.cooknest.data.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlannerFragment extends Fragment {

    private RecyclerView rvPlanner;
    private MealPlanAdapter adapter;
    private List<MealPlan> mealPlanList = new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarView calendarView4 = view.findViewById(R.id.calendarView4);

// Set listener for date changes
        calendarView4.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(
                    @NonNull CalendarView view,
                    int year,
                    int month, // 0-based (0 = January, 11 = December)
                    int dayOfMonth
            ) {
                String selectedDate = formatDate(year%2000, month +1, dayOfMonth);
                // Do something with the selected date

                new Thread(()-> {
                    MealRepository a = new MealRepository(getActivity());
                    a.getPlannedMealsByDate(selectedDate);
                    Log.i("test", selectedDate);
try {


    int mealid = a.getPlannedMealsByDate(selectedDate).get(0).getMealId();
    Log.i("test", "id is "+mealid);
    RetrofitClient.getInstance().getApiService().getMealById(""+mealid).enqueue(new Callback<MealResponse>() {
        @Override
        public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
            Log.i("test", "meals");
            if (response.isSuccessful() && response.body() != null) {
                MealResponse mealResponse = response.body();
                String mealName = mealResponse.getMeals().get(0).getStrMeal();
                Log.i("test", mealName);
               // adapter.updateData(new MealPlan(mealResponse.getMeals().get(0), selectedDate));
                adapter.updateData(Arrays.asList(new MealPlan(mealResponse.getMeals().get(0), selectedDate)));

            }
            else
            {
                Log.i("test", "no meals");
            }
        }

        @Override
        public void onFailure(Call<MealResponse> call, Throwable throwable) {
            Log.i("test", "no onFailure");
        }
    });

}
catch ( Exception e)
{
    Log.i("test", "no meals");
}



                }).start();

            }
        });



    }
    private String formatDate(int year, int month, int day) {
        return String.format(Locale.getDefault(), "%02d%02d%02d", year, month, day);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_planner, container, false);
        rvPlanner = view.findViewById(R.id.rvPlanner);
        adapter = new MealPlanAdapter(mealPlanList);



        rvPlanner.setAdapter(adapter);

     /*   SharedPreferences prefs = getActivity().getSharedPreferences("meal_planner", MODE_PRIVATE);
        for (String key : prefs.getAll().keySet()) {
            String mealName = prefs.getString(key, "");
            mealPlanList.add(new MealPlan(mealName, key));
        }*/
        adapter.notifyDataSetChanged();
        return view;
    }

    private class MealPlan {
        private Meal mealName;
        private String date;

        public MealPlan(Meal mealName, String date) {
            this.mealName = mealName;
            this.date = date;
        }

        public String getMealName() {
            return mealName.getStrMeal();
        }

        public String getDate() {
            return date;
        }
    }

    private class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.ViewHolder> {
        private List<MealPlan> mealPlanList;

        public MealPlanAdapter(List<MealPlan> mealPlanList) {
            this.mealPlanList = mealPlanList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_plan, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MealPlan mealPlan = mealPlanList.get(position);
            holder.tvMealName.setText(mealPlan.getMealName());
            holder.tvMealDate.setText(mealPlan.getDate());
        }
        public void updateData(List<MealPlan> newData) {
            mealPlanList.clear();
            mealPlanList.addAll(newData);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mealPlanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvMealName, tvMealDate;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMealName = itemView.findViewById(R.id.tvMealName);
                tvMealDate = itemView.findViewById(R.id.tvDay);
            }
        }
    }
}

