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

public class User {
	private String username;
	private String password;
	private String email;
	
	public User() {
		this.username = null;
		this.password = null;
		this.email = null;
	}
	
	public User( String _username, String _password, String _email ) {
		this.username = _username;
		this.password = _password;
		this.email = _email;
	}
	
	public User( String _username, String _password ) {
		this.username = _username;
		this.password = _password;
		this.email = null;
	}
	
	public void setUsername( String _username ) {
		this.username = _username;
		this.password = null;
		this.email = null;
	}
	
	public void setPassword( String _password ) {
		this.username = null;
		this.password = _password;
		this.email = null;
	}
	
	public void setEmail( String _email ) {
		this.username = null;
		this.password = null;
		this.email = _email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
}
