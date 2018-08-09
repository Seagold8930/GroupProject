/**
 * Copyrightę 2018 - A. Perry, A. Sirvid, D. Mota
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHORS DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package application;

import java.util.ArrayList;
import java.util.List;

public class Project {
	private int projectID;
	private String username;
	private String projectName;
	private List<Task> taskList = new ArrayList();
	
	public Project(int pID, String user, String pName) {
		this.projectID = pID;
		this.username = user;
		this.projectName = pName;
	}

	public void addTaskList(Task task) {		
		this.taskList.add(task);
	}	
	public String getProjectName() {
		 return this.projectName;
	}
}
