function unicodeToUtf8(codePoint) {
  if (codePoint < 0x80) {
    return [codePoint];
  } else if (codePoint < 0x800) {
    return [0xc0 | (codePoint >> 6), 0x80 | (codePoint & 0x3f)];
  } else if (codePoint < 0x10000) {
    return [
      0xe0 | (codePoint >> 12),
      0x80 | ((codePoint >> 6) & 0x3f),
      0x80 | (codePoint & 0x3f),
    ];
  } else {
    return [
      0xf0 | (codePoint >> 18),
      0x80 | ((codePoint >> 12) & 0x3f),
      0x80 | ((codePoint >> 6) & 0x3f),
      0x80 | (codePoint & 0x3f),
    ];
  }
}

function utf8ToUnicode(byteSequence) {
  let codePoint = 0;
  let numBytes = 0;
  for (let i = 0; i < byteSequence.length; i++) {
    const byte = byteSequence[i];
    if (numBytes === 0) {
      if ((byte & 0x80) === 0) {
        codePoint = byte;
      } else if ((byte & 0xe0) === 0xc0) {
        codePoint = byte & 0x1f;
        numBytes = 1;
      } else if ((byte & 0xf0) === 0xe0) {
        codePoint = byte & 0x0f;
        numBytes = 2;
      } else if ((byte & 0xf8) === 0xf0) {
        codePoint = byte & 0x07;
        numBytes = 3;
      } else {
        throw new Error("Invalid UTF-8 byte sequence");
      }
    } else {
      if ((byte & 0xc0) !== 0x80) {
        throw new Error("Invalid UTF-8 byte sequence");
      }
      codePoint = (codePoint << 6) | (byte & 0x3f);
      numBytes--;
    }
  }
  if (numBytes !== 0) {
    throw new Error("Invalid UTF-8 byte sequence");
  }
  return codePoint;
}

function byteToBinary(byte) {
  return ("00000000" + byte.toString(2)).slice(-8);
}

function testConversion(codePoint) {
  const byteSequence = unicodeToUtf8(codePoint);
  const binarySequence = byteSequence.map(byteToBinary);
  const result = utf8ToUnicode(byteSequence);
  if (result !== codePoint) {
    console.error(
      `Conversion failed: ${codePoint} -> ${binarySequence} -> ${result}`
    );
  } else {
    console.log(
      `Conversion succeeded: ${codePoint} -> ${binarySequence} -> ${result}`
    );
  }
}

testConversion(0x41); // should print "Conversion succeeded: 65 -> 01000001 -> 65"
testConversion(0x20ac); // should print "Conversion succeeded: 8364 -> 11100010,10000010,10101100 -> 8364"
testConversion(0x1f600); // should print "Conversion succeeded: 128512 -> 11110000,10011111,10001001,10000000 -> 128512"

testConversion(0x41); // should print "Conversion succeeded: 65 -> 65 -> 65"
testConversion(0x20ac); // should print "Conversion succeeded: 8364 -> 226, 130, 172 -> 8364"
testConversion(0x1f600); // should print "Conversion succeeded: 128512 -> 240, 159, 152, 128 -> 128512"
