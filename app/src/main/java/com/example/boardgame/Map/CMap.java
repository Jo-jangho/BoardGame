package com.example.boardgame.Map;

/**
 * Created by 장호 on 2016-05-28.
 */
public class CMap
{
    public static int Size = 15;

    public int m_nList[] = {
            0, 3, -1, -2, 1,
            -4, -3, 1, 3, -1,
            2, -3, 1, -5, 0};

    /*
     ** function
     */
    public int Get(int idx)
    {
        return m_nList[idx];
    }

}

/*
Start +3 -1 +1 -4 -3 +1 +3 -1 +2 -3 +1 -5 GOAL
 */