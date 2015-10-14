package com.kuailedian.happytouch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kuailedian.applictionservice.INavigationService;
import com.kuailedian.components.BottomPopupWindow;
import com.kuailedian.components.LoginPopupWindow;
import com.kuailedian.domain.Account;
import com.kuailedian.domain.CartItem;
import com.kuailedian.domain.OrderCart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maxzhang on 5/8/2015.
 */
public class OrderFragmentBase extends Fragment {

    protected Context context;
    protected View rootView;
    BottomPopupWindow popupView;

    protected TextView tv_orderNum;//购物车

    private  TextView tv_ordercartTitle; //购物车总额

    protected ViewGroup anim_mask_layout;//动画层

    protected void InitView()
    {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v("mylog","mydemolog1");

        context = view.getContext();
        rootView = view;

        tv_orderNum = (TextView)view.findViewById(R.id.ordercart_num);

        tv_ordercartTitle = (TextView)view.findViewById(R.id.ordercart_title);

        setOrderBar();
        //control order popup view ,show or hide;
        ImageButton btnShowOrder  = (ImageButton) view.findViewById(R.id.btn_showolder);
        btnShowOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowOrder();
            }
        });


        //submit the order
        ImageButton btnOrderSubmit  = (ImageButton) view.findViewById(R.id.btn_ordersubmit);
        btnOrderSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //OrderCart cart = OrderCart.getOrderCart();
                final HTApplication app = (HTApplication)getActivity().getApplication();
                final INavigationService navigation = app.GetSystemDomain(INavigationService.class);
                Account account = app.GetSystemDomain(Account.class);
                if(account !=null) {
                    startActivity(new Intent(context, SettleAccountActivity.class));
                }
                else
                {
                    //UserRegisterFragment.goShopAndReservation = "R";
                    LoginPopupWindow popup = new LoginPopupWindow(context);
                    popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            Account account = app.GetSystemDomain(Account.class);
                            if(account !=null) {
                                startActivity(new Intent(context, SettleAccountActivity.class));
                            }

                        }
                    });
                    popup.showAtLocation(rootView , Gravity.CENTER , 0 , 0);
                }

                //if(cart.getIsBuyEnable())
//                if(account!=null)
//                    startActivity(new Intent(context, SettleAccountActivity.class));
                //else
                //    Toast.makeText(context, "包含点餐订单可免运费!商超不小于" + cart.getMinMoney()   , Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        setOrderBar();
    }

    protected void ShowOrder()
    {
        if(popupView==null) {
            popupView = new BottomPopupWindow(context, null);
            popupView.setShowBottomView(rootView);
        }

        popupView.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setOrderBar();
            }
        });

        popupView.Show(rootView);
    }


    private void setOrderBar()
    {
        OrderCart cart = OrderCart.getOrderCart();
        float money =  cart.getToalMoney();
        int amount =  cart.getTotalAmount();

        CharSequence strMoney = "￥" + String.valueOf(money);
        tv_ordercartTitle.setText(strMoney);

        CharSequence strAmount = String.valueOf(amount);
        tv_orderNum.setText(strAmount);
    }


    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        int id = Integer.MAX_VALUE;
        animLayout.setId(id);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    protected void setAnim(final View v, int[] start_location) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                start_location);
        int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
        tv_orderNum.getLocationInWindow(end_location);// shopCart是那个购物车

        // 计算位移
        int endX = 0 - start_location[0] + dipToPixels(65);// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                //ScaleAnimation scaleAnimationX = new ScaleAnimation()
                //btnShowOrder
                Log.v("happylog","animation start");

                Animation scaleAnimation= new ScaleAnimation(1f, 1.08f, 1f, 1.08f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);

                AnimationSet aa = new AnimationSet(true);
                aa.addAnimation(scaleAnimation);
                aa.setDuration(500);
                aa.addAnimation(scaleAnimation);


                tv_orderNum.startAnimation(aa);


            }
        });

    }

    public void AddProducts(View itemview, CartItem item) {

        OrderCart cart = OrderCart.getOrderCart();
        Date now = new Date();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(now);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date stime;
            Date etime;

            if(item.getType().equals("C")) {
                stime = dateFormat.parse(currentDate + " " + cart.getScstime());
                etime = dateFormat.parse(currentDate + " " + cart.getScetime());
            }
            else
            {
                stime = dateFormat.parse(currentDate + " " + cart.getDcstime());
                etime = dateFormat.parse(currentDate + " " + cart.getDcetime());
            }


            if(now.before(stime) || now.after(etime))
            {

                Toast.makeText(context, "现在不是服务时间！",
                        Toast.LENGTH_LONG).show();
                return;
            }



        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }


        cart.addCart(item);
        float money =  cart.getToalMoney();
        int amount =  cart.getTotalAmount();

        Log.v("print money",String.valueOf(money));
        Log.v("print amount",String.valueOf(amount));

        CharSequence strMoney = "￥" + String.valueOf(money);
        tv_ordercartTitle.setText(strMoney);

        CharSequence strAmount = String.valueOf(amount);
        tv_orderNum.setText(strAmount);



        int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        itemview.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        ImageView buyImg = new ImageView(context);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
        buyImg.setImageResource(R.mipmap.sign);// 设置buyImg的图片
        setAnim(buyImg, start_location);// 开始执行动画
    }


    private int dipToPixels(int dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }





}
