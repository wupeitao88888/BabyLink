package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by wpt on 2015/10/28.
 */
public class ExerciseActivity extends ActivitySupport implements View.OnClickListener {
    private Button exercise_apply_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_exercise);
//        for (int i = 0; i < original_bottom_ll.getChildCount(); i++) {
//            original_bottom_ll.getChildAt(i).setOnClickListener(this);
//        }
        exercise_apply_bt = (Button) findViewById(R.id.exercise_apply_bt);
        exercise_apply_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_apply_bt:
               mIntent(this,ApplyActivity.class);
                break;
        }
    }
}
