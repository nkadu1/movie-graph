package com.example.movieapplication.adapters;

import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import javax.inject.Inject;

import android.view.View;
import android.view.ViewGroup;
import com.example.GetUsersCollectionQuery;
import com.example.movieapplication.databinding.ListCardBinding;
import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ListItemViewHolder> {

    private List<GetUsersCollectionQuery.GetAllListByUser> userList = new ArrayList<>();

    @Inject
    public UsersListAdapter() {
        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListCardBinding listCardBinding = ListCardBinding.inflate(layoutInflater, parent, false);
        return new ListItemViewHolder(listCardBinding.getRoot(), listCardBinding);
    }

    public void setUserList(List<GetUsersCollectionQuery.GetAllListByUser> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        holder.bind(userList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {

        private final ListCardBinding listCardBinding;

        ListItemViewHolder(@NonNull View itemView, ListCardBinding listCardBinding) {
            super(itemView);
            this.listCardBinding = listCardBinding;
        }

        void bind(GetUsersCollectionQuery.GetAllListByUser list) {
            this.listCardBinding.listName.setText(list.listName());
        }

    }
}
