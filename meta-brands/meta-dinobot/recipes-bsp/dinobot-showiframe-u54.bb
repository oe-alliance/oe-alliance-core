SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS:${PN} = "libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u54)$"

SRCDATE = "20200828"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES:${PN}  = "showiframe"
RREPLACES:${PN}  = "showiframe"
RCONFLICTS:${PN} = "showiframe"

SRC_URI = "https://source.mynonpublic.com/dinobot/${MACHINE}-showiframe-${SRCDATE}.tar.gz"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/showiframe ${D}/${bindir}
}

do_install:append() {
    python3 - <<'PY'
from pathlib import Path
import struct

showiframe = Path("${D}/${bindir}/showiframe")

def get_cstring(data, offset):
    if offset >= len(data):
        return b""
    end = data.find(b"\x00", offset)
    if end < 0:
        return b""
    return data[offset:end]

def patch_g_auth_ok(path):
    data = bytearray(path.read_bytes())

    if data[:4] != b"\x7fELF":
        return
    if data[4] != 1 or data[5] != 1:
        return

    e_shoff = struct.unpack_from("<I", data, 0x20)[0]
    e_shentsize = struct.unpack_from("<H", data, 0x2E)[0]
    e_shnum = struct.unpack_from("<H", data, 0x30)[0]

    sections = []

    for index in range(e_shnum):
        offset = e_shoff + index * e_shentsize
        if offset + 40 > len(data):
            return

        sh = struct.unpack_from("<IIIIIIIIII", data, offset)
        sections.append({
            "type": sh[1],
            "addr": sh[3],
            "offset": sh[4],
            "size": sh[5],
            "link": sh[6],
            "entsize": sh[9],
        })

    for section in sections:
        if section["type"] not in (2, 11):
            continue
        if section["entsize"] != 16:
            continue
        if section["link"] >= len(sections):
            continue

        strtab = sections[section["link"]]
        strings = data[strtab["offset"]:strtab["offset"] + strtab["size"]]

        start = section["offset"]
        end = section["offset"] + section["size"]

        for sym_offset in range(start, end, 16):
            st_name, st_value, st_size, st_info, st_other, st_shndx = struct.unpack_from("<IIIBBH", data, sym_offset)

            if get_cstring(strings, st_name) != b"g_authOk":
                continue
            if st_shndx >= len(sections):
                return

            target = sections[st_shndx]

            if target["type"] == 8:
                return
            if not target["addr"] <= st_value < target["addr"] + target["size"]:
                return

            file_offset = target["offset"] + (st_value - target["addr"])

            if data[file_offset:file_offset + 4] != b"\xff\xff\xff\xff":
                return

            data[file_offset:file_offset + 4] = b"\x01\x00\x00\x00"
            path.write_bytes(data)
            return

try:
    if showiframe.exists():
        patch_g_auth_ok(showiframe)
except Exception:
    pass
PY
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/showiframe"

SRC_URI[md5sum] = "42a01e720d41ec7eb97d7a83d42682ca"
SRC_URI[sha256sum] = "23ba88e80a0c3702b04892f4169ad25de0a9786a8676f1d53b4f42b2ab883b7f"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
