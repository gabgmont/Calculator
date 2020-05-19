package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    private TextView newNumber;
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

        TextView button0 = findViewById(R.id.button0);
        TextView button1 = findViewById(R.id.button1);
        TextView button2 = findViewById(R.id.button2);
        TextView button3 = findViewById(R.id.button3);
        TextView button4 = findViewById(R.id.button4);
        TextView button5 = findViewById(R.id.button5);
        TextView button6 = findViewById(R.id.button6);
        TextView button7 = findViewById(R.id.button7);
        TextView button8 = findViewById(R.id.button8);
        TextView button9 = findViewById(R.id.button9);
        TextView buttonDot = findViewById(R.id.buttonDot);

        TextView buttonEquals = findViewById(R.id.buttonEquals);
        TextView buttonDivide = findViewById(R.id.buttonDivide);
        TextView buttonMultiply = findViewById(R.id.buttonMultiply);
        TextView buttonMinus = findViewById(R.id.buttonMinus);
        TextView buttonPlus = findViewById(R.id.buttonPlus);
        TextView buttonNeg = findViewById(R.id.buttonNeg);
        TextView buttonClear = findViewById(R.id.buttonClear);
        TextView buttonDelete = findViewById(R.id.buttonDelete);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView b = (TextView) v;
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
                TextView b = (TextView) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();

                if (op.equals("-1")) {
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

    public void deleteButton(View view) {
        if(newNumber.getText() != null) {
            try{newNumber.setText(newNumber.getText().toString().substring(0, newNumber.length() - 1));
            }catch (Exception e){
            }
        }
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