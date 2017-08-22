package com.mathmash;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

/**
 * Created by Home on 9/7/2016.
 */
public class Wrong extends MainActivity{

    Boolean comingFromQuestionActivity = true;
    String operationChosen;
    TextView tv_showCorrectAnswer;
    TextView tv_showQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrong_layout);
        tv_showCorrectAnswer = (TextView)findViewById(R.id.tv_showCorrectAnswer);
        tv_showQuestion = (TextView)findViewById(R.id.tv_showQuestion);


        }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                if (comingFromQuestionActivity) {
                    Intent intent = new Intent(Wrong.this, Question.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(Wrong.this, RevisitMistakes.class);
                    intent.putExtra("operationChosen",operationChosen);
                    startActivity(intent);
                    finish();
                }
            }

            public void onTick(long millisUntilFinished) {
            }

        }.start();
        animateAnswer();

    }

    private void animateAnswer(){
        if (getIntent().getStringExtra("operationChosen") != null){
            comingFromQuestionActivity = false;
            operationChosen = getIntent().getStringExtra("operationChosen");
        }

        if (getIntent().getIntExtra("correctAnswer",-1) > 0){
            String num1 = String.valueOf(getIntent().getIntExtra("num1",-1));
            String num2 = String.valueOf(getIntent().getIntExtra("num2",-1));
            String operation = getIntent().getStringExtra("operation");
            String question = num1 + " " +operation+ " "+num2 + " = ";
            int correctAnswer = getIntent().getIntExtra("correctAnswer",-1);
            tv_showCorrectAnswer.setText(String.valueOf(getIntent().getIntExtra("correctAnswer",-1)));
            tv_showQuestion.setText(question);
            tv_showCorrectAnswer.setVisibility(View.VISIBLE);
            tv_showQuestion.setVisibility(View.VISIBLE);
            //tv_showCorrectAnswer.setAlpha(1f);
            //tv_showQuestion.setAlpha(1f);

            int startDelay_q = 800;
            int startDelay_a = 800;
            int setDuration = 0;


            /*ObjectAnimator animator1 = ObjectAnimator.ofFloat(tv_showQuestion, "alpha", 0f);
            animator1.setStartDelay(startDelay_q*2);
            animator1.setRepeatCount(0);
            animator1.setDuration(setDuration);

            //2nd show

            ObjectAnimator animator2 = ObjectAnimator.ofFloat(tv_showQuestion, "alpha", 1f);
            animator2.setStartDelay(startDelay_q/2);
            animator2.setRepeatCount(0);
            animator2.setDuration(setDuration);

            ObjectAnimator animator3 = ObjectAnimator.ofFloat(tv_showQuestion, "alpha", 0f);
            //animator3.setStartDelay(2500);
            animator3.setStartDelay(startDelay_q + (startDelay_q/2));
            animator3.setRepeatCount(0);
            animator3.setDuration(setDuration);

            //3rd show

            ObjectAnimator animator4 = ObjectAnimator.ofFloat(tv_showQuestion, "alpha", 1f);
            //animator4.setStartDelay(3000);
            animator4.setStartDelay(startDelay_q/2);
            animator4.setRepeatCount(0);
            animator4.setDuration(setDuration);

            ObjectAnimator animator5 = ObjectAnimator.ofFloat(tv_showQuestion, "alpha", 0f);
            //animator5.setStartDelay(3500);
            long temptime = startDelay_q + (startDelay_q / 2);
            animator5.setStartDelay(temptime);
            animator5.setRepeatCount(0);
            animator5.setDuration(setDuration);

            //sequencial animation
            AnimatorSet set = new AnimatorSet();
            set.play(animator1).before(animator2);
            set.play(animator2).before(animator3);
            set.play(animator3).before(animator4);
            set.play(animator4).before(animator5);
            set.start();*/


            //TV_2 ANIMATION
            ObjectAnimator tv2_animator1 = ObjectAnimator.ofFloat(tv_showCorrectAnswer, "alpha", 0f);
            tv2_animator1.setRepeatCount(0);
            tv2_animator1.setDuration(setDuration);

            ObjectAnimator tv2_animator2 = ObjectAnimator.ofFloat(tv_showCorrectAnswer, "alpha", 1f);
            tv2_animator2.setStartDelay(startDelay_a);
            tv2_animator2.setRepeatCount(0);
            tv2_animator2.setDuration(setDuration);

            ObjectAnimator tv2_animator3 = ObjectAnimator.ofFloat(tv_showCorrectAnswer, "alpha", 0f);
            //animator3.setStartDelay(2500);
            tv2_animator3.setStartDelay(startDelay_a);
            tv2_animator3.setRepeatCount(0);
            tv2_animator3.setDuration(setDuration);

            //2nd viewing

            ObjectAnimator tv2_animator4 = ObjectAnimator.ofFloat(tv_showCorrectAnswer, "alpha", 1f);
            //animator4.setStartDelay(3000);
            tv2_animator4.setStartDelay(startDelay_a);
            tv2_animator4.setRepeatCount(0);
            tv2_animator4.setDuration(setDuration);

            /*letting answer stay till end of activity
            ObjectAnimator tv2_animator5 = ObjectAnimator.ofFloat(tv_showCorrectAnswer, "alpha", 0f);
            //animator5.setStartDelay(3500);
            tv2_animator5.setStartDelay(startDelay_a);
            tv2_animator5.setRepeatCount(0);
            tv2_animator5.setDuration(setDuration);*/

            //3rd show
            /*ObjectAnimator tv2_animator6 = ObjectAnimator.ofFloat(tv_showCorrectAnswer, "alpha", 1f);
            //animator4.setStartDelay(3000);
            tv2_animator6.setStartDelay(startDelay_a);
            tv2_animator6.setRepeatCount(0);
            tv2_animator6.setDuration(setDuration);

            ObjectAnimator tv2_animator7 = ObjectAnimator.ofFloat(tv_showCorrectAnswer, "alpha", 0f);
            //animator5.setStartDelay(3500);
            tv2_animator7.setStartDelay(startDelay_a);
            tv2_animator7.setRepeatCount(0);
            tv2_animator7.setDuration(setDuration);*/

            //sequencial animation
            AnimatorSet set2 = new AnimatorSet();
            set2.play(tv2_animator1).before(tv2_animator2);
            set2.play(tv2_animator2).before(tv2_animator3);
            set2.play(tv2_animator3).before(tv2_animator4);
            //set2.play(tv2_animator4).before(tv2_animator5);
            //set2.play(tv2_animator5).before(tv2_animator6);
            //set2.play(tv2_animator6).before(tv2_animator7);
            set2.start();





            /*//animation for QUESTION
            AnimationSet as = new AnimationSet(true);
            as.setFillEnabled(true);
            as.setInterpolator(new BounceInterpolator());

            AlphaAnimation ta = new AlphaAnimation(0f,1f);
            ta.setDuration(setDuration);
            as.addAnimation(ta);

            //question stays on for one seconds
            AlphaAnimation ta2 = new AlphaAnimation(1f,0f);
            ta2.setStartOffset(startDelay*2);
            ta2.setRepeatCount(0);
            ta2.setDuration(setDuration);
            as.addAnimation(ta2);

            //2nd show

            AlphaAnimation ta3 = new AlphaAnimation(0f,1f);
            ta2.setStartOffset(startDelay);
            ta2.setRepeatCount(0);
            ta3.setDuration(setDuration);
            as.addAnimation(ta3);

            //question stays on for two seconds
            AlphaAnimation ta4 = new AlphaAnimation(1f,0f);
            ta4.setStartOffset(startDelay); // allowing 2000 milliseconds for ta to finish
            ta4.setDuration(setDuration);
            as.addAnimation(ta4);

            //3rd  show
            AlphaAnimation ta5 = new AlphaAnimation(0f,1f);
            ta5.setStartOffset(startDelay*2); // allowing 2000 milliseconds for ta to finish
            ta5.setDuration(setDuration);
            as.addAnimation(ta5);

            AlphaAnimation ta6 = new AlphaAnimation(1f,0f);
            ta6.setStartOffset(startDelay); // allowing 2000 milliseconds for ta to finish
            ta6.setDuration(setDuration);
            as.addAnimation(ta6);


            tv_showQuestion.setAnimation(as);

            //animation for ANSWER
            AnimationSet ans_as = new AnimationSet(true);
            ans_as.setFillEnabled(true);
            ans_as.setInterpolator(new BounceInterpolator());

            AlphaAnimation ans_ta = new AlphaAnimation(0f,1f);
            ans_ta.setStartOffset(startDelay);
            ta2.setRepeatCount(0);
            ans_ta.setDuration(setDuration);
            ans_as.addAnimation(ans_ta);

            //question stays on for two seconds
            AlphaAnimation ans_ta2 = new AlphaAnimation(1f,0f);
            ans_ta2.setStartOffset(startDelay); // allowing 2000 milliseconds for ta to finish
            ta2.setRepeatCount(0);
            ans_ta2.setDuration(setDuration);
            as.addAnimation(ans_ta2);

            AlphaAnimation ans_ta3 = new AlphaAnimation(0f,1f);
            ans_ta2.setStartOffset(startDelay);
            ta2.setRepeatCount(0);
            ans_ta3.setDuration(setDuration);
            as.addAnimation(ans_ta3);

            //question stays on for two seconds
            AlphaAnimation ans_ta4 = new AlphaAnimation(1f,0f);
            ans_ta4.setStartOffset(startDelay); // allowing 2000 milliseconds for ta to finish
            ta2.setRepeatCount(0);
            ans_ta4.setDuration(setDuration);
            ans_as.addAnimation(ans_ta4);

            tv_showCorrectAnswer.setAnimation(ans_as);
            //as.start();
            //ans_as.start();
            ans_as.play(ans_ta)*/





            //tv_showCorrectAnswer.animate().scaleXBy(3f).scaleYBy(3f).setInterpolator(new BounceInterpolator()).setDuration(2000).start();
            //tv_showCorrectAnswer.animate().alpha(1)(3f).scaleYBy(3f).setInterpolator(new BounceInterpolator()).setDuration(2000).start();
        }

}
}




