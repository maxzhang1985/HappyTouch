package com.kuailedian.happytouch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.kuailedian.reveallayout.RevealLayout;


public class FragmentSampleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

       TextView tv1 = (TextView) this.findViewById(R.id.eating);
        TextView tv2 = (TextView) this.findViewById(R.id.shopping);

       tv1.setOnClickListener(listener );
        tv2.setOnClickListener(listener);


//       if (savedInstanceState == null) {
//         getSupportFragmentManager().beginTransaction()
//               .add(R.id.container, SimpleFragment.newInstance(0))
//                .commit();
//       }
    }

   private   View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int index = 0 ;
            if(v.getId() == R.id.eating){
              index = 1;
            }
            else
            {
                index =2;
            }
            Fragment fragment =   SimpleFragment.newInstance(index);
            Log.v("fragment","new");
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.container,fragment)
                    .commit();
            Log.v("fragment","todo");
        }
    };



    public static class SimpleFragment extends Fragment {

        private RevealLayout mRevealLayout;
        private TextView mTextView;
        private int mIndex;
        private static final int[] COLOR_LIST = new int[]{
            0xff33b5e5,
            0xff99cc00,
            0xffff8800,
            0xffaa66cc,
            0xffff4444,
        };

        public static SimpleFragment newInstance(int index) {
            SimpleFragment fragment = new SimpleFragment();
            Bundle args = new Bundle();
            args.putInt("index", index);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
            mRevealLayout = (RevealLayout) rootView.findViewById(R.id.reveal_layout);
            mTextView = (TextView) rootView.findViewById(R.id.text);
            mIndex = getArguments().getInt("index");
            mTextView.setBackgroundColor(COLOR_LIST[mIndex % 5]);
            mTextView.setText("Fragment " + mIndex);
            mRevealLayout.setContentShown(false);
            mRevealLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mRevealLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    mRevealLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRevealLayout.show();
                        }
                    }, 50);
                }
            });

            return rootView;
        }

    }
}
