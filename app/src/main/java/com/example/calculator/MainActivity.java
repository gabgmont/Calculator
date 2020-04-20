package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private String pendingOperation = "=";

    private static final String STORED_OPERATION = "PendingOperation";
    private static final String STORED_OP1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonNeg = findViewById(R.id.buttonNeg);
        Button buttonClear = findViewById(R.id.buttonClear);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();

                if (op.equals("Neg")) {
                    if (value.length() != 0) {
                        Double number = Double.valueOf(value);
                        number *= -1;
                        newNumber.setText(number.toString());
                    } else {
                        newNumber.setText("-");
                    }
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        performOperation(doubleValue, op);
                    } catch (NumberFormatException e) {
                        newNumber.setText("");
                    }
                    pendingOperation = op;
                    displayOperation.setText(pendingOperation);
                }
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonNeg.setOnClickListener(opListener);

        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
                newNumber.setText("");
                displayOperation.setText("");
                operand1 = 0.0;
                pendingOperation = "=";
            }
        };
        buttonClear.setOnClickListener(clearListener);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STORED_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STORED_OP1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STORED_OPERATION);
        operand1 = savedInstanceState.getDouble(STORED_OP1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    if(operand1 == 0){
                        operand1 = value;
                        break;
                    }else {
                        operand1 *= value;
                        break;
                    }
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    if(operand1 == 0){
                        operand1 = value;
                        break;
                    }else {
                        operand1 -= value;
                        break;
                    }
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");
    }
}