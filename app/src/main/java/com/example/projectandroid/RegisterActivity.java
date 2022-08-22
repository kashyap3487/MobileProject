package com.example.projectandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.projectandroid.DataModel.Car;
import com.example.projectandroid.DataModel.Employee;
import com.example.projectandroid.DataModel.Manager;
import com.example.projectandroid.DataModel.Motorcycle;
import com.example.projectandroid.DataModel.Programmer;
import com.example.projectandroid.DataModel.Tester;
import com.example.projectandroid.DataModel.Vehicle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    public static String[] EMPLOYEE_TYPES = {"Choose a Type", "Manager", "Tester", "Programmer"};
    public static String[] COLORS = {"Choose a Color", "Red", "Green", "Yellow",
            "Blue", "White", "Black", "Purple", "Pink", "Brown", "Orange"};

    private AppCompatButton reg_button;
    private RadioButton car, motorBike, yes, no;
    private Spinner color, eType;
    private ArrayAdapter<String> adapterColor, adapterEtype;

    private TextInputEditText fname, lname, bdate, salary, occuRate, empID,
            employeeExtra, vhModel, carType, plateNo;

    private TextInputLayout fnameLay, lnameLay, bdateLay, salaryLay, occuRateLay, empIDLay,
            employeeExtraLay, vhModelLay, plateNoLay, carTypeLay;

    private DBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        helper = new DBHelper(this);
        reg_button = findViewById(R.id.add_to_db);
        reg_button.setOnClickListener(this);

        car = findViewById(R.id.car_rad);
        motorBike = findViewById(R.id.motor_rad);
        yes = findViewById(R.id.yes_rad);
        no = findViewById(R.id.no_rad);

        car.setOnCheckedChangeListener(this);
        motorBike.setOnCheckedChangeListener(this);
        yes.setOnCheckedChangeListener(this);
        no.setOnCheckedChangeListener(this);


        eType = findViewById(R.id.etype_spin);
        color = findViewById(R.id.vh_color_spin);
        adapterColor = new ArrayAdapter<String>(this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, COLORS);


        adapterEtype = new ArrayAdapter<String>(this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                EMPLOYEE_TYPES);
        eType.setAdapter(adapterEtype);
        color.setAdapter(adapterColor);

        eType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //manager
                if (i == 1) {
                    findViewById(R.id.employee_optional_lay).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.employee_optional_tv)).setText("# clients");
                } else if (i == 2) {
                    findViewById(R.id.employee_optional_lay).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.employee_optional_tv)).setText("# bugs");
                } else if (i == 3) {
                    findViewById(R.id.employee_optional_lay).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.employee_optional_tv)).setText("# projects");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //the layouts
        fname = findViewById(R.id.fname_edtx);
        fnameLay = findViewById(R.id.fname_edtx_lay);

        lname = findViewById(R.id.lname_edtx);
        lnameLay = findViewById(R.id.lname_edtx_lay);

        bdate = findViewById(R.id.bdate_edtx);
        bdateLay = findViewById(R.id.bdate_edtx_lay);

        salary = findViewById(R.id.salary_edtx);
        salaryLay = findViewById(R.id.salary_edtx_lay);

        occuRate = findViewById(R.id.occu_edtx);
        occuRateLay = findViewById(R.id.occu_edtx_lay);

        empID = findViewById(R.id.eid_edtx);
        empIDLay = findViewById(R.id.eid_edtx_lay);

        employeeExtra = findViewById(R.id.employee_optional_edtx);
        employeeExtraLay = findViewById(R.id.e_optional_edtx_lay);

        vhModel = findViewById(R.id.vh_model_edtx);
        vhModelLay = findViewById(R.id.vh_model_edtx_lay);

        plateNo = findViewById(R.id.plate_edtx);
        plateNoLay = findViewById(R.id.plate_edtx_lay);

        carType = findViewById(R.id.car_type_edtx);
        carTypeLay = findViewById(R.id.car_type_edtx_lay);
    }

    //checks whether the string can be parsed within integer limit
    private boolean isInteger(String s){

        try {
            int i = Integer.parseInt(s);
            return  true;
        }
        catch (Exception e){
            return  false;
        }
    }

    @Override
    public void onClick(View view) {
        //remove all previous errors
        fnameLay.setError(null);
        lnameLay.setError(null);
        bdateLay.setError(null);
        salaryLay.setError(null);
        occuRateLay.setError(null);
        empIDLay.setError(null);
        employeeExtraLay.setError(null);
        vhModelLay.setError(null);
        plateNoLay.setError(null);
        carTypeLay.setError(null);



        //input validation
        if (fname.getText() == null || fname.getText().toString().isEmpty()) {
            fnameLay.setError("This field can't be empty");
            return;
        }
        else if(fname.getText().toString().length()>50){
            fnameLay.setError("Maximum 50 characters");
            return;
        }

        if (lname.getText() == null || lname.getText().toString().isEmpty()) {
            lnameLay.setError("This field can't be empty");
            return;
        }
        else if(lname.getText().toString().length()>50){
            lnameLay.setError("Maximum 50 characters");
            return;
        }

        Date bDate=null;
        if (bdate.getText() == null || bdate.getText().toString().isEmpty()) {
            bdateLay.setError("This field can't be empty");
            return;
        } else {
            String date = bdate.getText().toString();
            //validate date
            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
            sdfrmt.setLenient(false);

            try {
                bDate = sdfrmt.parse(date);
                //the date has to be at least
                if (bDate.getTime() + 18L * 3600 * 24 * 356 * 1000 > System.currentTimeMillis()) {
                    bdateLay.setError("Your age should be at least 18!");
                    return;
                }
            } catch (ParseException e) {
                bdateLay.setError("Invalid date");
                return;
            }

        }

        if (salary.getText() == null || salary.getText().toString().isEmpty()) {
            salaryLay.setError("This field can't be empty");
            return;
        }
        if (occuRate.getText() == null || occuRate.getText().toString().isEmpty()) {
            occuRateLay.setError("This field can't be empty");
            return;
        }

        if (empID.getText() == null || empID.getText().toString().isEmpty()) {
            empIDLay.setError("This field can't be empty");
            return;
        }
        else if(!isInteger(empID.getText().toString())){
            empIDLay.setError("Too big number");
            return;
        }

        if (eType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Employee type", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (employeeExtra.getText() == null || employeeExtra.getText().toString().isEmpty()) {
            employeeExtra.setError("This field can't be empty");
            return;
        }
        else  if(!isInteger(employeeExtra.getText().toString())){
            employeeExtraLay.setError("Number too big");
            return;
        }

        if (!car.isChecked() && !motorBike.isChecked()) {
            Toast.makeText(this, "Please Select Vehicle Type", Toast.LENGTH_LONG).show();
            return;
        }

        if (motorBike.isChecked() && (!yes.isChecked() && !no.isChecked())) {
            Toast.makeText(this, "Please Select Side Car", Toast.LENGTH_LONG).show();
            return;
        }

        if (car.isChecked() && (carType.getText() == null || carType.getText().toString().isEmpty())) {
            carTypeLay.setError("This field can't be empty");
            return;
        }
        else  if(car.isChecked() && carType.getText().toString().length()>50){
            carTypeLay.setError("Maximum 50 characters");
            return;
        }

        if (vhModel.getText() == null || vhModel.getText().toString().isEmpty()) {
            vhModelLay.setError("This field can't be empty");
            return;
        }
        else if(vhModel.getText().toString().length()>50){
            vhModelLay.setError("Maximum 50 character");
            return;
        }

        if (plateNo.getText() == null || plateNo.getText().toString().isEmpty()) {
            plateNoLay.setError("This field can't be empty");
            return;
        }
        else if(plateNo.getText().toString().length()>50){
            plateNoLay.setError("Maximum 50 characters");
            return;
        }


        if (color.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Vehicle Color", Toast.LENGTH_LONG).show();
            return;
        }

        //build the data if everything is ok
        Employee emp=null;
        Vehicle  vh = null;



        if(eType.getSelectedItemPosition()==1)
            emp=new Manager(employeeExtra.getText().toString());
        else if(eType.getSelectedItemPosition()==2)
            emp =new Tester(employeeExtra.getText().toString());
        else
            emp = new Programmer(employeeExtra.getText().toString());

        if(car.isChecked())
            vh= new Car(carType.getText().toString());
        else
            vh = new Motorcycle(yes.isChecked());


        emp.setName(fname.getText().toString().trim() +" "+ lname.getText().toString().trim());

        Calendar c=Calendar.getInstance();
        c.setTime(bDate);
        emp.setBirthYear(c.get(Calendar.YEAR));
        long age =System.currentTimeMillis() - bDate.getTime();
        age = age/(3600L*24*1000*365);
        emp.setAge((int)age);

        emp.setMonthlySalary(Double.parseDouble(salary.getText().toString()));
        emp.setRate(Double.parseDouble(occuRate.getText().toString()));
        emp.setID(Long.parseLong(empID.getText().toString()));

        //check if the ID is already taken
        if(!helper.isIDAvailable(emp.getID())){
            empIDLay.setError("This ID is already taken");
            return;
        }


        vh.setMake(vhModel.getText().toString());
        vh.setPlate(plateNo.getText().toString());
        vh.setColor(COLORS[color.getSelectedItemPosition()]);


        emp.setVehicle(vh);

        Log.d("REGISTER",emp.toDisplay());
        helper.saveData(emp);

        Toast.makeText(this, "Saved To Database",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (b) {
            if (compoundButton.getId() == R.id.car_rad) {
                findViewById(R.id.motor_optional_lay).setVisibility(View.GONE);
                findViewById(R.id.car_optional_lay).setVisibility(View.VISIBLE);
            } else if (compoundButton.getId() == R.id.motor_rad) {
                findViewById(R.id.motor_optional_lay).setVisibility(View.VISIBLE);
                findViewById(R.id.car_optional_lay).setVisibility(View.GONE);
            }
        }
    }
}

