package application;

public class RuntimeAttributes {
	private String userToken;
	private boolean saved;
	private boolean changed;
	private boolean concluded;
	private boolean taskSelected;
	
	
	public RuntimeAttributes() {
		this.userToken = null;
		this.saved = false;
		this.changed = false;
		this.concluded = false;
		this.taskSelected = false;
	}

	public RuntimeAttributes(String userToken) {
		super();
		this.userToken = userToken;
	}

	
	public RuntimeAttributes(String userToken, boolean concluded) {
		super();
		this.userToken = userToken;
		this.saved = false;
		this.changed = false;
		this.concluded = concluded;
		this.taskSelected = true;
	}

	public String getUserToken() {
		return userToken;
	}
	
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	public boolean isSaved() {
		return saved;
	}
	
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	
	public boolean isChanged() {
		return changed;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean isConcluded() {
		return concluded;
	}

	public void setConcluded(boolean concluded) {
		this.concluded = concluded;
	}

	public Boolean isTaskSelected() {
		return taskSelected;
	}

	public void setTaskSelected(Boolean taskSelected) {
		this.taskSelected = taskSelected;
	}
}
