package com.example.boardgame.Item;

/**
 * Created by 장호 on 2016-05-28.
 */

public class CItemData
{
    String m_strType;
    String m_strTarget;
    String m_strName;
    int m_nVal;
    int m_nLevel;
    int m_nPrice;

    public CItemData(String _strName, String _strType,String _strTarget ,int _nVal, int _nLevel, int _nPrice)
    {
        m_strTarget = _strTarget;
        m_strName = _strName;
        m_strType = _strType;
        m_nVal = _nVal;
        m_nLevel = _nLevel;
        m_nPrice = _nPrice;
    }
}

