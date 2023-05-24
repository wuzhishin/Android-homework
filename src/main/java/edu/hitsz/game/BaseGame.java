package edu.hitsz.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.ImageManager;
import edu.hitsz.activity.MainActivity;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AircraftFactory;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.BulletProp;

/**
 * 游戏逻辑抽象基类，遵循模板模式，action() 为模板方法
 * 包括：游戏主面板绘制逻辑，游戏执行逻辑。
 * 子类需实现抽象方法，实现相应逻辑
 * @author hitsz
 */
public abstract class BaseGame extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    public static final String TAG = "BaseGame";
    boolean mbLoop = false; //控制绘画线程的标志位
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //绘图的画布
    private Paint mPaint;
    protected Handler handler0;
    protected Handler handler1;

    //点击屏幕位置
    float clickX = 0, clickY=0;

    private int backGroundTop = 0;

    /**
     * 背景图片缓存，可随难度改变
     */
    protected Bitmap backGround;



    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 16;

    private final HeroAircraft heroAircraft;

    protected final List<AbstractAircraft> enemyAircrafts;

    private final List<AbstractBullet> heroBullets;
    private final List<AbstractBullet> enemyBullets;
    private final List<BaseProp> props;

    protected int enemyMaxNumber = 2;

    protected int bossNumber;

    protected int bossExistNumber = 0;

    private boolean gameOverFlag = false;
    protected static int score = 0;
    private int time = 0;

    protected AircraftFactory aircraftFactory;

    protected double AscendTimes = 1.0;

    protected double ElitePossiblity = 0.2;


    /**
     * 周期（ms)
     * 控制英雄机射击周期，默认值设为简单模式
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;




    public BaseGame(Context context, Handler handler0, Handler handler1){
        super(context);
        this.handler0 = handler0;
        this.handler1 = handler1;
        mbLoop = true;
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
        ImageManager.initImage(context);
        score = 0;

        // 初始化英雄机
        heroAircraft = HeroAircraft.getInstance();
        heroAircraft.setHp(1000);

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        heroController();
    }
    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        //new Thread(new Runnable() {
        Runnable task = () -> {

                time += timeInterval;



            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {

                createAircraft();

                shootAction();
            }

                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();

                //道具移动
                propsMoveAction();

                // 撞击检测
                try {

                    crashCheckAction();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 后处理
                postProcessAction();

                try {
                    Thread.sleep(timeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            //}
        };

        Runnable difficultyIncrease = () -> {
            IncreaseDifficulty();
        };


        new Thread(task).start();
        new Thread(difficultyIncrease).start();
    }

    public void heroController(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickX = motionEvent.getX();
                clickY = motionEvent.getY();
                heroAircraft.setLocation(clickX, clickY);

                if ( clickX<0 || clickX> MainActivity.screenWidth || clickY<0 || clickY>MainActivity.screenHeight){
                    // 防止超出边界
                    return false;
                }
                return true;
            }
        });
    }


    abstract void createAircraft();

    abstract void IncreaseDifficulty();

    private void shootAction() {
        // 敌机射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts){
            if(enemyAircraft instanceof EliteEnemy){
                enemyBullets.addAll(enemyAircraft.executeStrategy(((EliteEnemy) enemyAircraft).getDirection(),((EliteEnemy) enemyAircraft).getPower()));
            }
            else if(enemyAircraft instanceof BossEnemy){
                enemyBullets.addAll(enemyAircraft.executeStrategy(((BossEnemy) enemyAircraft).getDirection(),((BossEnemy) enemyAircraft).getPower()));
            }
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.executeStrategy(heroAircraft.getDirection(), heroAircraft.getPower()));
    }

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction(){
        for (BaseProp tool : props){
            tool.forward();
        }
    }


    /**
     * 碰撞检测：
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() throws InterruptedException {
        // 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    Message message4 = Message.obtain();
                    message4.what = 4 ;
                    handler1.sendMessage(message4);

                    enemyAircraft.decreaseHp(bullet.getPower());


                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        score += 10;
                        if(enemyAircraft instanceof EliteEnemy){
                            ((EliteEnemy) enemyAircraft).PropDrop(props);
                        }
                        else if(enemyAircraft instanceof BossEnemy){
                            ((BossEnemy) enemyAircraft).PropDrop(props);

                            Message message3 = Message.obtain();
                            message3.what = 3 ;
                            handler0.sendMessage(message3);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得补给
        for (BaseProp prop : props){
            if (prop.notValid()) {
                continue;
            }
            if(heroAircraft.crash(prop)){
                Message message6 = Message.obtain();
                message6.what = 6 ;
                handler1.sendMessage(message6);

                if(prop instanceof BloodProp){
                    ((BloodProp) prop).Effect(heroAircraft);
                }
                else if(prop instanceof BombProp){
                    Message message5 = Message.obtain();
                    message5.what = 5 ;
                    handler1.sendMessage(message5);

                    List<AbstractAircraft> bombDie = ((BombProp) prop).Effect(enemyAircrafts,enemyBullets);
                    for(AbstractAircraft enemy:bombDie){
                        score += 10;
                    }
                }
                else if(prop instanceof BulletProp){
                    ((BulletProp) prop).Effect(heroAircraft);
                }
                prop.vanish();
            }
        }
    }
    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);

        if (heroAircraft.notValid()) {

            BulletProp.EffectThread.shutdown();

            gameOverFlag = true;
            mbLoop = false;
            Log.i(TAG, "heroAircraft is not Valid");


            System.out.println("Game Over!");


        }

    }

    public void draw() {
        canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }

        //绘制背景，图片滚动
        canvas.drawBitmap(backGround,0,this.backGroundTop-backGround.getHeight(),mPaint);
        canvas.drawBitmap(backGround,0,this.backGroundTop,mPaint);
        backGroundTop +=1;
        if (backGroundTop == MainActivity.screenHeight)
            this.backGroundTop = 0;

        //先绘制子弹，后绘制飞机
        paintImageWithPositionRevised(enemyBullets); //敌机子弹


        paintImageWithPositionRevised(heroBullets);  //英雄机子弹


        paintImageWithPositionRevised(enemyAircrafts);//敌机

        paintImageWithPositionRevised(props); //道具


        canvas.drawBitmap(ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY()- ImageManager.HERO_IMAGE.getHeight() / 2,
                mPaint);

        //画生命值
        paintScoreAndLife();

        mSurfaceHolder.unlockCanvasAndPost(canvas);

    }

    private void paintImageWithPositionRevised(List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, mPaint);
        }
    }

    private void paintScoreAndLife() {
        int x = 10;
        int y = 40;

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);

        canvas.drawText("SCORE:" + this.score, x, y, mPaint);
        y = y + 60;
        canvas.drawText("LIFE:" + this.heroAircraft.getHp(), x, y, mPaint);
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        new Thread(this).start();
        Log.i(TAG, "start surface view thread");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        MainActivity.screenWidth = i1;
        MainActivity.screenHeight = i2;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        mbLoop = false;
    }

    @Override
    public void run() {

        while (mbLoop){   //游戏结束停止绘制
            synchronized (mSurfaceHolder){
                action();
                draw();
            }
        }
        Message message1 = Message.obtain();
        message1.what = 1 ;

        handler0.sendMessage(message1);
    }

    public static int getScore(){
        return score;
    }

}
