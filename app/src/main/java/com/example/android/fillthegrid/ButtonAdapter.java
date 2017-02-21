package com.example.android.fillthegrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Reggie on 12-Feb-17.
 */

public class ButtonAdapter extends BaseAdapter {

    private Context mContext;
    public int difficultyLevel = 4;
    //Number of buttons that will be stored in my grid
    private Integer[] gridSize = new Integer[72];

    public ButtonAdapter(Context c) {
        mContext = c;
    }

    public int setDifficultyLevel(int level) {
        difficultyLevel = level;
        return difficultyLevel;
    }

    public int getCount() {
        return gridSize.length;
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

    public void setColour(View v) {
        //get the Parent(in this case the Gridview)
        ViewGroup parent = (ViewGroup) v.getParent();

        //Get the background you will be setting all the neighbouring buttons/groups of buttons to
        Drawable background = v.getBackground();

        //Use an arraylist to store the positions of all the neighbouring buttons/groups of buttons
        // which are the same background
        ArrayList<Integer> positionList = new ArrayList<Integer>();

        //Populate arraylist by checking in all directions
        checkRight(v, positionList);
        checkLeft(v, positionList);
        checkUp(v, positionList);
        checkDown(v, positionList);

        //Loop through arraylist, create new ArrayList for each item in this one, with positions of
        //each button you will be changing
        for(int i = 0; i < positionList.size(); i++){
            ArrayList<Integer> posList = new ArrayList<Integer>();
            v = parent.getChildAt(positionList.get(i));

            setRight(v, posList);
            setLeft(v, posList);
            setUp(v, posList);
            setDown(v, posList);

            //Set all buttons in this group to the chosen color
            for(int j = 0; j < posList.size(); j++){
                parent.getChildAt(posList.get(j)).setBackground(background);
            }
        }
    }

    //These next four methods for checking in all four directions for to populate the arraylist containing the positions of all buttons in the
    public void checkRight(View v, ArrayList<Integer> posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgr = background;

        while ((bgr == background) && ((position) % 8 != 0) && (position < gridSize.length)) {
            position += 1;
            bgr = parent.getChildAt(position).getBackground();
            checkUp(v, posList);
            checkDown(v, posList);
        }
        posList.add(position);
    }

    public void checkLeft(View v, ArrayList<Integer> posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgl = background;

        while ((bgl == background) && ((position) % 9 != 0) && (position > 0)) {
            position -= 1;
            bgl = parent.getChildAt(position).getBackground();
            checkUp(v, posList);
            checkDown(v, posList);
        }
        posList.add(position);
    }

    public void checkUp(View v, ArrayList<Integer> posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgu = background;

        while ((bgu == background) && (position > 9)) {
            position -= 9;
            bgu = parent.getChildAt(position).getBackground();
            checkLeft(v, posList);
            checkRight(v, posList);
        }
        posList.add(position);
    }

    public void checkDown(View v, ArrayList<Integer> posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgd = background;

        while ((bgd == background) && (position < (gridSize.length - 9))) {
            position += 9;
            bgd = parent.getChildAt(position).getBackground();
            checkLeft(v, posList);
            checkRight(v, posList);
        }
        posList.add(position);
    }

    public void setRight(View v, ArrayList<Integer> posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgr = background;

        while ((bgr == background) && ((position) % 8 != 0) && (position < gridSize.length)) {
            posList.add(position);
            position += 1;
            bgr = parent.getChildAt(position).getBackground();
            setUp(v, posList);
            setDown(v, posList);
        }
    }

    public void setLeft(View v, ArrayList<Integer> posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgl = background;

        while ((bgl == background) && ((position) % 9 != 0) && (position > 0)) {
            posList.add(position);
            position -= 1;
            bgl = parent.getChildAt(position).getBackground();
            setUp(v, posList);
            setDown(v, posList);
        }
    }

    public void setUp(View v, ArrayList<Integer> posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgu = background;

        while ((bgu == background) && (position > 9)) {
            posList.add(position);
            position -= 9;
            bgu = parent.getChildAt(position).getBackground();
            setLeft(v, posList);
            setRight(v, posList);
        }
    }

    public void setDown(View v, ArrayList<Integer> posList) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int position = (Integer) v.getTag();

        //Get button background
        Drawable background = v.getBackground();
        Drawable bgd = background;

        while ((bgd == background) && (position < (gridSize.length - 9))) {
            posList.add(position);
            position += 9;
            bgd = parent.getChildAt(position).getBackground();
            setLeft(v, posList);
            setRight(v, posList);
        }
    }
}
