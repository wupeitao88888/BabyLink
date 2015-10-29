package com.shiliuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.ExerciseActivity;
import com.shiliuke.bean.Exercise;
import com.shiliuke.BabyLink.R;
import com.shiliuke.utils.GlideCircleTransform;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 活动
 */
public class ExerciseAdapter extends BaseAdapter {
    private Context context;
    private List<Exercise> list;

    public ExerciseAdapter(Context context, List<Exercise> list) {
        super();
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Exercise classList = list.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_exercise_item, null);
        }

        ImageView exercise_pic = ViewHolder.get(convertView, R.id.exercise_pic);
        ImageView exercise_authorPic = ViewHolder.get(convertView, R.id.exercise_authorPic);
        ImageView layout_icon1 = ViewHolder.get(convertView, R.id.layout_icon1);
        ImageView layout_icon2 = ViewHolder.get(convertView, R.id.layout_icon2);
        ImageView layout_icon3 = ViewHolder.get(convertView, R.id.layout_icon3);
        ImageView layout_icon4 = ViewHolder.get(convertView, R.id.layout_icon4);
        ImageView layout_icon5 = ViewHolder.get(convertView, R.id.layout_icon5);
        TextView exercise_title = ViewHolder.get(convertView, R.id.exercise_title);
        TextView exercise_address = ViewHolder.get(convertView, R.id.exercise_address);
        TextView exercise_usercount = ViewHolder.get(convertView, R.id.exercise_usercount);
        TextView exercise_authorName = ViewHolder.get(convertView, R.id.exercise_authorName);
        Button signup = ViewHolder.get(convertView, R.id.signup);
        setImage(exercise_pic, classList.getExercise_pic(), context);
        setImageUserPic(exercise_authorPic, classList.getExercise_authorPic(), context);
        ImageView[] image = {layout_icon1, layout_icon2, layout_icon3, layout_icon4, layout_icon5};
        for (int i = 0; i < 5; i++) {
            try {
                setImageUserPic(image[i], classList.getExercise_signlist().get(i).getUserpic(), context);
            } catch (Exception e) {
                image[i].setVisibility(View.GONE);
            }
        }
        ViewUtil.setText(context, exercise_title, classList.getExercise_title());
        ViewUtil.setText(context, exercise_address, classList.getExercise_address());
        ViewUtil.setText(context, exercise_usercount, classList.getExercise_usercount());
        ViewUtil.setText(context, exercise_authorName, classList.getExercise_authorName());

//        Intent intent=new Intent(mActivity,ExerciseActivity.class);
//        startActivity(intent);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.title_bar)
                .error(R.drawable.title_bar)
                .crossFade()
                .into(iview);

    }

    private void setImageUserPic(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(context))
                .placeholder(R.drawable.title_bar)
                .error(R.drawable.title_bar)
                .crossFade()
                .into(iview);
    }


}
