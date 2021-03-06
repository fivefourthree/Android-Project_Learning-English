package com.example.asus.learningenglish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    private TextView player_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Objects.requireNonNull(getSupportActionBar()).hide();

        player_name = findViewById(R.id.player_name);
        player_name.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        player_name.setText(getName());


        Button btn_game = findViewById(R.id.btn_game);
        btn_game.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        Button btn_study = findViewById(R.id.btn_study);
        btn_study.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        Button btn_record = findViewById(R.id.btn_record);
        btn_record.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        changeActivity(btn_game, 0);
        changeActivity(btn_study, 1);
        changeActivity(btn_record, 2);

        player_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        final EditText setName = new EditText(this);
        new AlertDialog.Builder(this).setTitle("Your name:").setView(setName).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = setName.getText().toString();
                if (!newName.equals("")) {
                    MainActivity.db.execSQL("update " + DBOpenHelper.DATABASE_TABLE + " set english='" + newName + "' where id='0'");
                    player_name.setText(newName);
                }
            }
        }).show();
    }

    private String getName() {
        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE ,null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("english"));
        cursor.close();
        return name;
    }

    private void changeActivity(Button btn_click, final int check_target) {
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (check_target) {
                    case 0:
                        intent.setClass(MenuActivity.this, GameActivity.class);
                        break;
                    case 1:
                        intent.setClass(MenuActivity.this, StudyActivity.class);
                        break;
                    case 2:
                        intent.setClass(MenuActivity.this, RecordActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        });
    }

}
