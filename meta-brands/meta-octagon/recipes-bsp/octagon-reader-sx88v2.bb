SUMMARY = "libreader for Octagon Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(sx88v2)$"

SRCDATE = "20240313"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/octagon/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_install:append() {
    python3 - <<'PY'
from pathlib import Path

reader = Path("${D}/${bindir}/libreader")

old = bytes.fromhex("0a30c5e5")
new = bytes.fromhex("0a20c5e5")

if reader.exists():
    data = reader.read_bytes()
    if old in data:
        reader.write_bytes(data.replace(old, new, 1))
PY
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "5c84dffbd04e9cccf234264b6a89b408"
SRC_URI[sha256sum] = "d8fc17408fbadc8ca08ff904287477ee55dc2092a1a357244f182bee2d6701c4"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped"
