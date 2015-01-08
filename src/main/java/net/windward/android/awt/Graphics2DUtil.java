package net.windward.android.awt;

import net.windward.android.awt.font.GlyphVector;
import net.windward.android.awt.geom.AffineTransform;
import org.apache.harmony.awt.gl.font.CommonGlyphVector;

public class Graphics2DUtil {

	public static void drawGlyphVector(Graphics2D g2d, GlyphVector g, float x, float y) {
		if(isIdentity(g) && g instanceof CommonGlyphVector) {
			g2d.drawString(string((CommonGlyphVector)g), x, y);
		} else {
			Shape s = g.getOutline(x, y);
			g2d.fill(s);
		}
	}

	private static String string(CommonGlyphVector gv) {
		StringBuilder text = new StringBuilder();
		for(int i=0;i!=gv.getNumGlyphs();++i) {
			int charIndex = gv.getGlyphCharIndex(i);
			char ch = gv.getGlyphChar(charIndex);
			text.append(ch);
		}
		return text.toString();
	}

	private static boolean isIdentity(GlyphVector gv) {
		for(int i=0;i!=gv.getNumGlyphs();++i) {
			AffineTransform tr = gv.getGlyphTransform(i);
			if(tr != null && !tr.isIdentity()) {
				return false;
			}
		}
		return true;
	}
}
