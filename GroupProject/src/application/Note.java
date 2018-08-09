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

public class Note {
	private int noteID;
	private int taskID;
	private String noteName; // don't know why we need this??
	private String description; // bad name for what the note contains??
	
	public Note(int nID, int tID, String name, String desc) {
		this.noteID = nID;
		this.taskID = tID;
		this.noteName = name;
		this.description = desc;
	}
	public String getNoteName() {
		return this.noteName;
	}
	public String getDescription() {
		return this.description;
	}
}
