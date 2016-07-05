package com.example.boardgame;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boardgame.Dice.CDiceClient;
import com.example.boardgame.Item.CItemClient;
import com.example.boardgame.Map.CMap;
import com.example.boardgame.Player.CPlayerClient;

public class MainActivity extends AppCompatActivity
{
    /* var */
    public CPlayerClient m_cPlayerClient = null;
    CDiceClient m_cDiceClient = null;
    public CMap m_cMap = null;
    public CItemClient m_cItem = null;

    TextView turn;
    Button btnRoll, btn_dice, btn_item;

    String player_1_name;
    String player_2_name;

    View dialogView;
    EditText dlgPlayer1Name, dlgPlayer2Name;

    /*
     ** function
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);


        /* Init */
        dialogView = (View)View.inflate(MainActivity.this, R.layout.dialog1, null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("사용자 정보 입력");
        dlg.setView(dialogView);
        dlg.setPositiveButton("확인",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dlgPlayer1Name = (EditText)dialogView.findViewById(R.id.player_1_name);
                        dlgPlayer2Name = (EditText)dialogView.findViewById(R.id.player_2_name);

                        player_1_name = dlgPlayer1Name.getText().toString();
                        player_2_name = dlgPlayer2Name.getText().toString();

                        m_cPlayerClient.SetPlayerName(CPlayerClient.TurnPlayer1, player_1_name);
                        m_cPlayerClient.SetPlayerName(CPlayerClient.TurnPlayer2, player_2_name);

                        m_cPlayerClient.Update();
                    }
                });
        dlg.setNegativeButton("취소", null);
        dlg.show();

        turn = (TextView)findViewById(R.id.turn);
        m_cMap = new CMap();
        m_cPlayerClient = new CPlayerClient(this, this, R.id.turn, player_1_name, player_2_name);
        m_cDiceClient = new CDiceClient(this, R.id.dice);
        m_cItem = new CItemClient(this);

        m_cPlayerClient.Move(CPlayerClient.TurnPlayer1,0);
        m_cPlayerClient.Move(CPlayerClient.TurnPlayer2,0);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        btnRoll = (Button)findViewById(R.id.btnRoll);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(m_cPlayerClient.GetDiceCount(m_cPlayerClient.GetTurn()) - 1 < 0)
                    return;

                if(false == m_cPlayerClient.GetActive())
                {
                    Toast("한 턴에 한 번만 굴리세요!");
                    return;
                }

                /**/
                m_cDiceClient.Roll();

                m_cPlayerClient.Roll(m_cDiceClient.GetVal());

                /**/
                Win();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        btn_dice = (Button)findViewById(R.id.btn_dice);
        btn_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("주사위 상점");
                dlg.setMessage("주사위를 구매하시겠습니까? (HP - 3)");
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(m_cPlayerClient.GetDiceCount(m_cPlayerClient.GetTurn()) + 3 > 10)
                                {
                                    Toast("10개 이상 구매가 불가능합니다.");
                                    return;
                                }
                                m_cDiceClient.BuyResultOk();
                                m_cPlayerClient.DiceBuyResultOk();
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                m_cPlayerClient.SetTurn(!m_cPlayerClient.GetTurn());
                m_cPlayerClient.SetActive(true);
                m_cPlayerClient.Update();
            }
        });

        //**//*
        btn_item = (Button)findViewById(R.id.btn_item);

        btn_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("아이템 상점");
                dlg.setItems(m_cItem.GetShopList(), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        m_cItem.m_nShopItemSelect = which;
                        /**/
                        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                        dlg.setTitle("아이템 상점");
                        dlg.setMessage(m_cItem.GetBuyMsg(m_cItem.m_nShopItemSelect));
                        dlg.setPositiveButton("확인",
                                new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        m_cItem.Buy();
                                        m_cPlayerClient.Update();
                                    }
                                });
                        dlg.setNegativeButton("취소", null);
                        dlg.show();
                    }
                });
                dlg.setPositiveButton("닫기", null);
                dlg.show();
            }
        });
    }
    public void Win()
    {
        if( m_cPlayerClient.GetPlayer1().GetHPMax() == 11)
        {
            Toast.makeText(MainActivity.this, m_cPlayerClient.GetPlayer1().GetName() + "님이 승리 하였습니다.!!", Toast.LENGTH_SHORT).show();

            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 3000);
        }
        else if( m_cPlayerClient.GetPlayer2().GetHPMax() == 11)
        {
            Toast.makeText(MainActivity.this, m_cPlayerClient.GetPlayer2().GetName() + "님이 승리 하였습니다.!!", Toast.LENGTH_SHORT).show();

            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 3000);
        }
    }

    public void Toast(String strMsg)
    {
        Toast.makeText(MainActivity.this, strMsg, Toast.LENGTH_SHORT).show();
    }
}