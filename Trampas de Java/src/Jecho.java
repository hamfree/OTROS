/*
** jecho.java - Java version of echo.
**
** This version of echo obeys the POSIX 1003.1, 2004 standard; which specifies
**  that echo shall not support any options, and need not support any 
**  escape characters (and this dosen't). This way sites can use the same
**  standard version of echo on all their systems, and not be at the mercy
**  of quirky shell builtins.
**
** 12-Jan-2005	ver 1.00
**
** COMPILE: javac jecho.java
**
** USAGE: java jecho [word [word...]]
**
**
** COPYRIGHT: Copyright (c) 2005 Brendan Gregg.
**
**  This program is free software; you can redistribute it and/or
**  modify it under the terms of the GNU General Public License
**  as published by the Free Software Foundation; either version 2
**  of the License, or (at your option) any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU General Public License for more details.
**
**  You should have received a copy of the GNU General Public License
**  along with this program; if not, write to the Free Software Foundation,
**  Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
**
**  (http://www.gnu.org/copyleft/gpl.html)
**
** 12-Jan-2005	Brendan Gregg	Created this.
*/

import java.io.*;

public class Jecho {

	static int index = 0;

	public static void main(String[] args) throws IOException {

		while (index < args.length) {
			System.out.print(args[index++]);
			if (index < args.length) 
				System.out.print(" ");
		}
		System.out.println();
	}
}

