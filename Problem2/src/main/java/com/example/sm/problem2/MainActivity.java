package com.example.sm.problem2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyBaseAdapter adapter;
    ListView listview;
    ArrayList<Employee> emp_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // need something here
        emp_list = new ArrayList<>();
        emp_list.add(new Employee("Em01", 21, 10000));

        adapter = new MyBaseAdapter(this, emp_list);
        listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((AdapterView.OnItemClickListener)adapter);
    }
    @Override
    public void onClick(View v){
        EditText edit_name = (EditText) findViewById(R.id.edit_name);
        EditText edit_age = (EditText) findViewById(R.id.edit_age);
        EditText edit_salary = (EditText) findViewById(R.id.edit_salary);

        Employee employee;
        int index = 0;
        switch (v.getId()){

            case R.id.btn_inc:
                int money1 = Integer.parseInt(edit_salary.getText().toString());
                edit_salary.setText((money1 + 10000) + "");
                emp_list.get(index).increase();
                break;

            case R.id.btn_dec:
                int money2 = Integer.parseInt(edit_salary.getText().toString());
                edit_salary.setText((money2 - 10000) + "");
                emp_list.get(index).decrease();
                break;

            case R.id.btn_store:
                emp_list.add(new Employee(edit_name.getText().toString(), Integer.parseInt(edit_age.getText().toString()), Integer.parseInt(edit_salary.getText().toString())));
                break;

            case R.id.btn_modify:
                emp_list.set(index, new Employee(edit_name.getText().toString(), Integer.parseInt(edit_age.getText().toString()), Integer.parseInt(edit_salary.getText().toString())));
                break;

            case R.id.btn_delete:
                emp_list.remove(index);
                break;
        }
    }
}

interface Payment {
    void increase();
    void decrease();
}
