package com.example.boardgame.Player;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by 장호 on 2016-05-14.
 */
public class CPlayer
{
    /* var */
    TextView m_vitextHP = null;
    TextView m_vitextHP_Max = null;
    TextView m_vitextDiceCount = null;
    TextView m_vitextMoney = null;
    ImageView m_viimgToken = null;
    TextView m_vitextName = null;

    CPlayerData m_cData = null;

    /*
     ** function
     */

    public CPlayer(Context context,String strName,int nIdHp, int nIdHpMax, int nIdDiceCount, int nIdMoney, int nToken,int nName)
    {
        m_vitextHP =  (TextView) ((Activity)context).findViewById((nIdHp));
        m_vitextHP_Max =  (TextView) ((Activity)context).findViewById((nIdHpMax));
        m_vitextDiceCount = (TextView) ((Activity)context).findViewById((nIdDiceCount));
        m_vitextMoney = (TextView) ((Activity)context).findViewById((nIdMoney));
        m_viimgToken = (ImageView) ((Activity)context).findViewById((nToken));
        m_vitextName = (TextView) ((Activity)context).findViewById((nName));

        /**/
        m_cData = new CPlayerData();
        m_cData.m_nHP = 10;
        m_cData.m_nHP_Max = 10;
        m_cData.m_nDiceCount = 3;
        m_cData.m_nMoney = 0;
        m_cData.m_strName = strName;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    public void Update()
    {
        m_vitextHP.setText(String.valueOf(m_cData.m_nHP));
        m_vitextHP_Max.setText(String.valueOf(m_cData.m_nHP_Max));
        m_vitextDiceCount.setText(String.valueOf(m_cData.m_nDiceCount));
        m_vitextMoney.setText(String.valueOf(m_cData.m_nMoney));
        m_vitextName.setText(String.valueOf(m_cData.m_strName));

        m_viimgToken.setX(120 + (160 * m_cData.m_nidx));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //

    public int GetHPMax()
    {
        return m_cData.m_nHP_Max;
    }
    public void SetHPMax(int nHPMax)
    {
        m_cData.m_nHP_Max = nHPMax;
    }

    public int GetIdx()
    {
        return m_cData.m_nidx;
    }
    public void SetIdx(int nIdx)
    {
        m_cData.m_nidx = nIdx;
    }

    public int GetDiceCount()
    {
        return m_cData.m_nDiceCount;
    }
    public void SetDiceCount(int nCount)
    {
        m_cData.m_nDiceCount = nCount;
    }

    public int GetHP()
    {
        return m_cData.m_nHP;
    }

    public void AddHP(int nHP)
    {
        m_cData.m_nHP += nHP;

        if(m_cData.m_nHP > m_cData.m_nHP_Max)
            m_cData.m_nHP = m_cData.m_nHP_Max;
    }
    public void SetHP(int nHP)
    {
        m_cData.m_nHP = nHP;
    }
    public String GetName()
    {
        return m_cData.m_strName;
    }
    public void SetName(String strName)
    {
        m_cData.m_strName = strName;
    }

    public int GetMoney()
    {
        return m_cData.m_nMoney;
    }
    public void SetMoney(int nMoney)
    {
        m_cData.m_nMoney = nMoney;
    }
    public void AddDiceCount(int nVal)
    {
        m_cData.m_nDiceCount += nVal;
    }
    public void AddMoney(int nMoney)
    {
        m_cData.m_nMoney += nMoney;
    }
    public void AddMHP(int nVal)
    {
        m_cData.m_nHP_Max += nVal;
    }
}