package com.lasay.com243.studinfosystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentInfoActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        EditText etStudentNo = findViewById(R.id.etStudentNo);
        EditText etLastName = findViewById(R.id.etLastName);
        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etMiddleName = findViewById(R.id.etMiddleName);
        EditText etProgram = findViewById(R.id.etProgram);
        EditText etYearLevel = findViewById(R.id.etYearLevel);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btnLogout = findViewById(R.id.btnLogout);
        TextView tvOutput = findViewById(R.id.tvOutput);

        tvOutput.setText(dbHelper.getAllStudents());

        btnSubmit.setOnClickListener(v -> {
            String studentNo = etStudentNo.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String firstName = etFirstName.getText().toString().trim();
            String middleName = etMiddleName.getText().toString().trim();
            String program = etProgram.getText().toString().trim();
            String yearLevel = etYearLevel.getText().toString().trim();

            if (studentNo.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || program.isEmpty() || yearLevel.isEmpty()) {
                Toast.makeText(this, "Fill in all required student fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertStudent(studentNo, lastName, firstName, middleName, program, yearLevel);

            if (inserted) {
                Toast.makeText(this, "Student record saved!", Toast.LENGTH_SHORT).show();
                etStudentNo.setText("");
                etLastName.setText("");
                etFirstName.setText("");
                etMiddleName.setText("");
                etProgram.setText("");
                etYearLevel.setText("");
                tvOutput.setText(dbHelper.getAllStudents());
            } else {
                Toast.makeText(this, "Failed to save (Student No. may already exist)", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentInfoActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}