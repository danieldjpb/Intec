package com.dragon.intec.objects;/*
 * Created by HOME on 8/25/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Student implements Parcelable{

    private String token;
    private String secret;
    private static final String keyToken = "TOKEN";
    private static final String keySecret = "SECRETE";
    private static final String keyObject = "STUDENT";
    private Activity activity;

    private String id;
    private String name;
    private String program;
    private String academicCondition;
    private String quarter;
    private String lastCondition;
    private double quarterIndex;
    private double generalIndex;
    private int validatedCredits;
    private int approvedCredits;
    private int approvedQuarters;
    private String[] alerts;
    private Signatures signatures;

    protected Student(Parcel in) {
        token = in.readString();
        secret = in.readString();
        id = in.readString();
        name = in.readString();
        program = in.readString();
        academicCondition = in.readString();
        quarter = in.readString();
        lastCondition = in.readString();
        quarterIndex = in.readDouble();
        generalIndex = in.readDouble();
        validatedCredits = in.readInt();
        approvedCredits = in.readInt();
        approvedQuarters = in.readInt();
        alerts = in.createStringArray();
        signatures = in.readParcelable(Signatures.class.getClassLoader());
        internetConnection = in.readByte() != 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public Signatures getSignatures() {
        return signatures;
    }

     private Student setSignatures(Signatures signatures) {
        this.signatures = signatures;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProgram() {
        return program;
    }

    public String getAcademicCondition() {
        return academicCondition;
    }

    public String getQuarter() {
        return quarter;
    }

    public String getLastCondition() {
        return lastCondition;
    }

    public double getQuarterIndex() {
        return quarterIndex;
    }

    public double getGeneralIndex() {
        return generalIndex;
    }

    public int getValidatedCredits() {
        return validatedCredits;
    }

    public int getApprovedCredits() {
        return approvedCredits;
    }

    public int getApprovedQuarters() {
        return approvedQuarters;
    }

    public String[] getAlerts() {
        return alerts;
    }

    private Student setAlerts(String[] alerts) {
        this.alerts = alerts;
        return this;
    }

    private Student setApprovedQuarters(int approvedQuarters) {
        this.approvedQuarters = approvedQuarters;
        return this;
    }

    private Student setApprovedCredits(int approvedCredits) {
        this.approvedCredits = approvedCredits;
        return this;
    }

    private Student setValidatedCredits(int validatedCredits) {
        this.validatedCredits = validatedCredits;
        return this;
    }

    private Student setGeneralIndex(double generalIndex) {
        this.generalIndex = generalIndex;
        return this;
    }

    private Student setProgram(String program) {
        this.program = program;
        return this;
    }

    public Student setQuarterIndex(double quarterIndex) {
        this.quarterIndex = quarterIndex;
        return this;
    }

    public Student setLastCondition(String lastCondition) {
        this.lastCondition = lastCondition;
        return this;
    }

    public Student setQuarter(String quarter) {
        this.quarter = quarter;
        return this;
    }

    public Student setAcademicCondition(String academic_condition) {
        this.academicCondition = academic_condition;
        return this;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Student setId(String id) {
        this.id = id;
        return this;
    }

    public boolean internetConnection = false;

    public Student(String token, String secret, Activity activity){
        this.token = token;
        this.secret = secret;
        this.activity = activity;

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(keyToken, token);
        editor.putString(keySecret, secret);

        editor.apply();
    }

    public void getData() throws IOException, JSONException {

        if(!internetConnection){

            saveToJSONTEST(activity);

            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            String jsonOBJ = sharedPref.getString(keyObject, "");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonOBJ);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            assert jsonObject != null;
            setId(jsonObject.optString("id"));
            setName(jsonObject.optString("name"));
            setProgram(jsonObject.optString("program"));
            setAcademicCondition(jsonObject.optString("academic_condition"));
            setQuarter(jsonObject.optString("quarter"));
            setLastCondition(jsonObject.optString("last_condition"));
            setQuarterIndex(Double.parseDouble(jsonObject.optString("quarter_index")));
            setGeneralIndex(Double.parseDouble(jsonObject.optString("general_index")));
            setValidatedCredits(Integer.parseInt(jsonObject.optString("validated_credits")));
            setApprovedCredits(Integer.parseInt(jsonObject.optString("approved_credits")));
            setApprovedQuarters(Integer.parseInt(jsonObject.optString("approved_quarters")));

            ArrayList<String> vals = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("alerts");
            for (int i = 0; i < jsonArray.length(); i++){
                vals.add(jsonArray.getString(i));
            }
            setAlerts(vals.toArray(new String[vals.size()]));

            JSONArray jsonArraySignatures = jsonObject.getJSONArray("signatures");
            String[][] signatures = new String[jsonArraySignatures.length()][12];
            for (int i = 0; i < jsonArraySignatures.length(); i++){
                for (int j = 0; j < 12; j++){
                    signatures[i][j] = jsonArraySignatures.getJSONArray(i).getString(j);
                }
            }
            setSignatures(new Signatures(signatures));
            Log.i("USER_signatures", signatures[0][0]);


        }else{
            //Testing
            URL url = new URL("http://coolsite.com/coolstuff.js");
            InputStream in = url.openStream();
            InputStreamReader reader = new InputStreamReader(in);
            //Get json from server parse it and add it to the list
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("-----------");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            assert jsonObject != null;
            setId(jsonObject.optString("id"));
            setName(jsonObject.optString("name"));
            setProgram(jsonObject.optString("program"));
            setAcademicCondition(jsonObject.optString("academic_condition"));
            setQuarter(jsonObject.optString("quarter"));
            setLastCondition(jsonObject.optString("last_condition"));
            setQuarterIndex(Double.parseDouble(jsonObject.optString("quarter_index")));
            setGeneralIndex(Double.parseDouble(jsonObject.optString("general_index")));
            setValidatedCredits(Integer.parseInt(jsonObject.optString("validated_credits")));
            setApprovedCredits(Integer.parseInt(jsonObject.optString("approved_credits")));
            setApprovedQuarters(Integer.parseInt(jsonObject.optString("approved_quarters")));

            ArrayList<String> vals = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("alerts");
            for (int i = 0; i < jsonArray.length(); i++){
                vals.add(jsonArray.getString(i));
            }
            setAlerts(vals.toArray(new String[vals.size()]));

            JSONArray jsonArraySignatures = jsonObject.getJSONArray("signatures");
            String[][] signatures = new String[jsonArraySignatures.length()][12];
            for (int i = 0; i < jsonArraySignatures.length(); i++){
                for (int j = 0; j < 12; j++){
                    signatures[i][j] = jsonArraySignatures.getJSONArray(i).getString(j);
                    Log.i("USER_signatures", signatures[i][j]);
                }
            }
            setSignatures(new Signatures(signatures));

            //Then save it
            saveToJSON(activity);
        }
    }

    public void saveToJSON(Activity activity){

        String jsonOBJ = "";
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("program", getProgram());
            jsonObject.put("academic_condition", getAcademicCondition());
            jsonObject.put("quarter", getQuarter());
            jsonObject.put("last_condition", getLastCondition());
            jsonObject.put("quarter_index", getQuarterIndex());
            jsonObject.put("general_index", getGeneralIndex());
            jsonObject.put("validated_credits", getValidatedCredits());
            jsonObject.put("approved_credits", getApprovedCredits());
            jsonObject.put("approved_quarters", getApprovedQuarters());

            String[] vals = getAlerts();

            JSONArray jsonArray = new JSONArray();
            for (String val : vals) {
                jsonArray.put(val);
            }

            jsonObject.put("alerts", getAlerts());

            jsonOBJ = jsonObject.toString();
            Log.i("USER_json", jsonOBJ);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(keyObject, jsonOBJ);
        editor.apply();
    }

    private void saveToJSONTEST(Activity activity){

        String jsonOBJ = "";
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", "1065948");
            jsonObject.put("name", "Hector Andres Acosta Pozo");
            jsonObject.put("program", "(IDS 2015) INGENIERIA DE SOFTWARE");
            jsonObject.put("academic_condition", "NORMAL");
            jsonObject.put("quarter", "AGOSTO - OCTUBRE");
            jsonObject.put("last_condition", "MAYO - JULIO");
            jsonObject.put("quarter_index", "4.00");
            jsonObject.put("general_index", "3.83");
            jsonObject.put("validated_credits", "0");
            jsonObject.put("approved_credits", "23");
            jsonObject.put("approved_quarters", "4");

            String[] vals = {"Hola", "Que tal??"};

            JSONArray jsonArray = new JSONArray();
            for (String val : vals) {
                jsonArray.put(val);
            }

            jsonObject.put("alerts", jsonArray);

            JSONArray jsonArray1 = new JSONArray();
            JSONArray jsonArray2 = new JSONArray();

            jsonArray1.put("CBF201");
            jsonArray1.put("PROBABILIDAD Y ESTADISTICA");
            jsonArray1.put("02");
            jsonArray1.put("AJ404");
            jsonArray1.put("09/11");
            jsonArray1.put("");
            jsonArray1.put("09/11");
            jsonArray1.put("");
            jsonArray1.put("14/16");
            jsonArray1.put("A");
            jsonArray1.put("JOSE ANTONIO SCOTT GUILLEARD DEL CARMEN SANTO ALFONSO");
            jsonArray1.put(true);

            jsonArray2.put(jsonArray1);
            jsonArray2.put(jsonArray1);
            jsonArray2.put(jsonArray1);
            jsonArray2.put(jsonArray1);

            jsonObject.put("signatures", jsonArray2);

            jsonOBJ = jsonObject.toString();
            Log.i("USER_json", jsonOBJ);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(keyObject, jsonOBJ);
        editor.apply();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(token);
        parcel.writeString(secret);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(program);
        parcel.writeString(academicCondition);
        parcel.writeString(quarter);
        parcel.writeString(lastCondition);
        parcel.writeDouble(quarterIndex);
        parcel.writeDouble(generalIndex);
        parcel.writeInt(validatedCredits);
        parcel.writeInt(approvedCredits);
        parcel.writeInt(approvedQuarters);
        parcel.writeStringArray(alerts);
        parcel.writeParcelable(signatures, i);
        parcel.writeByte((byte) (internetConnection ? 1 : 0));
    }
}
