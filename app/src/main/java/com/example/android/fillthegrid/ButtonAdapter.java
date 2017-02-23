package com.example.android.fillthegrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.HashSet;
import java.util.Random;

/**
 * Class created by Reggie on 12-Feb-17.
 */

public class ButtonAdapter extends BaseAdapter {

    private Context mContext;

    //Default difficulty level
    private int difficultyLevel = 4;
    //Default number of buttons that will be stored in my grid
    private int gridSize = 72;

    public ButtonAdapter(Context c) {
        mContext = c;
    }

    public void setDifficultyLevel(int level) { difficultyLevel = level; }

    public void setGridSize(int size){ gridSize = size; }

    public int getCount() {
        return gridSize;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new Button for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = new Button(mContext);
            if (mContext.getResources().getConfiguration().orientation == 1) {
                btn.setLayoutParams(new GridView.LayoutParams(75, 75));
            } else {
                btn.setLayoutParams(new GridView.LayoutParams(135, 135));
            }

        } else {
            btn = (Button) convertView;
        }

        //Create random number for selecting a random color
        Random rand = new Random();

        //Get array of backgrounds
        TypedArray backgrounds = mContext.getResources().obtainTypedArray(R.array.button_bgs);

        //Set background to a random colour
        btn.setBackgroundResource(backgrounds.getResourceId(rand.nextInt(difficultyLevel), 0));
        backgrounds.recycle();

        //Set a button tag for future potential usage
        btn.setTag(position);

        //Setting an onClick method for each button.
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColour(v);
            }
        });

        return btn;
    }

    private void setColour(View v) {
        //get the Parent(in this case the Gridview)
        ViewGroup parent = (ViewGroup) v.getParent();

        //Get the background you will be setting all the neighbouring buttons/groups of buttons to
        Drawable background = v.getBackground();

        /*Use a HashSet to store the positions of all the neighbouring buttons/groups of buttons
        which are the same background. Using a HashSet over an ArrayList because a HashSet does
        not allow duplicates */
        HashSet<Integer> positionList = new HashSet<>();

        //Populate HashSet by checking in all directions.
        checkRight(v, positionList);
        checkLeft(v, positionList);
        for(int i : positionList){
            checkUp(parent.getChildAt(i), positionList);
            checkDown(parent.getChildAt(i), positionList);
            checkRight(parent.getChildAt(i), positionList);
            checkLeft(parent.getChildAt(i), positionList);
        }

        //Loop through HashSet, create new HashSet for each item in this one, with positions of
        //each button you will be changing
        for(int i : positionList){
            HashSet<Integer> posList = new HashSet<>();

            if(((i + 1) < gridSize) && ((i + 1) % 9 != 0)){
                checkRight(parent.getChildAt(i + 1), posList);
            }
            if(((i - 1) > 0) && ((i - 2) % 9 != 0)){
                checkLeft(parent.getChildAt(i - 1), posList);
            }
            if((i - 9) > 9){
                checkUp(parent.getChildAt(i - 9), posList);
            }
            if((i + 9) < gridSize){
                checkDown(parent.getChildAt(i + 9), posList);
            }
            //Set all buttons in this group to the chosen color
            for(int j : posList){
                parent.getChildAt(j).setBackground(background);
            }
        }
    }

    //These next four methods for checking in all four directions for to populate the HashSet containing the positions of all buttons in the
    private void checkRight(View v, HashSet posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgr = background;

        while ((bgr == background) && ((position) % 9 != 0) && (position < gridSize)) {
            posList.add(position);
            position += 1;
            v = parent.getChildAt(position);
            bgr = v.getBackground();
        }

    }

    private void checkLeft(View v, HashSet posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgl = background;

        while ((bgl == background) && ((position - 1) % 9 != 0) && (position > 0)) {
            posList.add(position);
            position -= 1;
            v = parent.getChildAt(position);
            bgl = v.getBackground();
        }
    }

    private void checkUp(View v, HashSet posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgu = background;

        while ((bgu == background) && (position > 9)) {
            posList.add(position);
            position -= 9;
            v = parent.getChildAt(position);
            bgu = v.getBackground();
        }
    }

    private void checkDown(View v, HashSet posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgd = background;

        while ((bgd == background) && (position < (gridSize - 9))) {
            posList.add(position);
            position += 9;
            v = parent.getChildAt(position);
            bgd = v.getBackground();
        }
    }
}
