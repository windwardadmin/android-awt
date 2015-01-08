/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.windward.android.awt;


import net.windward.android.awt.geom.AffineTransform;
import net.windward.android.awt.geom.Rectangle2D;
import net.windward.android.awt.image.BufferedImage;
import net.windward.android.awt.image.ColorModel;
import net.windward.android.awt.image.DataBuffer;
import net.windward.android.awt.image.DataBufferByte;
import net.windward.android.awt.image.DataBufferInt;
import net.windward.android.awt.image.DataBufferUShort;
import org.apache.harmony.awt.internal.nls.Messages;


public class TexturePaint implements Paint {
    /**
     * The BufferedImage object used as texture  
     */
    BufferedImage img;

    /**
     * The Rectangle2D bounds of texture piece to be painted  
     */
    Rectangle2D anchor;

    public TexturePaint(BufferedImage img, Rectangle2D anchor) {
        if (img == null) {
            // awt.114=Image is null
            throw new NullPointerException(Messages.getString("awt.114")); //$NON-NLS-1$
        }
        if (anchor == null) {
            // awt.115=Anchor is null
            throw new NullPointerException(Messages.getString("awt.115")); //$NON-NLS-1$
        }
        this.img = img;
        this.anchor = anchor;
    }

    public BufferedImage getImage() {
        return img;
    }

    public PaintContext createContext(ColorModel cm, Rectangle device, Rectangle2D user,
            AffineTransform t, RenderingHints hints) {
        Object value = hints.get(RenderingHints.KEY_INTERPOLATION);
        boolean bilinear = (value != null)
                && (value != RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        int type = img.getType();
        DataBuffer buf = img.getRaster().getDataBuffer();
        if (buf instanceof DataBufferInt) {
            if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_ARGB_PRE
                    || type == BufferedImage.TYPE_INT_BGR || type == BufferedImage.TYPE_INT_RGB) {
                if (bilinear) {
                    return new TexturePaintContext.IntBilinear(img, anchor, t);
                }
                return new TexturePaintContext.IntSimple(img, anchor, t);
            }
        } else if (buf instanceof DataBufferByte) {
            if (type == BufferedImage.TYPE_BYTE_GRAY) {
                if (bilinear) {
                    return new TexturePaintContext.ByteBilinear(img, anchor, t);
                }
                return new TexturePaintContext.ByteSimple(img, anchor, t);
            }
        } else if (buf instanceof DataBufferUShort) {
            if (type == BufferedImage.TYPE_USHORT_GRAY) {
                if (bilinear) {
                    return new TexturePaintContext.ShortBilinear(img, anchor, t);
                }
                return new TexturePaintContext.ShortSimple(img, anchor, t);
            }
        }
        if (bilinear) {
            if (type != BufferedImage.TYPE_BYTE_INDEXED) {
                return new TexturePaintContext.CommonBilinear(img, anchor, t);
            }
        } else {
            return new TexturePaintContext.CommonSimple(img, anchor, t);
        }
        return new TexturePaintContext(img, anchor, t);
    }

    public int getTransparency() {
        return img.getColorModel().getTransparency();
    }

    public Rectangle2D getAnchorRect() {
        return anchor;
    }
}
