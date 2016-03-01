package edu.uw.weardemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up the ViewPager
        mViewPager = (ViewPager) findViewById(R.id.pager);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getFragmentManager());

        adapter.addFragment(new FirstFragment());
        adapter.addFragment(new SecondFragment());

        mViewPager.setAdapter(adapter);


//        CardFragment cardFragment = CardFragment.create("Title", "This is the description of the card");
//
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.frame, cardFragment);
//        ft.commit();


//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });



    }


    static class MyFragmentAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> mFragments;

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<Fragment>();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
            // Update the pager when adding a fragment.
            notifyDataSetChanged();
        }
    }

    public static class FirstFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.first_fragment, container, false);
        }
    }

    public static class SecondFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.second_fragment, container, false);
        }
    }

    public void speakNow(View v) {
        Log.v("TAG","speaking");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String text = results.get(0);

            Log.v("TAG", text);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
