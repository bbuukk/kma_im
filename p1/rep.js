const char = "A";
// UTF-8
const utf8 = new TextEncoder().encode(char);
console.log(
  "UTF-8:",
  utf8.reduce((acc, i) => acc + i.toString(2).padStart(8, "0"), "")
);

// UTF-16
const utf16 = new Uint16Array([...char].map((c) => c.charCodeAt(0)));
console.log(
  "UTF-16:",
  Array.from(utf16)
    .map((i) => i.toString(2).padStart(16, "0"))
    .join("")
);

// UTF-32
const utf32 = new Uint32Array([...char].map((c) => c.charCodeAt(0)));
console.log(
  "UTF-32:",
  Array.from(utf32)
    .map((i) => i.toString(2).padStart(32, "0"))
    .join("")
);
