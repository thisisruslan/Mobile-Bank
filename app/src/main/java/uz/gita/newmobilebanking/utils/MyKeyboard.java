package uz.gita.newmobilebanking.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import uz.gita.newmobilebanking.R;

public class MyKeyboard extends ConstraintLayout implements View.OnClickListener {

    // constructors
    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // keyboard keys (buttons)
    private TextView mButton1;
    private TextView mButton2;
    private TextView mButton3;
    private TextView mButton4;
    private TextView mButton5;
    private TextView mButton6;
    private TextView mButton7;
    private TextView mButton8;
    private TextView mButton9;
    private TextView mButton0;
    private ImageView mButtonDelete;
    private ImageView mButtonEnter;

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;

    private void init(Context context, AttributeSet attrs) {

        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.my_keyboard, this, true);
        mButton1 = (TextView) findViewById(R.id.btOne);
        mButton2 = (TextView) findViewById(R.id.btTwo);
        mButton3 = (TextView) findViewById(R.id.btThree);
        mButton4 = (TextView) findViewById(R.id.btFour);
        mButton5 = (TextView) findViewById(R.id.btFive);
        mButton6 = (TextView) findViewById(R.id.btSix);
        mButton7 = (TextView) findViewById(R.id.btSeven);
        mButton8 = (TextView) findViewById(R.id.btEight);
        mButton9 = (TextView) findViewById(R.id.btNine);
        mButton0 = (TextView) findViewById(R.id.btZero);
        mButtonDelete = (ImageView) findViewById(R.id.btBackspace);
        mButtonEnter = (ImageView) findViewById(R.id.btFingerPrint);

        // set button click listeners
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton0.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        mButtonEnter.setOnClickListener(this);

        // map buttons IDs to input strings
        keyValues.put(R.id.btOne, "1");
        keyValues.put(R.id.btTwo, "2");
        keyValues.put(R.id.btThree, "3");
        keyValues.put(R.id.btFour, "4");
        keyValues.put(R.id.btFive, "5");
        keyValues.put(R.id.btSix, "6");
        keyValues.put(R.id.btSeven, "7");
        keyValues.put(R.id.btEight, "8");
        keyValues.put(R.id.btNine, "9");
        keyValues.put(R.id.btZero, "0");
        keyValues.put(R.id.btFingerPrint, "\n");
    }

    @Override
    public void onClick(View v) {

        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;

        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.getId() == R.id.btBackspace) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                // delete the selection
                inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);
        }
    }

    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }
}

