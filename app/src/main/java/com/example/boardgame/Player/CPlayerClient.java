package com.example.boardgame.Player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.example.boardgame.MainActivity;
import com.example.boardgame.R;

/**
 * Created by 장호 on 2016-05-14.
 */
public class CPlayerClient
{
    /* final */
    public static boolean TurnPlayer1 = false;
    public static boolean TurnPlayer2 = true;

    /* valr */
    MainActivity m_cMainActivity = null;

    CPlayer m_cPlayer1;
    CPlayer m_cPlayer2;

    TextView m_vitextTurn = null;
    Boolean m_bTurn = TurnPlayer1;
    Boolean m_bActive = true; //
    Context m_Context = null;

    /*
     ** function
     */

    public CPlayerClient(MainActivity cMainActivity, Context context, int nIdTurn, String player_1_name, String player_2_name)
    {
        m_cMainActivity = cMainActivity;
        m_Context = context;

        m_vitextTurn = (TextView) ((Activity)context).findViewById((nIdTurn));

        /**/
        m_cPlayer1 = new CPlayer(context, player_1_name, R.id.player_1_HP, R.id.player_1_MHP, R.id.player_1_dice, R.id.player_1_money, R.id.player1,R.id.player_1);
        m_cPlayer2 = new CPlayer(context, player_2_name, R.id.player_2_HP, R.id.player_2_MHP, R.id.player_2_dice, R.id.player_2_money, R.id.player2,R.id.player_2);

        Update();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    public void SetPlayerName(boolean bPlayer, String strName)
    {
        if(bPlayer == TurnPlayer1)
            m_cPlayer1.SetName(strName);
        else if(bPlayer == TurnPlayer2)
            m_cPlayer2.SetName(strName);
    }
    public void Roll(int nVal)
    {
        SetDiceCount(m_bTurn, GetDiceCount(m_bTurn) - 1);

        SetActive(false);    //

        AddMoney(m_bTurn,10);

        /**/
        int nMovePoint =  GetIdx(m_bTurn) + nVal;

        if(nMovePoint >= m_cMainActivity.m_cMap.Size-1)
        {
            Goal(m_bTurn);
            return;
        }
        else if(nMovePoint < 0)
            return;

        int nMapData = m_cMainActivity.m_cMap.Get(nMovePoint);

        if(m_bTurn == TurnPlayer1)
            m_cPlayer1.AddHP(nMapData);
        else if(m_bTurn == TurnPlayer2)
            m_cPlayer2.AddHP(nMapData);

        /**/
        Move(m_bTurn,nVal);

        /**/
        Update();
    }
    public void DiceBuyResultOk()
    {
        SetHP(m_bTurn, GetHP(m_bTurn) - 3);

        SetDiceCount(m_bTurn, GetDiceCount(m_bTurn) + 3);
        Death(m_bTurn);
        Update();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    public void Update()
    {
        if(m_bTurn == TurnPlayer1)
        {
            m_vitextTurn.setText(m_cPlayer1.GetName());
            m_vitextTurn.setTextColor(Color.RED);
        }
        else if(m_bTurn == TurnPlayer2)
        {
            m_vitextTurn.setText(m_cPlayer2.GetName());
            m_vitextTurn.setTextColor(Color.BLUE);
        }

        /**/
        Death(m_bTurn);

        /**/
        m_cPlayer1.Update();
        m_cPlayer2.Update();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    public void AddHp(boolean bPlayer,int nVal)
    {
        if(bPlayer == TurnPlayer1)
        {
            m_cPlayer1.AddHP(nVal);
        }
        else if(bPlayer == TurnPlayer2)
        {
            m_cPlayer2.AddHP(nVal);
        }
    }
    public void AddMoney(boolean bPlayer,int nVal)
    {
        if(bPlayer == TurnPlayer1)
        {
            m_cPlayer1.AddMoney(nVal);
        }
        else if(bPlayer == TurnPlayer2)
        {
            m_cPlayer2.AddMoney(nVal);
        }
    }
    public void Death(boolean bPlayer)
    {
        if (m_cPlayer1.GetHP() <= 0)
        {
            m_cPlayer1.SetIdx(0);

            m_cPlayer1.SetHP(m_cPlayer1.GetHPMax());
            m_cPlayer1.SetMoney(0);

            if(m_bTurn == bPlayer)
            {
                SetActive(false);
            }
        }
        if(m_cPlayer2.GetHP() <= 0)
        {
            m_cPlayer2.SetIdx(0);

            m_cPlayer2.SetHP(m_cPlayer2.GetHPMax());
            m_cPlayer2.SetMoney(0);

            if(m_bTurn == bPlayer)
            {
                SetActive(false);
            }
        }
    }

    public void Goal(boolean bPlayer)
    {
        if(bPlayer == TurnPlayer1)
        {
            m_cPlayer1.SetIdx(0);

            m_cPlayer1.SetHPMax(m_cPlayer1.GetHPMax() + 1);
            m_cPlayer1.SetHP(m_cPlayer1.GetHPMax());
        }
        else if(bPlayer == TurnPlayer2)
        {
            m_cPlayer2.SetIdx(0);

            m_cPlayer2.SetHPMax(m_cPlayer2.GetHPMax() + 1);
            m_cPlayer2.SetHP(m_cPlayer2.GetHPMax());
        }
    }
    public void Move(boolean bPlayer,int nIndex)
    {
        if(bPlayer == TurnPlayer1)
            m_cPlayer1.SetIdx(m_cPlayer1.GetIdx() + nIndex);
        else if(bPlayer == TurnPlayer2)
            m_cPlayer2.SetIdx(m_cPlayer2.GetIdx() + nIndex);
    }
    public int GetHP(boolean bPlayer)
    {
        if(bPlayer == TurnPlayer1)
            return m_cPlayer1.GetHP();
        else if(bPlayer == TurnPlayer2)
            return m_cPlayer2.GetHP();

        return -1;
    }
    public void SetHP(boolean bPlayer, int nHp)
    {
        if(bPlayer == TurnPlayer1)
            m_cPlayer1.SetHP(nHp);
        else if(bPlayer == TurnPlayer2)
            m_cPlayer2.SetHP(nHp);
    }

    public int GetDiceCount(boolean bPlayer)
    {
        if(bPlayer == TurnPlayer1)
            return m_cPlayer1.GetDiceCount();
        else if(bPlayer == TurnPlayer2)
            return m_cPlayer2.GetDiceCount();

        return -1;
    }
    public void SetDiceCount(boolean bPlayer, int nCount)
    {
        if(bPlayer == TurnPlayer1)
            m_cPlayer1.SetDiceCount(nCount);
        else if(bPlayer == TurnPlayer2)
            m_cPlayer2.SetDiceCount(nCount);
    }
    public int GetIdx(boolean bPlayer)
    {
        if(bPlayer == TurnPlayer1)
            return m_cPlayer1.GetIdx();
        else if(bPlayer == TurnPlayer2)
            return m_cPlayer2.GetIdx();

        return -1;
    }

    public  CPlayer GetPlayer1()
    {
        return  m_cPlayer1;
    }
    public  CPlayer GetPlayer2()
    {
        return  m_cPlayer2;
    }

    public boolean GetTurn()
    {
        return m_bTurn;
    }
    public void SetTurn(boolean turn)
    {
        m_bTurn = turn;
    }

    public boolean GetActive()
    {
        return m_bActive;
    }
    public void SetActive(boolean active)
    {
        m_bActive = active;
    }
}
