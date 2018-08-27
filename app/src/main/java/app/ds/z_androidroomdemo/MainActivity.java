package app.ds.z_androidroomdemo;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.ds.database.AppDatabase;
import app.ds.model.Person;
import app.ds.utils.CommonFunctions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_home_list;
    private EditText et_home_name, et_home_mobile, et_home_email, et_home_address;
    private Button btn_home_submit;

    private AppDatabase db;
    private Executor executor;

    private List<Person> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "people-db").allowMainThreadQueries().build();
        executor = Executors.newSingleThreadExecutor();

        ll_home_list = (LinearLayout) findViewById(R.id.ll_home_list);
        et_home_name = (EditText) findViewById(R.id.et_home_name);
        et_home_mobile = (EditText) findViewById(R.id.et_home_mobile);
        et_home_email = (EditText) findViewById(R.id.et_home_email);
        et_home_address = (EditText) findViewById(R.id.et_home_address);

        btn_home_submit = (Button) findViewById(R.id.btn_home_submit);
        btn_home_submit.setOnClickListener(this);

        getAllData();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_home_submit) {
            if (isValidated()) {
                final Person person = new Person();
                person.setName(et_home_name.getText().toString());
                person.setMobile(et_home_mobile.getText().toString());
                person.setEmail(et_home_email.getText().toString());
                person.setAddress(et_home_address.getText().toString());

                executor.execute(new Runnable() {
                    public void run() {
                        db.getPersonDao().insertAll(person);
                        getAllData();
                    }
                });
            }
        }
    }

    private boolean isValidated() {
        boolean isValidated = true;
        if (et_home_name.getText().toString().length() == 0) {
            isValidated = false;
            CommonFunctions.showToast(MainActivity.this, "Please enter name");

        } else if (et_home_mobile.getText().toString().length() == 0) {
            isValidated = false;
            CommonFunctions.showToast(MainActivity.this, "Please enter mobile");

        } else if (et_home_email.getText().toString().length() == 0) {
            isValidated = false;
            CommonFunctions.showToast(MainActivity.this, "Please enter email");

        } else if (et_home_address.getText().toString().length() == 0) {
            isValidated = false;
            CommonFunctions.showToast(MainActivity.this, "Please enter address");

        }
        return isValidated;
    }

    private void getAllData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ll_home_list.removeAllViews();
                    }
                });
                users.clear();
            }

            @Override
            protected Void doInBackground(Void... params) {
                users = db.getPersonDao().getAllPeople();
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                setData();
            }
        }.execute();
    }

    private void setData() {
        for (int i = 0; i < users.size(); i++) {
            final Person person = users.get(i);
            final View view = getLayoutInflater().inflate(R.layout.row_person, null, false);

            TextView tv_row_home_name = (TextView) view.findViewById(R.id.tv_row_home_name);
            TextView tv_row_home_mobile = (TextView) view.findViewById(R.id.tv_row_home_mobile);
            TextView tv_row_home_email = (TextView) view.findViewById(R.id.tv_row_home_email);
            TextView tv_row_home_address = (TextView) view.findViewById(R.id.tv_row_home_address);
            ImageView iv_row_delete = (ImageView) view.findViewById(R.id.iv_row_delete);

            tv_row_home_name.setText(person.getName());
            tv_row_home_mobile.setText(person.getMobile());
            tv_row_home_email.setText(person.getEmail());
            tv_row_home_address.setText(person.getAddress());

            iv_row_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.getPersonDao().delete(person);
                    getAllData();
                }
            });

            ll_home_list.addView(view);
        }
    }

    /*private void getAllData() {
        executor.execute(new Runnable() {
            public void run() {
                List<Person> everyone = db.getPersonDao().getAllPeople();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ll_home_list.removeAllViews();
                    }
                });

                for (int i = 0; i < everyone.size(); i++) {
                    Person person = everyone.get(i);
                    final View view = getLayoutInflater().inflate(R.layout.row_person, null, false);

                    TextView tv_row_home_name = (TextView) view.findViewById(R.id.tv_row_home_name);
                    TextView tv_row_home_mobile = (TextView) view.findViewById(R.id.tv_row_home_mobile);
                    TextView tv_row_home_email = (TextView) view.findViewById(R.id.tv_row_home_email);
                    TextView tv_row_home_address = (TextView) view.findViewById(R.id.tv_row_home_address);

                    tv_row_home_name.setText(person.getName());
                    tv_row_home_mobile.setText(person.getMobile());
                    tv_row_home_email.setText(person.getEmail());
                    tv_row_home_address.setText(person.getAddress());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll_home_list.addView(view);
                        }
                    });
                }
            }
        });
    }*/
}
