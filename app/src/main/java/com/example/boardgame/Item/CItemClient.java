package com.example.boardgame.Item;

import android.util.Log;

import com.example.boardgame.MainActivity;
import com.example.boardgame.Player.CPlayer;

import java.util.Random;

/**
 * Created by 장호 on 2016-05-28.
 */
public class CItemClient
{
    /* var */
    public MainActivity m_cMainActivity = null;

    CItemInfoData [] m_cItemList = null;

    String[] m_strShopList = null;

    public int m_nShopItemSelect = -1;


    /*
     ** function
     */

    public CItemClient(MainActivity mainActivity)
    {
        m_cMainActivity = mainActivity;

        m_cItemList = new CItemInfoData[3];

        CItemData []cList = null;
        /**/
        m_cItemList[0] = new CItemInfoData("싸고 좋을 수도 있는 것",10);
        cList = new CItemData[6];
        cList[0] = new CItemData("내 말 위치 +1","move","me",1,1,10);
        cList[1] = new CItemData("상대 말 위치 -1","move","you",-1,1,10);
        cList[2] = new CItemData("내 HP +1","hp","me",1,1,10);
        cList[3] = new CItemData("내 HP -2","hp","me",-2,1,10);
        cList[4] = new CItemData("내 돈 +10","money","me",10,1,10);
        cList[5] = new CItemData("내 다이스 +1","dice","me",1,1,10);
        m_cItemList[0].SetItemList(cList);

        m_cItemList[1] = new CItemInfoData("가성비가 좋을 수도 있는것",30);
        cList = new CItemData[6];
        cList[0] = new CItemData("상대 말 위치 -2","move","you",-2,3,30);
        cList[1] = new CItemData("상대 HP -1","hp","you",-1,3,30);
        cList[2] = new CItemData("내 돈 +20","money","me",20,3,30);
        cList[3] = new CItemData("상대 돈 -20","money","you",-20,3,30);
        cList[4] = new CItemData("내 말 위치 그대로","move","me",0,3,30);
        cList[5] = new CItemData("상대 말 위치 그대로","move","you",0,3,30);
        m_cItemList[1].SetItemList(cList);

        m_cItemList[2] = new CItemInfoData("비산 값 하는 것",60);
        cList = new CItemData[6];
        cList[0] = new CItemData("내 말 위치 +2","move","me",2,6,60);
        cList[1] = new CItemData("내 HP +3","hp","me",3,6,60);
        cList[2] = new CItemData("상대 HP -4","hp","you",-4,6,60);
        cList[3] = new CItemData("내 돈 +30","money","me",30,6,60);
        cList[4] = new CItemData("상대 MHP -1","mhp","you",-1,6,60);
        cList[5] = new CItemData("내 다이스 +2","dice","me",2,6,60);
        m_cItemList[2].SetItemList(cList);

        m_strShopList = new String[m_cItemList.length];
        int i = m_cItemList.length;
        while(--i >= 0)
        {
            m_strShopList[i] = String.format("%s (%d원)",m_cItemList[i].GetName(),m_cItemList[i].GetPrice());
        }
    }

    public String[] GetShopList()
    {
        return m_strShopList;
    }
    public String GetBuyMsg(int idx)
    {
        return String.format("%s (%d원)을 구매하시겠습니까? \n(Money - %d)",m_cItemList[idx].GetName(), m_cItemList[idx].GetPrice(), m_cItemList[idx].GetPrice());
    }

    // move, hp, money, dice, mhp
    public void Buy()
    {
        Boolean bPlayer = m_cMainActivity.m_cPlayerClient.GetTurn();
        CPlayer cPlayer = null;

        if(bPlayer == m_cMainActivity.m_cPlayerClient.TurnPlayer1)
            cPlayer =  m_cMainActivity.m_cPlayerClient.GetPlayer1();
        if(bPlayer == m_cMainActivity.m_cPlayerClient.TurnPlayer2)
            cPlayer =  m_cMainActivity.m_cPlayerClient.GetPlayer2();

        if( cPlayer.GetMoney() <  m_cItemList[m_nShopItemSelect].GetPrice())
        {
            m_cMainActivity.Toast("돈 없음 ㅠㅠ");
            return;
        }

        cPlayer.AddMoney(-1 * m_cItemList[m_nShopItemSelect].GetPrice());

        Random rand = new Random();
        int nVal = (int)rand.nextInt(m_cItemList[m_nShopItemSelect].GetSize() -1);

        CItemData cItemData = m_cItemList[m_nShopItemSelect].GetItem(nVal);

        /**/
        m_cMainActivity.Toast(cItemData.m_strName);

        if(true == cItemData.m_strType.equals("hp"))
        {
            Type1(cItemData);
        }
        else if(true == cItemData.m_strType.equals("move"))
        {
            Type2(cItemData);
        }
        else if(true == cItemData.m_strType.equals("money"))
        {
            Type3(cItemData);
        }
        else if(true == cItemData.m_strType.equals("dice"))
        {
            Type4(cItemData);
        }
        else if(true == cItemData.m_strType.equals("mhp"))
        {
            Type5(cItemData);
        }
    }

