package com.example.boardgame.Dice;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.boardgame.R;

import java.util.Random;


/**
 * Created by 장호 on 2016-05-14.
 */
public class CDiceClient
{
    /* final */
    static int IdDice0 = R.drawable.dice0;
    static int IdDice1 = R.drawable.dice1;
    static int IdDice2 = R.drawable.dice2;
    static int IdDice3 = R.drawable.dice3;

    /* var */
    ImageView m_viImgDice = null;
    Context m_Context = null;

    int m_nVal = 0;

    /*
     ** function
     */

    public CDiceClient(Context context,int nIdDice)
    {
        m_Context = context;

        /**/
        m_viImgDice = (ImageView) ((Activity)m_Context).findViewById((nIdDice));
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    public void Roll()
    {
        Random rand = new Random();
        m_nVal = (int)rand.nextInt(4);

        /**/
        if(m_nVal == 0) {
            SetImg(IdDice0);
            LinearLayout linear = (LinearLayout)View.inflate(m_Context, R.layout.toastdice0, null);
            Toast toast = new Toast(m_Context);
            toast.setView(linear);
            toast.show();
        }

        else if(m_nVal == 1) {
            SetImg(IdDice1);
            LinearLayout linear = (LinearLayout)View.inflate(m_Context, R.layout.toastdice1, null);
            Toast toast = new Toast(m_Context);
            toast.setView(linear);
            toast.show();
        }

        else if(m_nVal == 2) {
            SetImg(IdDice2);
            LinearLayout linear = (LinearLayout)View.inflate(m_Context, R.layout.toastdice2, null);
            Toast toast = new Toast(m_Context);
            toast.setView(linear);
            toast.show();
        }

        else if(m_nVal == 3) {
            SetImg(IdDice3);
            LinearLayout linear = (LinearLayout)View.inflate(m_Context, R.layout.toastdice3, null);
            Toast toast = new Toast(m_Context);
            toast.setView(linear);
            toast.show();
        }
    }

    /**/
    public void BuyResultOk()
    {
        Toast.makeText(m_Context.getApplicationContext(), "주사위를 구매하였습니다.", Toast.LENGTH_SHORT).show();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    public  int GetVal()
    {
        return m_nVal;
    }
    public void SetImg(int nIdDice)
    {
        m_viImgDice.setImageResource(nIdDice);
    }
}
