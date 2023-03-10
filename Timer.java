/*
 * Timer class used as a tool to manage cooldowns and time limitations
 */

public class Timer {
    private double startTime; 
    private double timerLength;

    public void start(){
        this.startTime = System.currentTimeMillis();
    }
    public void setTimerLength(double timeLength){
        this.timerLength = timeLength*1000;
    }
    // Returns if the Timer is up
    public boolean finished(){
        double currentTime = System.currentTimeMillis();
        if(this.startTime == 0 || this.timerLength == 0){
            return true;
        }
        return (currentTime - this.startTime) >= timerLength;
    }
}
