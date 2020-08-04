//package com.cmy.kotlinsimple;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.RelativeLayout;
//
//import com.lafonapps.adadapter.AdBean;
//import com.lafonapps.adadapter.AdListener;
//import com.lafonapps.adadapter.AdType;
//import com.lafonapps.adadapter.BaseWebViewActivity;
//import com.lafonapps.adadapter.utils.AppStatusDetector;
//import com.lafonapps.adadapter.utils.NotificationCenter;
//import com.lafonapps.common.rate.AppRater;
//import com.lafonapps.common.update.VersionAutoUpdater;
//import com.lafonapps.common.util.Common;
//import com.lafonapps.common.util.Preferences;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Observable;
//import java.util.Observer;
//import java.util.Random;
//
//import static com.lafonapps.adadapter.Constans.AD_TIME_FIRST;
//import static com.lafonapps.adadapter.Constans.AD_TIME_PRELOAD;
//
//public abstract class BaseActivity extends AppCompatActivity implements AdListener {
//
//    private static final String TAG = BaseActivity.class.getName();
//    private static int counter;
//    private static boolean promptToRateOnAppLaunched; //是否已经在应用启动后提示过评论了
//    protected String tag = getClass().getCanonicalName();
//
//    protected boolean hasVideoAd = false;
//    protected boolean hasNativeAd = false;
//
//    private Observer applicationWillEnterForegroundNotificationObserver = new Observer() {
//        @Override
//        public void update(Observable observable, Object o) {
//            if (Common.getCurrentActivity() == BaseActivity.this) {
//                if (CommonConfig.sharedCommonConfig.shouldShowInterstitialAd) {
//                    //showInterstitalAd();
//                } else {
//                    promptToRateAppWhenApplicationEnterForeground();
//                }
//            }
//        }
//    };
//
//    public static boolean isPad(Context context) {
//        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Log.d(tag, "onCreate");
//
//        NotificationCenter.defaultCenter().addObserver
//                (AppStatusDetector.APPLICATION_WILL_ENTER_FOREGROUND_NOTIFICATION,
//                        applicationWillEnterForegroundNotificationObserver);
//
//        VersionAutoUpdater.autoUpdateOnce(this);
//
//        promptToRateAppOnAppLaunched(); //启动应用后提示评论
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(tag, "onStart");
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //预加载全屏广告
////        loadInterstitalAd();
////        showBannerAd();
//        Log.d(tag, "onResume");
//        //待当前Activity界面完成布局后再展示全屏广告，避免无法展示
//        findViewById(android.R.id.content).post(new Runnable() {
//            @Override
//            public void run() {
//                incrementAdCounter();
//            }
//        });
//
//        //预加载激励视频广告
//        if (hasVideoAd) {
//            loadRewardAd();
//        }
//
//        //预加载原生广告
//        if (hasNativeAd) {
//            loadNativeAd();
//        }
//    }
//
//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//
//        Log.d(tag, "onAttachedToWindow");
//    }
//
//    @Override
//    public void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//
//        Log.d(tag, "onDetachedFromWindow");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        Log.d(tag, "onPause");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d(tag, "onRestart");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        Log.d(tag, "onStop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.d(tag, "onDestroy");
//        NotificationCenter.defaultCenter().removeObserver
//                (AppStatusDetector.APPLICATION_WILL_ENTER_FOREGROUND_NOTIFICATION,
//                        applicationWillEnterForegroundNotificationObserver);
//        super.onDestroy();
//    }
//
//    @Override
//    public void onBackPressed() {
//        //拦截返回键，加入以下逻辑：1.没有评论过的提示评论；2.防止连续点击返回键误退出应用，加入"再次点击返回键退出"提示
//        if (shouldPromptToRateAppAtLastActivityOnBackPressed()) {
//            boolean handed = AppRater.defaultAppRater.handBackEventToPromtRate(this);
//            if (handed) {
//                return;
//            }
//        }
//        super.onBackPressed();
//    }
//
//    /**
//     * 显示banner广告
//     */
//    protected void showBannerAd() {
//        AdManager.getInstance().showAd(BaseActivity.this, getBannerView(), AdType.BANNERTYPE, getBannerPositionIndex(), AD_TIME_FIRST, this);
//    }
//
//    /**
//     * 预加载激励视频广告
//     */
//    protected void loadRewardAd() {
//        AdManager.getInstance().showAd(this, null, AdType.REWARDVIDEOTYPE, getRewardVideoPositionIndex(), AD_TIME_PRELOAD, this);
//    }
//
//    /**
//     * 展示激励视频广告
//     */
//    protected void showRewardVideoAd() {
//        AdManager.getInstance().showVideoAd(this, null, AdType.REWARDVIDEOTYPE, getRewardVideoPositionIndex(), AD_TIME_FIRST, this);
//    }
//
//    //头条的横幅广告关闭必须添加一个view，所以不能用LineaLayout
//    public abstract RelativeLayout getBannerView();
//
//    public abstract int getBannerPositionIndex();
//
//    /**
//     * 显示悬浮广告
//     */
////    protected void showFloatAd() {
////        AdManager.getInstance().showAd(CommonConfig.sharedCommonConfig.floatAdName, BaseActivity.this, getFloatView(), AdType.FLOATTYPE, this);
////    }
////
////    public abstract ViewGroup getFloatView();
//
//    /**
//     * 激励视频广告配置选项index
//     *
//     * @return
//     */
//    public abstract int getRewardVideoPositionIndex();
//
//    /**
//     * 预加载原生广告
//     */
//    protected void loadNativeAd() {
//        AdManager.getInstance().showAd(this, getNativeView(), AdType.NATIVELTYPE, getNativePositionIndex(), AD_TIME_PRELOAD, this);
//    }
//
//    /**
//     * 显示信息流原生广告
//     */
//    protected void showNativeAd() {
//        AdManager.getInstance().showVideoAd(this, getNativeView(), AdType.NATIVELTYPE, getNativePositionIndex(), AD_TIME_FIRST, this);
//    }
//
//    //原生广告高度要足够高，否则内容会压缩变形，最好是300dp的高度
//    public abstract ViewGroup getNativeView();
//
//    /**
//     * 显示视屏广告
//     */
////    protected void showVedioAd() {
////        AdManager.getInstance().showAd(CommonConfig.sharedCommonConfig.vedioAdName, BaseActivity.this, getVedioView(), AdType.VIDEOTYPE, this);
////    }
////
////    public abstract ViewGroup getVedioView();
//
//    /**
//     * 显示插屏广告
//     */
////    protected void showInterstitalAd() {
////        AdManager.getInstance().disPlayInterstitial(CommonConfig.sharedCommonConfig.interstitialAdName);
////    }
////
////    public abstract ViewGroup getInterstitalView();
//    public abstract int getNativePositionIndex();
//
//    /**
//     * 加载插屏广告
//     */
////    protected void loadInterstitalAd() {
////        AdManager.getInstance().showAd(CommonConfig.sharedCommonConfig.interstitialAdName, BaseActivity.this, getInterstitalView(), AdType.INTERSTIALTYPE, this);
////    }
//
//
//    //获取当前界面的广告平台
//    public abstract String[] getAdType();
//
//    //针对获取的广告平台，分别释放资源
//    public void destoryAd() {
//        ArrayList<String> adNameList = new ArrayList<String>();
//
//        for (int i = 0; i < getAdType().length; i++) {
//            adNameList.add(getAdType()[i]);
//        }
//        HashSet<String> adNameSetList = new HashSet<String>();
//        adNameSetList.addAll(adNameList);
//        for (String s : adNameSetList) {
//            AdManager.getInstance().destoryAd(s);
//        }
//    }
//
//    /**
//     * 按下Back键时是否提示评论。仅当当前对象是最后一个Activity的时候，并且没有评论过，才会提示评论
//     * 子类可以重写此方法。
//     *
//     * @return 如果没有评论过，是否提示评论。值由CommonConfig.sharedCommonConfig.shouldPromptToRateAppAtLastActivityOnBackPressed决定
//     */
//    protected boolean shouldPromptToRateAppAtLastActivityOnBackPressed() {
//        return CommonConfig.sharedCommonConfig.shouldPromptToRateAppAtLastActivityOnBackPressed;
//    }
//
//    /**
//     * 在应用启动后提示评论。如果已经评论过，或者shouldPromptToRateAppOnAppLaunched()方法返回false，则不提示
//     */
//    public void promptToRateAppOnAppLaunched() {
//        if (shouldPromptToRateAppOnAppLaunched() && AppRater.defaultAppRater.shouldRate(this) && !promptToRateOnAppLaunched) {
//            AppRater.defaultAppRater.promptToRateWithCustomDialog(this);
//            promptToRateOnAppLaunched = true;
//        }
//    }
//
//    /**
//     * 启动应用后，是否提示用户评论。如果用户已评论过，则什么都不做
//     * 子类可以重写此方法。
//     *
//     * @return 如果没有评论过，是否提示评论。值由CommonConfig.sharedCommonConfig.shouldPromptToRateAppOnAppLaunched
//     */
//    protected boolean shouldPromptToRateAppOnAppLaunched() {
//        int appLaunchedCountAtCurrentVersion = Preferences.getSharedPreference().getCountOfAppLaunchedFromVersion();
//        if (CommonConfig.sharedCommonConfig.minAppLaunchCountToPromptToRateAppOnAppLaunched > 0 && CommonConfig.sharedCommonConfig.minAppLaunchCountToPromptToRateAppOnAppLaunched <= appLaunchedCountAtCurrentVersion) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 应用从后台返回到前台，提示用户评论。评论评论的概率是1/3。如果用户已评论过，promptToRateAppWhenApplicationEnterForeground()方法返回false，则不提示。
//     */
//    private void promptToRateAppWhenApplicationEnterForeground() {
//        if (shouldPromptToRateAppWhenApplicationEnterForeground() && AppRater.defaultAppRater.shouldRate(this)) {
//            AppRater.defaultAppRater.promptToRateWithCustomDialog(this);
//        }
//    }
//
//    /**
//     * 从后台返回前台是否提示评论。如已经评论过，则不提示。默认值由CommonConfig.sharedCommonConfig.probabilityValueForPromptToRateAppWhenApplicationEnterForeground决定。如果返回0，则不提示，返回1则每次都提示，返回n则概率为1/n
//     *
//     * @return 如果没有评论过，是否提示评论。
//     */
//    protected boolean shouldPromptToRateAppWhenApplicationEnterForeground() {
//        boolean shouldPrompt = false;
//        int probability = CommonConfig.sharedCommonConfig.probabilityValueForPromptToRateAppWhenApplicationEnterForeground;
//        if (probability > 0) {
//            Random random = new Random();
//            int randomInt = random.nextInt(probability) + 1;
//            Log.d(TAG, "promptToRateAppWhenApplicationEnterForeground: randomInt = " + randomInt);
//            if (randomInt == probability) { //1/n的弹出概率
//                shouldPrompt = true;
//            }
//        }
//        return shouldPrompt;
//    }
//
//    /**
//     * 增加即将弹出广告的计数次数，达到一定次数就会弹出广告，然后重置为0
//     */
//    protected void incrementAdCounter() {
//        counter++;
//
//        int numberOfTimesToPresentInterstitial = Preferences.getSharedPreference().getNumberOfTimesToPresentInterstitial();
//        Log.d(TAG, "presentedTimes = " + counter + ", numberOfTimesToPresentInterstitial = " + numberOfTimesToPresentInterstitial);
//        if (counter >= numberOfTimesToPresentInterstitial && shouldAutoPresentInterstitialAd()) {
//            //显示插屏
//            //showInterstitalAd();
//        }
//    }
//
//    /* 是否在页面跳转次数累计到一定次数后自动弹出全屏广告。默认是true */
//    protected boolean shouldAutoPresentInterstitialAd() {
//        return true;
//    }
//
//    @Override
//    public void onTimeOut(AdBean adBean) {
//
//    }
//
//    @Override
//    public void onLoaded(AdBean adBean) {
//
//    }
//
//    @Override
//    public void onLoadFailed(int code, String msg, AdBean adBean) {
//
//    }
//
//    @Override
//    public void onCloseClick(AdBean adBean) {
//        closeAd(adBean.getAdType());
//    }
//
//    @Override
//    public void onDismiss(AdBean adBean) {
//    }
//
//    @Override
//    public void onAdClick(AdBean adBean) {
//
//    }
//
//    @Override
//    public void onAdExposure(AdBean adBean) {
//
//    }
//
//    @Override
//    public void onComplete(AdBean adBean) {
//
//    }
//
//    @Override
//    public void onVideoCached(AdBean adBean) {
//        Log.i(TAG, "onVideoCached:" + adBean.getPlatform() + ", adType=" + adBean.getAdType());
//    }
//
//    /**
//     * 激励视频播放成功回调
//     */
//    @Override
//    public void onReward(AdBean adBean) {
//
//    }
//
//    private void closeAd(int adType) {
//        ViewGroup container;
//        switch (adType) {
//            case AdType.SPLASHTYPE:
//                break;
//            case AdType.BANNERTYPE:
//                container = getBannerView();
//                if (container != null) {
//                    container.setVisibility(View.GONE);
//                }
//                break;
//        }
//    }
//
//    private ViewGroup getAdViewGroup(int adType) {
//        ViewGroup container = null;
//        switch (adType) {
//            case AdType.NATIVELTYPE:
//                container = getNativeView();
//                break;
//            case AdType.SPLASHTYPE:
//                break;
//            case AdType.BANNERTYPE:
//                container = getBannerView();
//                break;
//        }
//        return container;
//    }
//
//    private int getAdPositionIndex(int adType) {
//        int adPositionIndex = 0;
//        switch (adType) {
//            case AdType.SPLASHTYPE:
//                break;
//            case AdType.BANNERTYPE:
//                adPositionIndex = getBannerPositionIndex();
//                break;
//        }
//        return adPositionIndex;
//    }
//
//    protected void setScreenOrientation() {
//        if (isPad(this)) {
//            steepMode();
//        } else {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//    }
//
//    /***
//     * 沉浸式模式
//     */
//    protected void steepMode() {
//        if (Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
//
//    /**
//     * 跳转第三方网页
//     *
//     * @param url
//     */
//    protected void jumpToThirdParty(String url, String title) {
//        Intent intent2 = new Intent(this, BaseWebViewActivity.class);
//        intent2.putExtra("webUrl", url);
//        intent2.putExtra("title", title);
//        startActivity(intent2);
//    }
//}