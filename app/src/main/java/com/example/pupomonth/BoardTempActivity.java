package com.example.pupomonth;

import static com.example.pupomonth.R.*;
import static com.example.pupomonth.R.id.btnShowMore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardTempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.all_board_all);


        TextView tvBoard = findViewById(R.id.tvBoardTitle);
        Button btnShow = findViewById(id.btnShowMore);

        ImageButton imgButton =findViewById(id.navi33);

        imgButton.setOnClickListener(v->{
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mPreContractStartActivityResult.launch(cameraIntent);
        });

        tvBoard.setText("내 게시판");
        btnShow.setText("내 게시물");

        ArrayList<ItemBoard> boardList = new ArrayList<>();
        boardList.add(new ItemBoard("항공드론연계정공이수대상자모집","http://contents.jabcho.org/uploads/B.jpeg"));
        boardList.add(new ItemBoard("2023클라우드기반AIIDEACONTEST","http://contents.jabcho.org/uploads/C.png"));
        boardList.add(new ItemBoard("2023추계종합학술대회및대학생논문경진대회","http://contents.jabcho.org/uploads/D.jpeg"));
        boardList.add(new ItemBoard("영어과학기술논문MASTER","http://contents.jabcho.org/uploads/E.png"));
        boardList.add(new ItemBoard("JBNU문작성기초역량강화특강","http://contents.jabcho.org/uploads/F.png"));
        ScrollView scrollView = findViewById(id.boardScrollView);
        ListView listView = findViewById(id.boardListView);
        listView.setOnTouchListener((v, event) -> {
            scrollView.requestDisallowInterceptTouchEvent(true);
            return false;
        });
        BoardAdapter boardAdapter = new BoardAdapter(this, boardList);


        findViewById(id.btnShowMore).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(drawable.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == id.action_menu1){
                        Toast.makeText(BoardTempActivity.this, "전체 보기 클릭", Toast.LENGTH_SHORT).show();
                        tvBoard.setText("전체 게시판");
                        btnShow.setText("전체 보기");
                        listView.setAdapter(boardAdapter);
                    }else if (menuItem.getItemId() == id.action_menu2){
                        Toast.makeText(BoardTempActivity.this, "관리자 공지 클릭", Toast.LENGTH_SHORT).show();
                        tvBoard.setText("관리자 게시판");
                        btnShow.setText("관리자 공지");
                    }else {
                        Toast.makeText(BoardTempActivity.this, "내 게시물 클릭", Toast.LENGTH_SHORT).show();
                        tvBoard.setText("내 게시판");
                        btnShow.setText("내 게시물");
                    }

                    return false;
                });
                popupMenu.show();
            }
        });
    }
    private void runCameraResult(Bitmap bitmap){
        Intent cameraResultIntent = new Intent(BoardTempActivity.this, CameraResultTempActivity.class);
        cameraResultIntent.putExtra("bm",(Bitmap) bitmap);
        startActivity(cameraResultIntent);
    }

    private final ActivityResultLauncher<Intent> mPreContractStartActivityResult =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    a_result -> {
                        if (a_result.getResultCode() == Activity.RESULT_OK) {
                            assert a_result.getData() != null;
                            Bitmap photo = (Bitmap) Objects.requireNonNull(a_result.getData().getExtras()).get("data");
                            runCameraResult(photo);
                        }
                    });

}