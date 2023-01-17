import java.net.*;
import java.util.*;
import java.io.*;

public class Player extends GameObject implements Runnable {
    private String name;
    private int team; // 0 -> Red Team | 1 -> Blue Team
    private int playerId;
    private Agent agent;
    private boolean ready;

    private int itemsHolding;
    private Gun primaryGun;
    private Gun secondaryGun;
    private int holdingSlot;
    private int health;
    private boolean alive;
    private int numCredits;
    private boolean hasBomb;
    private double movementSpeed;
    private final double defaultMovementSpeed;
    private int direction; // degrees

    Socket socket;
    PrintWriter output;
    BufferedReader input;

    Queue<String> messages;

    Player(int playerId, Socket socket) throws IOException {
        this.playerId = playerId;
        this.socket = socket;

        this.team = -1;
        this.holdingSlot = 2;
        this.defaultMovementSpeed = Const.PLAYER_MOVEMENT_SPEED;
        this.movementSpeed = defaultMovementSpeed;

        this.messages = new LinkedList<String>();

        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream());
    }

    public boolean collides(GameObject other) {
        return false;
    }

    public void resetMovementSpeed() {
        this.movementSpeed = defaultMovementSpeed;
    }

    // ------------------------------------------------------------------------------------------------

    public void kill() {
        Gun gunDrop;
        if (this.getPrimGun() != null) {
            gunDrop = this.getPrimGun();
        } else {
            gunDrop = this.getSecGun();
        }
        this.setAlive(false);
        /* TODO: Drop gun function */
    }

    // Client to server commands
    public void name(String name) {
        this.setName(name);
    }

    public void update(){
        while(!messages.isEmpty()){
            String msg = messages.poll();
            String[] args = msg.split(" ", 2);
            String command = args[0];
            switch(command){
                case "ERROR":
                case "NAME":
                case "TEAM":
                case "AGENT":
                case "READY":
            }
        }
    }

    public void run() {
        try {
            while (true) {
                String msg = input.readLine();
                if (msg.length() > 0) {
                    System.out.println("input: " + msg);
                    this.messages.add(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print(String text) {
        if (this.socket == null) {
            System.out.println("Dead socket, message send failure");
            return;
        }
        this.output.println(text);
        this.output.flush();
    }

    // ------------------------------------------------------------------------------------------------
    // getters and setters

    public String getName() {
        return this.name;
    }

    public int getTeam() {
        return this.team;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public Agent getAgent() {
        return this.agent;
    }

    public boolean checkReady() {
        return this.ready;
    }

    public int getCredits() {
        return this.numCredits;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public Gun getPrimGun() {
        return this.primaryGun;
    }

    public Gun getSecGun() {
        return this.secondaryGun;
    }

    public int getHealth() {
        return this.health;
    }

    public double getMovementSpeed() {
        return this.movementSpeed;
    }

    public boolean checkSpike() {
        return this.hasBomb;
    }

    public int getDirection() {
        return this.direction;
    }

    public Gun getHolding() {
        if (this.holdingSlot == 1)
            return primaryGun;
        if (this.holdingSlot == 2)
            return secondaryGun;
        else
            return null;
    }

    public int getItemsHolding() {
        return this.itemsHolding;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(int teamId) {
        this.team = teamId;
    }

    public void setAgent(String agentName) {
        this.agent = Agent.valueOf(agentName);
    }

    public void setReady() {
        this.ready = !(this.ready);
    }

    public void setCredits(int credits) {
        this.numCredits = credits;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setGun(int slot, Gun gun) {
        switch (slot) {
            case 0:
                this.primaryGun = gun;
            case 1:
                this.secondaryGun = gun;
        }
    }

    public void setMovementSpeed(double newSpeed) {
        this.movementSpeed = newSpeed;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHealth(boolean damage, int amount) {
        if (damage)
            this.health -= amount;
        if (!damage)
            this.health += amount;
    }

    public void setSpike(boolean hasSpike) {
        this.hasBomb = hasSpike;
    }

    public void setDirection(int degrees) {
        this.direction = degrees % 360;
    }

    public void setDirection(int deltaX, int deltaY) {
        this.direction = (int) (Math.atan2(deltaY, deltaX));
    }

    public void setHolding(int gunSlot) {
        switch (gunSlot) {
            case 1:
                this.holdingSlot = gunSlot;
            case 2:
                this.holdingSlot = gunSlot;
        }
    }

    public void switchWeaponUp() {
        this.holdingSlot = (this.holdingSlot + 1) % itemsHolding;
    }

    public void switchWeaponDown() {
        if (this.holdingSlot == 1)
            this.holdingSlot = this.itemsHolding;
        else
            this.holdingSlot--;
    }

    public void setItemsHolding(int numItems) {
        this.itemsHolding = numItems;
    }
}