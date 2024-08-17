package com.example.demo.ciphers.aes.logic;

import com.example.demo.ciphers.des.logic.HexString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AESKeyGeneratorTest {

    @Test
    void testGenerateKeys() {
        // Arrange
        HexString hexKey = new HexString("5468617473206D79204B756E67204675");

        // Act
        HexString[] result = AESKeyGenerator.generateKeys(hexKey);

        // Assert
        assertEquals(11, result.length);
        assertEquals("5468617473206D79204B756E67204675", result[0].toString());
        assertEquals("E232FCF191129188B159E4E6D679A293", result[1].toString());
        assertEquals("56082007C71AB18F76435569A03AF7FA", result[2].toString());
        assertEquals("D2600DE7157ABC686339E901C3031EFB", result[3].toString());
        assertEquals("A11202C9B468BEA1D75157A01452495B", result[4].toString());
        assertEquals("B1293B3305418592D210D232C6429B69", result[5].toString());
        assertEquals("BD3DC287B87C47156A6C9527AC2E0E4E", result[6].toString());
        assertEquals("CC96ED1674EAAA031E863F24B2A8316A", result[7].toString());
        assertEquals("8E51EF21FABB4522E43D7A0656954B6C", result[8].toString());
        assertEquals("BFE2BF904559FAB2A16480B4F7F1CBD8", result[9].toString());
        assertEquals("28FDDEF86DA4244ACCC0A4FE3B316F26", result[10].toString());
    }

}