package entities;

public interface MoveableEntity extends Entity {
	public double getDX();
	public double getDY();
	public void setDY(double dy);
	public void setDX(double dx);
}
