package com.example.projectandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.projectandroid.DataModel.Car;
import com.example.projectandroid.DataModel.Employee;
import com.example.projectandroid.DataModel.Manager;
import com.example.projectandroid.DataModel.Motorcycle;
import com.example.projectandroid.DataModel.Programmer;
import com.example.projectandroid.DataModel.Tester;
import com.example.projectandroid.DataModel.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;


public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DATABASE_OPERATION";

    private static final String DATABASE_NAME = "Employee_db";
    private static final int DATABASE_VERSION =1;
    public static final String EMPLOYEE_TABLE = "employees";
    public static final  String VEHICLE_TABLE = "vehicles";
    //table column names
    public static final String COL_ID = "ID", COL_NAME ="Name",
        AGE ="Age", BIRTH_YEAR = "bYear", RATE ="Rate", SALARY ="Salary", COL_TYPE="Type"
            ,COL_TYPE_VAl="Type_Value";

    //column names related to vehicle
    public static final  String COL_MAKE ="Make", COL_PLATE ="Plate",
    COLOR = "Color", CATEGORY ="Category";



    public DBHelper(Context context){//constructor

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table is only called the first time once the table is created it is not called
        sqLiteDatabase.execSQL("CREATE TABLE "+EMPLOYEE_TABLE+" (ID integer NOT NULL PRIMARY KEY," +
                "Name varchar(110) NOT NULL," +
                "Age integer," +
                "bYear integer," +
                "Rate real," +
                "Salary real," +
                "Type varchar(10)," +
                "Type_Value integer);");

        sqLiteDatabase.execSQL("CREATE table "+VEHICLE_TABLE+" (ID integer NOT NULL PRIMARY KEY," +
                "Make varchar(50)," +
                "Plate varchar(50)," +
                "Color varchar(10)," +
                "Category varchar(10)," +
                "Type_Value varchar(50));");

        Log.e(TAG,"Table Created");
    }



    public void saveData(Employee emp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,emp.getID());
        contentValues.put(COL_NAME,emp.getName());
        contentValues.put(AGE,emp.getAge());
        contentValues.put(BIRTH_YEAR,emp.getBirthYear());
        contentValues.put(RATE,emp.getRate());
        contentValues.put(SALARY,emp.getMonthlySalary());

        if(emp instanceof Manager) {
            contentValues.put(COL_TYPE, "manager");
            contentValues.put(COL_TYPE_VAl,((Manager)emp).getNbClients());
        }
        else if(emp instanceof Tester) {
            contentValues.put(COL_TYPE, "tester");
            contentValues.put(COL_TYPE_VAl,((Tester)emp).getNbBugs());
        }
        else{
            contentValues.put(COL_TYPE,"programmer");
            contentValues.put(COL_TYPE_VAl,((Programmer)emp).getNbProject());
        }

        db.insert(EMPLOYEE_TABLE,null,contentValues);


        //build the vehicle
        ContentValues vhc = new ContentValues();
        vhc.put(COL_ID,emp.getID());
        vhc.put(COL_MAKE, emp.getVehicle().getMake());
        vhc.put(COL_PLATE, emp.getVehicle().getPlate());
        vhc.put(COLOR, emp.getVehicle().getColor());
        vhc.put(CATEGORY, emp.getVehicle().getCategory());

        if(emp.getVehicle().getCategory().equals("car")){
            vhc.put(COL_TYPE_VAl,((Car)emp.getVehicle()).getType());
        }
        else
            vhc.put(COL_TYPE_VAl,((Motorcycle)emp.getVehicle()).isSidecar()?"1":"0");

        db.insert(VEHICLE_TABLE, null , vhc);
        Log.e(TAG,"One row is inserted");
    }

    public ArrayList<Employee> getAllEmployee(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.query(EMPLOYEE_TABLE,null,null,
                null,null,null,null);

        ArrayList<Employee> employees = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            do
            {
                //build the employee object;
                int i=cursor.getColumnIndex(COL_TYPE);
                String employeeType = cursor.getString(i);

                Employee emp= null;
                if(employeeType.equals("manager"))
                    emp = new Manager(cursor.getInt(7));
                else if(employeeType.equals("tester"))
                    emp = new Tester(cursor.getInt(7));
                else emp = new Programmer(cursor.getInt(7));

                emp.setID(cursor.getInt(0));
                emp.setName(cursor.getString(1));
                emp.setAge(cursor.getInt(2));
                emp.setBirthYear(cursor.getInt(3));
                emp.setRate(cursor.getDouble(4));
                emp.setMonthlySalary(cursor.getDouble(5));

                Cursor vCursor = db.query(VEHICLE_TABLE,null,
                    COL_ID+"=?",new String[]{String.valueOf(emp.getID())},
                        null,null,null); // get the vehicle with specific ID

                vCursor.moveToFirst();

                Vehicle vehicle=null;

                if(vCursor.getString(4).equals("car")){
                    vehicle = new Car(vCursor.getString(5));
                }
                else
                    vehicle = new Motorcycle(vCursor.getString(5).equals("1"));

                vehicle.setMake(vCursor.getString(1));
                vehicle.setPlate(vCursor.getString(2));
                vehicle.setColor(vCursor.getString(3));

                emp.setVehicle(vehicle);

                employees.add(emp);
            }while (cursor.moveToNext());
        }

        return employees;

    }

    public boolean isIDAvailable(long ID){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(EMPLOYEE_TABLE,new String[]{COL_ID},COL_ID+"=?",
                new String[]{String.valueOf(ID)},null,null,null);

        return cursor.getCount()<=0;
    }





    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

