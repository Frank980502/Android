package com.example.ultimetable.ui.timetable;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ultimetable.R;
import com.example.ultimetable.adapter.TimetableRecyclerViewAdapter;
import com.example.ultimetable.databinding.FragmentTimetableListBinding;
import com.example.ultimetable.bean.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {/@link OnListFragmentInteractionListener}
 * interface.
 */
public class TimetableMondayFragment extends Fragment {

    // TODO: Customize parameters
    //private int mColumnCount = 1;
    private TimetableViewModel timetableViewModel;
    private TimetableRecyclerViewAdapter adapter;
    private View root;
    private List<Course> courseList=new ArrayList<Course>();
    //private FirebaseFirestore db;
    private Course course1,course2;
    //private  int isVisibleCount;  // 每个ViewPager中的Fragment维护一个标记状态位
    //private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TimetableMondayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        timetableViewModel =new ViewModelProvider(this).get(TimetableViewModel.class);

        root = inflater.inflate(R.layout.fragment_timetable_list, container, false);
        courseList.clear();
        course1 = new Course("CS4005", "aaa","08:00", "LEC", "KBG15");
        course2 = new Course("CS4082", "bbb","12:00", "LAB", "D1020");
        courseList.add(course1);
        courseList.add(course2);
        FragmentTimetableListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable_list, container, false);
        RecyclerView recyclerView = binding.recyclerViewMonday;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new TimetableRecyclerViewAdapter(getActivity(), courseList);
        recyclerView.setAdapter(adapter);
        return binding.getRoot();

    }

}
