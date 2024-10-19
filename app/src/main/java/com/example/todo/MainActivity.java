package com.example.todo;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TodoDatabaseHelper dbHelper;
    private LinearLayout todoListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TodoDatabaseHelper(this);
        todoListLayout = findViewById(R.id.todoListLayout);
        EditText todoInput = findViewById(R.id.todoInput);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = todoInput.getText().toString();
                boolean urgent = false /* determine urgency, e.g., from a checkbox or switch */;
                dbHelper.addTodo(text, urgent);
                displayTodos();
                todoInput.setText(""); // Clear input
            }
        });

        displayTodos(); // Initial display of todos
    }

    private void displayTodos() {
        todoListLayout.removeAllViews(); // Clear previous views
        Cursor cursor = dbHelper.getAllTodos();
        if (cursor != null && cursor.moveToFirst()) {
            int textColumnIndex = cursor.getColumnIndex(TodoDatabaseHelper.COLUMN_TEXT);
            int urgentColumnIndex = cursor.getColumnIndex(TodoDatabaseHelper.COLUMN_URGENT);
            
            if (textColumnIndex != -1 && urgentColumnIndex != -1) {
                do {
                    String text = cursor.getString(textColumnIndex);
                    boolean urgent = cursor.getInt(urgentColumnIndex) == 1;
    
                    TextView todoTextView = new TextView(this);
                    todoTextView.setText(text);
                    if (urgent) {
                        todoTextView.setTextColor(Color.RED);
                    } else {
                        todoTextView.setTextColor(Color.BLACK);
                    }
                    todoListLayout.addView(todoTextView);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Always close the cursor
        }
    }
}
