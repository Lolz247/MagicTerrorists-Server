public class Gun {
    public GunModel model;
    private int ammo;
    private boolean active;

    Gun(String type, int ammo) {
        this.model = GunModel.valueOf(type);
        this.ammo = ammo;
        this.active = false;
    }

    public double fire(){
        if(this.ammo > 0) {
            this.ammo--;
            return 1/(model.getFireRate());
        }
        return 0;
    }
    public double reload(){
        if(this.ammo < model.getMaxAmmo()) {
            this.ammo = model.getMaxAmmo();
            return (model.getReloadSpeed());
        }
        return 0;
    }
    
    public int getAmmo() {
        return this.ammo; 
    }
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    public boolean isActive() {
        return this.active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}