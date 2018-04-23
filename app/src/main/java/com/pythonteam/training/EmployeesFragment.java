package com.pythonteam.training;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pythonteam.training.dummy.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EmployeesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Employee> empleados;
    private Context mContext;
    private MyEmployeeRecyclerViewAdapter mAdapter;
    private BroadcastReceiver mReceiver;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmployeesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EmployeesFragment newInstance(int columnCount) {
        EmployeesFragment fragment = new EmployeesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);

        mContext = container.getContext();
        // Set the adapter
        update();

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyEmployeeRecyclerViewAdapter(empleados, mListener);
            recyclerView.setAdapter(mAdapter);
        }
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) { //when intent is receiver, this method is called
                if(intent.getAction().contentEquals("db.update")){
                    //update intent received, call method to refresh your content loader
                    update();
                }
            }
        };
        //create a new intent filter
        IntentFilter mDataUpdateFilter = new IntentFilter("db.update");

        //register our broadcast receiver and intent filter
        getActivity().getApplicationContext().registerReceiver(mReceiver, mDataUpdateFilter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getApplicationContext().unregisterReceiver(mReceiver);
    }

    public void update() {
        if (mContext != null) {
            DBEmployee objDBE = new DBEmployee(mContext, "Trainings", null, 1);
            SQLiteDatabase objSQL = objDBE.getReadableDatabase();
            String query = "SELECT * FROM employee ORDER BY keyEmployee ASC";
            Cursor cursor = objSQL.rawQuery(query, null);
            empleados = new ArrayList<Employee>();
            if (cursor.moveToFirst()) {
                do {
                    Employee emp = new Employee();
                    emp.setKeyEmployee(cursor.getInt(0));
                    emp.setNameEmp(cursor.getString(1));
                    emp.setLastName(cursor.getString(2));
                    emp.setLastNameMom(cursor.getString(3));

                    emp.setBornDate(cursor.getString(4));
                    emp.setEmailEmp(cursor.getString(5));
                    emp.setPhone(cursor.getString(6));
                    emp.setKeyGender(cursor.getInt(7));
                    emp.setKeyJob(cursor.getInt(8));

                    empleados.add(emp);
                } while (cursor.moveToNext());
            }
        }
        if (mAdapter != null)
        {
            mAdapter = new MyEmployeeRecyclerViewAdapter(empleados,mListener);
            recyclerView.setAdapter(mAdapter);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Employee item);
    }
}
