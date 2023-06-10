package ru.vsu.cs.weatherapp2.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.vsu.cs.weatherapp2.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView temperature;
        private TextView wind;
        private TextView place;
        private TextView date;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.temperature);
            wind = itemView.findViewById(R.id.wind);
            place = itemView.findViewById(R.id.place);
            date = itemView.findViewById(R.id.date);
        }
        void bind(User user) {
            temperature.setText(user.getTemperature());
            wind.setText(user.getWind());
            place.setText(user.getPlace());
            date.setText(user.getDate());
        }
    }
}
