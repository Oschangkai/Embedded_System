/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sappy5678.lab3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author zp
 */
public class CalTour {
    private Boolean map[][]=new Boolean[8][8];
    private int StartPositionX=0;
    private int StartPositionY=0;
    int initX;int initY;
    private ArrayList<Integer> nextPositionX=new ArrayList<Integer>();
    private ArrayList<Integer> nextPositionY=new ArrayList<Integer>();
    private Boolean check=true;
    private final int xMove[] = {-2,-1,1,2,2,1,-1,-2};
    private final int yMove[] = {1,2,2,1,-1,-2,-2,-1};
    //private int stepsPosiible[]={0,0,0,0,0,0,0,0};
    //private int stepsIndex[]={0,1,2,3,4,5,6,7};
    private int steps=0;
    public void setStartPositionX(int startPositionX) {
        StartPositionX = startPositionX;
    }

    public void setStartPositionY(int startPositionY) {
        StartPositionY = startPositionY;
    }
    public boolean checkstep(int nextStepX,int nextStepY){
        //check=true;
        //check outrange
        //System.out.println("nextStepx");
        //System.out.println(nextStepX-1);
        //System.out.println("nextStepy");
        //System.out.println(nextStepY-1);
        if((nextStepX>8||nextStepY>8) || (nextStepX<1||nextStepY<1) || map[nextStepX-1][nextStepY-1])
        {
            return false;
        }
        else
        {
            //System.out.println(nextStepX);
            //System.out.println(nextStepY);
            //System.out.println();
            return true;
        }



    }
    private void goStep(int nextStepX,int nextStepY){
        //System.out.println("555555555555555555555555555555555555555555555");
        this.nextPositionX.add(nextStepX);
        //System.out.println(nextStepX);
        this.nextPositionY.add(nextStepY);
        //System.out.println(nextStepY);
        //System.out.println();
    }
    public void printlist(){

        System.out.print(Arrays.toString(this.nextPositionX.toArray()));
        System.out.println();
        System.out.print(Arrays.toString(this.nextPositionY.toArray()));
        System.out.println();
    }
    private int  calPossibles(int nextStepX,int nextStepY){
        int num=0;
        for(int i=0;i<8;i++)
        {
            if((nextStepX+xMove[i]<=8&&nextStepY+yMove[i]<=8)&&(nextStepX+xMove[i]>=1&&nextStepY+yMove[i]>=1)&&!map[nextStepX-1+xMove[i]][nextStepY-1+yMove[i]])
                num++;
        }
        return num;
    }
    public void reset() {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                map[i][j]=false;
            }
        }
        System.out.println("reset!!!!!!!!");
        steps = 0;
        nextPositionX.clear();
        nextPositionY.clear();
    }
    public void calNextSteps(int calPositionX,int calPositionY)
    {

        StartPositionX=calPositionX;
        StartPositionY=calPositionY;
        this.reset();
        calknightTour(StartPositionX,StartPositionY);
        this.nextPositionX.add(StartPositionX);
        this.nextPositionY.add(StartPositionY);
        Collections.reverse(nextPositionX);
        Collections.reverse(nextPositionY);
        this.printlist();

        System.out.println("done!!!!!!!!!!!!!!!!!!!!!");
    }
    private boolean calknightTour(int calPositionX,int calPositionY)
    {
        //System.out.println(steps);
        //初始化
        int stepsPosiible[]={0,0,0,0,0,0,0,0};
        int stepsIndex[]={0,1,2,3,4,5,6,7};
        for(int j=0;j<8;j++)
        {
            stepsIndex[j]=j;
            stepsPosiible[j]=calPossibles(calPositionX+xMove[j],calPositionY+yMove[j]);
        }

        //bubblesort
        for (int i = 8 - 1; i > 0; i--)
            for (int j = 0; j < i; j++)
                if (stepsPosiible[j] > stepsPosiible[j + 1])
                {
                    int tmp= stepsPosiible[j];
                    int tmp2= stepsIndex[j];
                    stepsPosiible[j] = stepsPosiible[j+1];
                    stepsPosiible[j+1] = tmp;
                    stepsIndex[j] = stepsIndex[j+1];
                    stepsIndex[j+1] = tmp2;
                    //Swap(array, j, j + 1);
                }

        if(steps==63)
            return true;
        steps=steps+1;
        map[calPositionX-1][calPositionY-1]=true;
        for(int i=0;i<8;i++)
        {
            if(stepsPosiible[i]==0)
                continue;
            if(checkstep(calPositionX+xMove[stepsIndex[i]],calPositionY+yMove[stepsIndex[i]]))
            {
                if(calknightTour(calPositionX+xMove[stepsIndex[i]],calPositionY+yMove[stepsIndex[i]]))
                {
                    //System.out.println(calPositionX+xMove[stepsIndex[i]]);
                    //System.out.println(calPositionY+yMove[stepsIndex[i]]);
                    //System.out.println();
                    goStep(calPositionX+xMove[stepsIndex[i]],calPositionY+yMove[stepsIndex[i]]);
                    return true;
                }

            }
        }
        steps=steps-1;
        map[calPositionX-1][calPositionY-1]=false;
        return false;


    }
    /*
        //private int calPositionX=0;
        //private int calPositionY=0;
        public boolean calNextSteps(int calPositionX,int calPositionY)
        {
            //初始化
            //calPositionX=StartPositionX;
            //calPositionY=StartPositionY;
            //System.out.println(steps);
            initX = calPositionX;
            initY =calPositionY;
            if(steps==63)
                return true;
            steps=steps+1;
            //System.out.println(calPositionX);
            //System.out.println(calPositionY);
            //System.out.println();
            map[calPositionX-1][calPositionY-1]=true;
            if(checkstep(calPositionX-2,calPositionY+1))
            {
                if(calNextSteps(calPositionX-2,calPositionY+1))
                {
                    goStep(calPositionX-2,calPositionY+1);
                    return true;
                }
            }
            if(checkstep(calPositionX-1,calPositionY+2))
            {
                if(calNextSteps(calPositionX-1,calPositionY+2))
                {
                    goStep(calPositionX-1,calPositionY+2);
                    return true;
                }
            }
            if(checkstep(calPositionX+1,calPositionY+2))
            {
                if(calNextSteps(calPositionX+1,calPositionY+2))
                {
                    goStep(calPositionX+1,calPositionY+2);
                    return true;
                }
            }
            if(checkstep(calPositionX+2,calPositionY+1))
            {
                if(calNextSteps(calPositionX+2,calPositionY+1))
                {
                    goStep(calPositionX+2,calPositionY+1);
                    return true;
                }

            }
            if(checkstep(calPositionX+2,calPositionY-1))
            {
                if(calNextSteps(calPositionX+2,calPositionY-1))
                {
                    goStep(calPositionX+2,calPositionY-1);
                    return true;
                }
            }
            if(checkstep(calPositionX+1,calPositionY-2))
            {
                if(calNextSteps(calPositionX+1,calPositionY-2))
                {
                    goStep(calPositionX+1,calPositionY-2);
                    return true;
                }
            }
            if(checkstep(calPositionX-1,calPositionY-2))
            {
                if(calNextSteps(calPositionX-1,calPositionY-2))
                {
                    goStep(calPositionX-1,calPositionY-2);
                    return true;
                }
            }
            if(checkstep(calPositionX-2,calPositionY-1))
            {
                if(calNextSteps(calPositionX-2,calPositionY-1))
                {
                    goStep(calPositionX-2,calPositionY-1);
                    return true;
                }
            }
            steps=steps-1;
            map[calPositionX-1][calPositionY-1]=false;
            return false;

        }
    */
    public ArrayList<Integer> getNextPositionX() {
        //nextPositionX.add(initX);
        //Collections.reverse(nextPositionX);
        return nextPositionX;
    }

    public void setNextPositionX(ArrayList<Integer> nextPositionX) {
        this.nextPositionX = nextPositionX;
    }

    public ArrayList<Integer> getNextPositionY() {
        // nextPositionY.add(initY);
        //Collections.reverse(nextPositionY);
        return nextPositionY;
    }

    public void setNextPositionY(ArrayList<Integer> nextPositionY) {
        this.nextPositionY = nextPositionY;
    }
}

