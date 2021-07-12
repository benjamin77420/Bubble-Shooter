package GameUnits;

public interface Factory {
    Ball creatBall();
    MovingBall creatMovingBall(Ball ball);
}