    /* hp */
    public void Type1(CItemData cItemData)
    {
        boolean bTarget = m_cMainActivity.m_cPlayerClient.GetTurn();

        /**/
        if(true == cItemData.m_strTarget.equals("you"))
            bTarget = !bTarget;

        m_cMainActivity.m_cPlayerClient.AddHp(bTarget,cItemData.m_nVal);

    }
    /* move */
    public void Type2(CItemData cItemData)
    {
        boolean bTarget = m_cMainActivity.m_cPlayerClient.GetTurn();

        /**/
        if(true == cItemData.m_strTarget.equals("you"))
            bTarget = !bTarget;

        int nVal = cItemData.m_nVal;

        int nMovePoint =  m_cMainActivity.m_cPlayerClient.GetIdx(bTarget) + nVal;

        if(nMovePoint >= m_cMainActivity.m_cMap.Size-1)
        {
            m_cMainActivity.m_cPlayerClient.Goal(bTarget);
            return;
        }
        else if(nMovePoint <= 0)
            return;

        int nMapData = m_cMainActivity.m_cMap.Get(nMovePoint);

        if(bTarget == m_cMainActivity.m_cPlayerClient.TurnPlayer1)
            m_cMainActivity.m_cPlayerClient.GetPlayer1().AddHP(nMapData);
        else if(bTarget == m_cMainActivity.m_cPlayerClient.TurnPlayer2)
            m_cMainActivity.m_cPlayerClient.GetPlayer2().AddHP(nMapData);

        /**/
        m_cMainActivity.m_cPlayerClient.Move(bTarget,nVal);
    }
    /* money */
    public void Type3(CItemData cItemData)
    {
        boolean bTarget = m_cMainActivity.m_cPlayerClient.GetTurn();

        if(true == cItemData.m_strTarget.equals("you"))
            bTarget = !bTarget;

        if(bTarget == m_cMainActivity.m_cPlayerClient.TurnPlayer1)
        {
            m_cMainActivity.m_cPlayerClient.GetPlayer1().AddMoney(cItemData.m_nVal);
        }
        if(bTarget == m_cMainActivity.m_cPlayerClient.TurnPlayer2)
        {
            m_cMainActivity.m_cPlayerClient.GetPlayer2().AddMoney(cItemData.m_nVal);
        }
    }
    /* dice */
    public void Type4(CItemData cItemData)
    {
        boolean bTarget = m_cMainActivity.m_cPlayerClient.GetTurn();

        if(true == cItemData.m_strTarget.equals("you"))
            bTarget = !bTarget;

        if(bTarget == m_cMainActivity.m_cPlayerClient.TurnPlayer1)
        {
            m_cMainActivity.m_cPlayerClient.GetPlayer1().AddDiceCount(cItemData.m_nVal);
        }
        if(bTarget == m_cMainActivity.m_cPlayerClient.TurnPlayer2)
        {
            m_cMainActivity.m_cPlayerClient.GetPlayer2().AddDiceCount(cItemData.m_nVal);
        }
    }
    /* mhp */
    public void Type5(CItemData cItemData)
    {
        boolean bTarget = m_cMainActivity.m_cPlayerClient.GetTurn();

        if(true == cItemData.m_strTarget.equals("you"))
            bTarget = !bTarget;

        if(bTarget == m_cMainActivity.m_cPlayerClient.TurnPlayer1)
        {
            m_cMainActivity.m_cPlayerClient.GetPlayer1().AddMHP(cItemData.m_nVal);
        }
        if(bTarget == m_cMainActivity.m_cPlayerClient.TurnPlayer2)
        {
            m_cMainActivity.m_cPlayerClient.GetPlayer2().AddMHP(cItemData.m_nVal);
        }

    }
}

/*
번호 	레벨	 내용
1	1	내 말 위치 +1
2	1	상대 말 위치 -1
3	1	내 HP +1
4	1	내 HP -2
5	1	내 돈 +10
6	1	내 다이스 +1
7	3	상대 말 위치 -2
8	3	상대 HP -1
9	3	내 돈 +20
10	3	상대 돈 -20
11	3	내 말 위치 그대로
12	3	상대 말 위치 그대로
13	6	내 말 위치 +2
14	6	내 HP +3
15	6	상대 HP -4
16	6	내 돈 +30
17	6	상대 MHP -1
18	6	내 다이스 +2
*/