package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private Switch urgentSwitch;
    private Button addButton;
    private ArrayList<String> todoList;
    private com.example.todo.TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        listView = findViewById(R.id.todo_list);
        editText = findViewById(R.id.edit_text);
        urgentSwitch = findViewById(R.id.urgent_switch);
        addButton = findViewById(R.id.add_button);

        // Initialize the todo list and adapter
        todoList = new ArrayList<>();
        todoAdapter = new com.example.todo.TodoAdapter(this, todoList);
        listView.setAdapter(todoAdapter);

        // Set the click listener for the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTodo = editText.getText().toString().trim();
                if (!newTodo.isEmpty()) {
                    if (urgentSwitch.isChecked()) {
                        newTodo = "URGENT: " + newTodo;
                    }
                    todoList.add(newTodo);
                    todoAdapter.notifyDataSetChanged();
                    editText.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
