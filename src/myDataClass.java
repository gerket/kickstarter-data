import java.util.ArrayList;
import java.util.Date;

public class myDataClass {

	String project_id;
	String name;
	String url;
	String category;
	String subcategory;
	String location;
	String status;
	Double goal;
	int pledged;
	double funded_percentage;
	int backers;
	Date funded_date;
	int levels;
	//ArrayList<String> reward_levels; //make this an arraylist of size 'levels' 
	String reward_levels;
	int updates;
	int comments;
	double duration; //days
	
	public myDataClass() {
		
	}
	
	public myDataClass(final String project_id, final String name, final String url, final String category, final String subcategory, final String location, final String status, 
			final Double goal, final int pledged, final double funded_percentage, final int backers, final Date funded_date, final int levels, final String reward_levels,
			final int updates, final int comments, final double duration) {
		
		this.project_id = project_id;
		this.name =  name;
		this.url = url;
		this.category = category;
		this.subcategory = subcategory;
		this.location = location;
		this.status = status;
		this.goal = goal;
		this.pledged = pledged;
		this.funded_percentage = funded_percentage;
		this.backers = backers;
		this.funded_date = funded_date;
		this.levels = levels;
		
		//this.reward_levels = new ArrayList<String>(); //make this an arraylist of size 'levels' 
		
		//If I have time I want to parse through this line and get the different reward levels into an arraylist but I guess I don't really need to for the job assigned
		this.reward_levels = reward_levels;
		
		this.updates = updates;
		this.comments = comments;
		this.duration = duration; //days
	}

	/**
	 * @param project_id the project_id to set
	 */
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param subcategory the subcategory to set
	 */
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(Double goal) {
		this.goal = goal;
	}

	/**
	 * @param pledged the pledged to set
	 */
	public void setPledged(int pledged) {
		this.pledged = pledged;
	}

	/**
	 * @param funded_percentage the funded_percentage to set
	 */
	public void setFunded_percentage(double funded_percentage) {
		this.funded_percentage = funded_percentage;
	}

	/**
	 * @param backers the backers to set
	 */
	public void setBackers(int backers) {
		this.backers = backers;
	}

	/**
	 * @param funded_date the funded_date to set
	 */
	public void setFunded_date(Date funded_date) {
		this.funded_date = funded_date;
	}

	/**
	 * @param levels the levels to set
	 */
	public void setLevels(int levels) {
		this.levels = levels;
	}

	/**
	 * @param reward_levels the reward_levels to set
	 */
	public void setReward_levels(String reward_levels) {
		this.reward_levels = reward_levels;
	}

	/**
	 * @param updates the updates to set
	 */
	public void setUpdates(int updates) {
		this.updates = updates;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(int comments) {
		this.comments = comments;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * @return the project_id
	 */
	public String getProject_id() {
		return project_id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the subcategory
	 */
	public String getSubcategory() {
		return subcategory;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the goal
	 */
	public Double getGoal() {
		return goal;
	}

	/**
	 * @return the pledged
	 */
	public int getPledged() {
		return pledged;
	}

	/**
	 * @return the funded_percentage
	 */
	public double getFunded_percentage() {
		return funded_percentage;
	}

	/**
	 * @return the backers
	 */
	public int getBackers() {
		return backers;
	}

	/**
	 * @return the funded_date
	 */
	public Date getFunded_date() {
		return funded_date;
	}

	/**
	 * @return the levels
	 */
	public int getLevels() {
		return levels;
	}

	/**
	 * @return the reward_levels
	 */
	public String getReward_levels() {
		return reward_levels;
	}

	/**
	 * @return the updates
	 */
	public int getUpdates() {
		return updates;
	}

	/**
	 * @return the comments
	 */
	public int getComments() {
		return comments;
	}

	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = project_id.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + url.hashCode();
		result = prime * result + category.hashCode();
		result = prime * result + subcategory.hashCode();
		result = prime * result + location.hashCode();
		result = prime * result + status.hashCode();
		result = prime * result + Double.toString(goal).hashCode();
		result = prime * result + pledged;
		result = prime * result + Double.toString(funded_percentage).hashCode();
		result = prime * result + backers;
		result = prime * result + funded_date.hashCode();
		result = prime * result + levels;
		result = prime * result + reward_levels.hashCode();
		result = prime * result + updates;
		result = prime * result + comments;
		result = prime * result + Double.toString(duration).hashCode();
		
		return result;
				
	}
	
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof myDataClass)) {
			return false;
		}
		
		
		myDataClass other = (myDataClass) obj;
		
		if(!this.project_id.equals(other.project_id)){
			return false;
		}
		if(!this.name.equals(other.name)){
			return false;
		}
		if(!this.url.equals(other.url)){
			return false;
		}
		if(!this.category.equals(other.category)){
			return false;
		}
		if(!this.subcategory.equals(other.subcategory)){
			return false;
		}
		if(!this.location.equals(other.location)){
			return false;
		}
		if(!this.status.equals(other.status)){
			return false;
		}
		if(this.goal != other.goal){
			return false;
		}
		if(this.pledged != other.pledged){
			return false;
		}
		if(this.funded_percentage != other.funded_percentage){
			return false;
		}
		if(this.levels != other.levels){
			return false;
		}
		if(!this.reward_levels.equals(other.reward_levels)){
			return false;
		}
		if(this.updates != other.updates){
			return false;
		}
		if(this.comments != other.comments){
			return false;
		}
		if(this.duration != other.duration){
			return false;
		}
		
		
		
		return true;
	}
	
	@Override
	public String toString() {
		return String
				.format(
				"myDataClass [project_id: %s, name: %s, url: %s, category: %s, subcategory: %s, location: %s, status: %s, goal: %s, pledged: %s, funded_percentage: %s, levels: %s, reward_levels: %s, updates: %s, comments: %s, duration: %s]",
				project_id, name, url, category, subcategory, location, status, goal, pledged, funded_percentage, levels, reward_levels, updates, comments, duration);
	}
	
}
