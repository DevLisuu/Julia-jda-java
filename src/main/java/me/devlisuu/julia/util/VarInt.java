package me.devlisuu.julia.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author zh32 <zh32 at zh32.de>
 */
public class VarInt {
    public static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;

        while(true) {
            int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if(j > 5) throw new RuntimeException("VarInt too big");
            if((k & 0x80) != 128) break;
        }

        return i;
    }

    public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while(true) {
            if((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }
            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }
}
