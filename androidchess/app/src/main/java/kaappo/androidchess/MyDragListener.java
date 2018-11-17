package kaappo.androidchess;

import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Vector;

import kaappo.androidchess.askokaappochess.TtyUI;
import kaappo.androidchess.askokaappochess.chessboard;
import kaappo.androidchess.askokaappochess.move;
import kaappo.androidchess.askokaappochess.piece;

import static java.lang.Thread.yield;

public class MyDragListener implements View.OnDragListener {

    public static TtyUI ttyUI;
    public static String move;

    private static int iTurn = -3;

    public final int START_PARENT_ID = 0;

    public static int getiTurn() {
        return iTurn;
    }

    public static void setiTurn(int iTurn) {
        MyDragListener.iTurn = iTurn;
    }

    public boolean onDrag(View relativeLayout, DragEvent event) {
        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:

                break;
            case DragEvent.ACTION_DRAG_EXITED:

                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup

                View view = (View) event.getLocalState();

                String move = view.getTag().toString().substring(23, 25) + MainActivity.getId(relativeLayout).substring(23, 25);
                System.out.println("Move: " + move);



                TtyUI.move = move;

                if (isIsMoveValid(move)) {
                    ((RelativeLayout) view.getParent()).removeView(view);
                    ((RelativeLayout) relativeLayout).addView(view);
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }


//                ((RelativeLayout) relativeLayout).removeAllViews();
//                System.out.println("valid asd" + TtyUI.isMoveValid);
//                System.out.println("valid string" + ChessActivity.inputString);
//                while (this.move != null) {
//                    try {Thread.sleep(10);} catch (Exception ignored) {}
//                }




//                if (isMoveValid) {
//                    System.out.println("Move " + move + " is valid!");
//                    ViewGroup oldOwner = (ViewGroup) view.getParent();
//                    oldOwner.removeView(view);
//
//                    RelativeLayout newOwner = (RelativeLayout) relativeLayout;
//                    newOwner.addView(view);
//
//                    view.setVisibility(View.VISIBLE);
//                    isMoveValid = false;
//
//
//                } else {
//                System.out.println("Move " + move.toLowerCase() + " is not valid!");
//                isMoveValid = false;

//                }






                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;
    }


    public static boolean isIsMoveValid (String move) {
        int x1 = (int) move.charAt(0) - 64;
        int y1 = (int) move.charAt(1) - 48;
        int x2 = (int) move.charAt(2) - 64;
        int y2 = (int) move.charAt(3) - 48;


        chessboard chessboard = MyDragListener.ttyUI.getmCb();

        piece p = chessboard.blocks[x1][y1];
        boolean bValid = false;


        if (p == null) {
            return false;
        }
        if (p.iColor != MyDragListener.ttyUI.getiTurn()) {
            bValid = false;
        }

        Vector mv = p.moveVector(chessboard);

        for (int i = 0; i < mv.size(); i++)
        {
            kaappo.androidchess.askokaappochess.move m = (move)mv.elementAt(i);

            if ((m.xtar == x2) && (m.ytar == y2)) {
                bValid = true;
            }

        }

        return bValid;
    }
}