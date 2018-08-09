/**
 * Copyright© 2018 - A. Perry, A. Sirvid, D. Mota
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

public class Task {
	private int taskID;
	private int projectID;
	private String taskName;
	private List<Note> noteList = new ArrayList();
	
	public Task(int tID, int pID, String name) {
		this.taskID = tID;
		this.projectID = pID;
		this.taskName = name;
	}
	
	public void addNoteList(Note note) {		
		this.noteList.add(note);
	}
}
