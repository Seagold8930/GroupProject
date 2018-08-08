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

import java.util.List;

public class Task {
	private int taskID;
	private int projectID;
	private String taskName;
	private List<Note> noteList;
	
	public Task(int tID) {
		this.taskID = tID;
	}
	public void setProjectID(int pID) {
		this.projectID = pID;
	}
	public void addNoteList(Note note) {		
		this.noteList.add(note);
	}
	public String getTaskName() {
		return this.taskName;
	}
	public Note getSpecificNote(int i) {
		return this.noteList.get(i);
	}
	public List<Note> getNoteList() {
		return this.noteList;
	}
}
