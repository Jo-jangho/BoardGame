package com.example.boardgame.Item;

/**
 * Created by 장호 on 2016-05-29.
 */
public class CItemInfoData
{
    /* var */
    String m_strName = null;
    int m_nPrice;

    CItemData [] m_cList = null;

    /*
     ** function
     */
    public CItemInfoData(String strName, int nPrice)
    {
        m_strName = strName;
        m_nPrice = nPrice;
    }

    public void SetItemList(CItemData [] cList)
    {
        m_cList = null;     //메모리 리셋
        m_cList = cList;
    }
    public CItemData GetItem(int idx)
    {
        return m_cList[idx];
    }
    public int GetSize()
    {
        return m_cList.length;
    }

    public String GetName()
    {
        return m_strName;
    }
    public int GetPrice()
    {
        return m_nPrice;
    }
}
