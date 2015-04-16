package noteSystemStudent;

public class status {
	private static status getInstance = null;
	Boolean reload = true;
	private status() {
		// TODO Auto-generated constructor stub
	}
	public static status getInstance() {
		if(getInstance ==null) {
			getInstance = new status();
		}
		return getInstance;
	}
	
	public boolean getStatus() {
		return reload;
	}
	public void setFalse() {
		reload = false;
	}
	public void setTrue() {
		reload = true;
	}
}
