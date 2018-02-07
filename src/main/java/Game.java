
public class Game {
    private int itsScore = 0;
    private int[] itsThrows = new int[21]; // 최대 투구 횟수
    private int itsCurrentThrow = 0;

    private int itsCurrentFrame = 1; // 현재 프레임 계산
    private boolean firstThrowFrame = true; // 프레임에서 첫번째 투구 여부 확인

    private int ball;
    private int firstThrow;
    private int secondThrow;

    public int score() {
        return scoreForFrame(getCurrentFrame() - 1);
    }

    public void add(int pins) {
        itsThrows[itsCurrentThrow++] = pins;
        itsScore += pins;
        adjustCurrentFrame(pins);
    }

    private void adjustCurrentFrame(int pins) {
        if (firstThrowFrame == true) {
            if (pins == 10) {  // 스트라이크
                itsCurrentFrame++; // 스트라이크는 한번만 투구하고 종료되기 때문에
            } else {
                firstThrowFrame = false;
            }
        } else {
            firstThrowFrame = true;
            itsCurrentFrame++;
        }
        itsCurrentFrame = Math.min(11, itsCurrentFrame); // itsCurrentFrame은 종료의미기 때문에 10frame 인경우 11로 되어 있다.
    }

    public int getCurrentFrame() {
        return itsCurrentFrame;
    }

    public int scoreForFrame(int theFrame) {
        ball = 0;
        int score = 0;
        for (int currentFrame = 0; currentFrame < theFrame; currentFrame++) {
            if (strike()) {
                score += 10 + nextTwoBallsForStrile();
                ball++;
            } else if (spare()) {
                ball += 2;
                score += 10 + nextBallForSpare();
            } else {
                score += twoBallsInFrame();
                ball += 2;
            }
        }

        return score;
    }

    private boolean strike() {
        return itsThrows[ball] == 10;
    }

    private boolean spare() {
        return (itsThrows[ball] + itsThrows[ball + 1]) == 10;
    }

    private int nextTwoBallsForStrile() {
        return itsThrows[ball + 1] + itsThrows[ball + 2];
    }

    private int nextBallForSpare() {
        return itsThrows[ball + 2];
    }

    private int twoBallsInFrame() {
        return itsThrows[ball] + itsThrows[ball + 1];
    }
}
